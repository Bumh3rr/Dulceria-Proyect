package components.card.cards;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.extras.components.FlatLabel;
import components.button.ButtonDefault;
import components.card.Card;
import java.awt.Cursor;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import model.Empleado;
import net.miginfocom.swing.MigLayout;
import raven.extras.AvatarIcon;

public class CardEmpleado extends Card {
    private Empleado empleado;
    private BiConsumer<Empleado, Runnable> event;
    private JLabel icon,status;
    private JButton button;
    private JTextPane description;

    public CardEmpleado(Empleado empleado, BiConsumer<Empleado, Runnable> event) {
        super(empleado, event);
        this.empleado = empleado;
        this.event = event;
        initComponents();
        init();
    }

    private void initComponents() {
        button = new ButtonDefault("Visualizar");
        status = new JLabel();
        description = new JTextPane();
        description.setEditable(false);
        description.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
        description.putClientProperty(FlatClientProperties.STYLE, ""
                + "border:0,0,0,0;"
                + "background:null;"
                + "[light]foreground:tint($Label.foreground,30%);"
                + "[dark]foreground:shade($Label.foreground,30%)");
    }

    private void init() {
        setLayout(new MigLayout("fill,insets 5 5 5 5 ", "", "fill"));
        putClientProperty(FlatClientProperties.STYLE, ""
                + "arc:30;"
                + "[light]background:darken($Panel.background,3%);"
                + "[dark]background:lighten($Panel.background,3%);");
        add(createHeader());
        add(createBody());
    }

    private JPanel createHeader() {
        JPanel header = new JPanel(new MigLayout("fill,insets 10 10 0 0", "[fill,center]", "[center]"));
        header.putClientProperty(FlatClientProperties.STYLE,"background:null");
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
        description.setText(
                "ID: " + empleado.getIdEmpleado()
                + "\nNombre: " + empleado.getNombre()
                + "\nApellido: " + empleado.getApellido()
                + "\nPuesto: " + empleado.getPuesto()
        );

        button.addActionListener(e -> event.accept(empleado,this::setInfo));
        body.add(title);
        body.add(description);
        body.add(status);
        body.add(button, "gapy 10,al trail");
        return body;
    }

    private void setInfo() {
        status.setText(empleado.getEstado().name());
        status.setIcon(new FlatSVGIcon((empleado.getEstado().name().equals(Empleado.Status.Activo.name())) ? "resources/icon/ic_active.svg" : "resources/icon/ic_inactive.svg"));
        status.putClientProperty(FlatClientProperties.STYLE, ""
                + "border:8,8,8,8;"
                + "arc:$Component.arc;"
                + ((empleado.getEstado().name().equals(Empleado.Status.Activo.name())) ? "background:fade(#1aad2c,10%);" : "background:fade(#F17027,10%);"));
        String text = String.format("ID: %d \nNombre: %s \nApellido: %s \nTeléfono: %s",
                empleado.getIdEmpleado(), empleado.getNombre(), empleado.getApellido(), empleado.getTelefono());
        description.setText(text);
    }

}
