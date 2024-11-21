package components;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.extras.components.FlatLabel;
import java.util.function.Consumer;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import model.Empleado;
import net.miginfocom.swing.MigLayout;
import raven.extras.AvatarIcon;

public class CardEmpleado extends JPanel {

    private final Empleado empleado;
    private final Consumer<Empleado> event;

    private JLabel icon;
    private JButton button;
    private JTextPane description;

    public CardEmpleado(Empleado empleado, Consumer<Empleado> event) {
        this.empleado = empleado;
        this.event = event;
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
        icon = new JLabel(new AvatarIcon(CardEmpleado.class.getResource("/resources/lgo.png"), 100, 100, 16));
        header.add(icon);
        return header;
    }

    private JPanel createBody() {
        JPanel body = new JPanel(new MigLayout("wrap", "[150]", "[][]push[]push"));
        body.putClientProperty(FlatClientProperties.STYLE, ""
                + "background:null");
        JLabel title = new JLabel("Empleado");
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
                "ID: " + empleado.getIdEmpleado()
                + "\nNombre: " + empleado.getNombre()
                + "\nApellido: " + empleado.getApellido()
                + "\nPuesto: " + empleado.getPuesto()
        );

        button = new JButton("Visualizar") {
            @Override
            public boolean isDefaultButton() {
                return true;
            }
        };
        button.putClientProperty(FlatClientProperties.STYLE, ""
                + "foreground:#FFFFFF");

        button.addActionListener(e -> event.accept(empleado));
        
        FlatLabel label_status = new FlatLabel();
        label_status.setText(empleado.getEstado().name());
        label_status.setIcon(new FlatSVGIcon((empleado.getEstado().name().equals(Empleado.Status.Activo.name())) ? "resources/icon/ic_active.svg" : "resources/icon/ic_inactive.svg"));
        label_status.putClientProperty(FlatClientProperties.STYLE, ""
                + "border:8,8,8,8;"
                + "arc:$Component.arc;"
                + ((empleado.getEstado().name().equals(Empleado.Status.Activo.name())) ? "background:fade(#1aad2c,10%);" : "background:fade(#F17027,10%);"));
                
        
        body.add(title);
        body.add(description);
        body.add(label_status);

        body.add(button, "gapy 10,al trail");
        return body;
    }

}
