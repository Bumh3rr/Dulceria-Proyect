package modal.cards;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.extras.components.FlatSpinner;
import model.Producto;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.util.function.Consumer;

public class CardProductBuy extends JPanel {
    private final Producto producto;
    private Consumer<Producto.ProductoSelect> consumer;

    private JButton button;
    private FlatSpinner spinner;
    private JTextPane description;

    public CardProductBuy(Producto producto, Consumer<Producto.ProductoSelect> consumer) {
        this.producto = producto;
        this.consumer = consumer;
        initComponents();
        initUI();
    }

    private void initComponents() {
        button = new JButton("Agregar") {
            @Override
            public void updateUI() {
                putClientProperty(FlatClientProperties.STYLE, ""
                        + "background:#f97316;"
                        + "foreground:#FFFFFF;" +
                        "font: bold 0");
                super.updateUI();
            }
        };
        spinner = new FlatSpinner();
        spinner.setModel(new SpinnerNumberModel(1, 1, producto.getStock(), 1));
        button.addActionListener(e -> consumer.accept(new Producto.ProductoSelect(producto.getId(), producto.getNombre(), getCountProductSelect(), producto.getPrecio_Venta(), getPrecioTotal())));
    }

    private void initUI() {
        setLayout(new MigLayout("fillx,wrap,insets n", "fill"));
        putClientProperty(FlatClientProperties.STYLE, ""
                + "arc:30;"
                + "[light]background:darken($Panel.background,3%);"
                + "[dark]background:lighten($Panel.background,3%);");
        add(createHeader());
        add(createBody());
        add(createFooter(), "al center");
        updateUI();
        revalidate();
    }

    private JComponent createFooter() {
        JPanel panel = new JPanel(new MigLayout("fillx,wrap,insets 3,hidemode 3", "fill"));
        panel.putClientProperty(FlatClientProperties.STYLE, ""
                + "background:null");

        panel.add(spinner, "al center");
        panel.add(button, "al center");
        return panel;
    }

    private JComponent createBody() {
        JPanel panel = new JPanel(new MigLayout("fillx,wrap,insets 5", "[grow 0]"));
        panel.putClientProperty(FlatClientProperties.STYLE, ""
                + "background:null");
        description = new JTextPane();
        description.setEditable(false);
        description.putClientProperty(FlatClientProperties.STYLE, ""
                + "border:0,0,0,0;"
                + "background:null;"
                + "[light]foreground:tint($Label.foreground,30%);"
                + "[dark]foreground:shade($Label.foreground,30%)");
        description.setText(
                ("Marca: " + producto.getMarca() + ""
                        + "\nNombre: " + producto.getNombre()
                        + "\nCategoria: " + producto.getCategoria().getTipo()
                        + "\nUnidades Disponibles: " + producto.getStock()
                        + "\nPrecio Venta: $" + producto.getPrecio_Venta()).strip()
        );
        panel.add(description);
        return panel;
    }

    private JComponent createHeader() {
        JPanel panel = new JPanel(new MigLayout("fillx,insets 0", "fill", "fill"));
        panel.putClientProperty(FlatClientProperties.STYLE, ""
                + "background:null");
        JLabel titule = new JLabel("ID: " + producto.getId(), new FlatSVGIcon("resources/icon/ic_candyAdd.svg", 0.4f), SwingConstants.LEFT);
        titule.putClientProperty(FlatClientProperties.STYLE, ""
                + "font:bold 0;");
        panel.add(titule);
        return panel;
    }

    private int getCountProductSelect() {
        return Integer.parseInt(spinner.getValue().toString());
    }

    private Double getPrecioTotal() {
        return getCountProductSelect() * producto.getPrecio_Venta();
    }

}
