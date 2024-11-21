package modal.cards;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.extras.components.FlatLabel;
import model.Producto;
import net.miginfocom.swing.MigLayout;
import raven.extras.AvatarIcon;

import javax.swing.*;
import java.util.function.Consumer;

public class CardProducto extends JPanel {

    private final Producto producto;
    private Consumer<Producto> consumer;

    private JLabel icon;
    private JButton button;
    private JTextPane description;

    public CardProducto(Producto producto, Consumer<Producto> consumer) {
        this.consumer = consumer;
        this.producto = producto;
        init();
    }

    private void init() {
        setLayout(new MigLayout("fill,insets 5 5 5 5 ", "", "fill"));
        putClientProperty(FlatClientProperties.STYLE, ""
                + "arc:30;"
                + "[light]background:darken($Panel.background,3%);"
                + "[dark]background:lighten($Panel.background,3%);");
        add(createHeader());
        add(createBody());
        updateUI();
        revalidate();
    }

    private JPanel createHeader() {
        JPanel header = new JPanel(new MigLayout("fill,insets 10 10 0 0", "[fill,center]", "[center]"));
        header.putClientProperty(FlatClientProperties.STYLE, ""
                + "background:null");
        icon = new JLabel(new AvatarIcon(CardProducto.class.getResource("/resources/dulce.png"), 100, 100, 16));
        header.add(icon);
        return header;
    }

    private JPanel createBody() {
        JPanel body = new JPanel(new MigLayout("wrap", "[150]", "[][]push[]push"));
        body.putClientProperty(FlatClientProperties.STYLE, ""
                + "background:null");
        JLabel title = new JLabel("Producto");
        title.putClientProperty(FlatClientProperties.STYLE, ""
                + "font:bold +1;");
        description = new JTextPane();
        description.setEditable(false);
        description.putClientProperty(FlatClientProperties.STYLE, ""
                + "border:0,0,0,0;"
                + "background:null;"
                + "[light]foreground:tint($Label.foreground,30%);"
                + "[dark]foreground:shade($Label.foreground,30%)");
        description.setText(
                "ID: " + producto.getId()
                + "\nMarca: " + producto.getMarca() + ""
                + "\nNombre: " + producto.getNombre()
                + "\nCategoria: " + producto.getCategoria().getTipo()
                + "\nUnidades Disponibles: " + producto.getStock()
                + "\nPrecio Venta: " + producto.getPrecio_Venta()
        );

        button = new JButton("Visualizar") {
            @Override
            public boolean isDefaultButton() {
                return true;
            }
        };
        button.putClientProperty(FlatClientProperties.STYLE, ""
                + "foreground:#FFFFFF");

        button.addActionListener(e -> consumer.accept(producto));

        FlatLabel label_status = new FlatLabel();
        label_status.setText(producto.getEstado().name());
        label_status.setIcon(new FlatSVGIcon((producto.getEstado().name().equals(Producto.Status.Disponible.name())) ? "resources/icon/ic_active.svg" : "resources/icon/ic_inactive.svg"));
        label_status.putClientProperty(FlatClientProperties.STYLE, ""
                + "border:8,8,8,8;"
                + "arc:$Component.arc;"
                + ((producto.getEstado().name().equals(Producto.Status.Disponible.name())) ? "background:fade(#1aad2c,10%);" : "background:fade(#F17027,10%);"));

        body.add(title);
        body.add(description);
        body.add(label_status);
        
        body.add(button, "gapy 10,al trail");
        return body;
    }

}
