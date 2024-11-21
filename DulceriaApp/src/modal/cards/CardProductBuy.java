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
    private Consumer<Producto> consumer;

    private JButton button;
    private FlatSpinner spinner;
    private JTextPane description;

    public CardProductBuy(Producto producto, Consumer<Producto> consumer) {
        this.producto = producto;
        this.consumer = consumer;
        initUI();
    }

    private void initUI() {
        setLayout(new MigLayout("fillx,wrap,insets n", "fill", "fill"));
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
        button = new JButton("Agregar");
        spinner = new FlatSpinner();
        panel.add(button, "al center");
        panel.add(spinner, "al center");
        return panel;
    }

    private JComponent createBody() {
        JPanel panel = new JPanel(new MigLayout("fillx,wrap,insets 5", "[grow 0]"));

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
                        + "\nPrecio Venta: " + producto.getPrecio_Venta()).strip()
        );
        panel.add(description);
        return panel;
    }

    private JComponent createHeader() {
        JPanel panel = new JPanel(new MigLayout("fillx,insets 0", "fill", "fill"));
        panel.putClientProperty(FlatClientProperties.STYLE, ""
                + "background:null");
        JLabel titule = new JLabel("ID: " + producto.getId(), new FlatSVGIcon("resources/ic_candyAdd.svg", 0.4f), SwingConstants.LEFT);
        titule.putClientProperty(FlatClientProperties.STYLE, ""
                + "font:bold 0;");
        panel.add(titule);
        return panel;
    }
}
