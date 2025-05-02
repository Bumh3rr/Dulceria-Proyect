package form.panels;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import components.FieldTextArea;
import components.MyLabelTitle;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingUtilities;
import lombok.Setter;
import model.Empleado;
import net.miginfocom.swing.MigLayout;
import raven.extras.AvatarIcon;

public class PanelInfoEmpleado extends JPanel {
    @Setter
    private Empleado empleado;
    private JLabel label_status;
    private FieldTextArea id, firstname, lastname, phone, rol, address, rfc, salary, date_register, date_low, buys, comis;
    private JButton buttonUpdate, buttonActiveOrLow;

    public void installEventButtonUpdate(Runnable event) {
        buttonUpdate.addActionListener((e) -> event.run());
    }
    public void installEventButtonActiveOrLow(Runnable event) {
        buttonActiveOrLow.addActionListener((e) -> event.run());
    }
    public PanelInfoEmpleado(Empleado empleado) {
        this.empleado = empleado;
        intComponents();
        init();
        refreshFields();
        changeStatus();
    }

    public void refreshFields() {
        SwingUtilities.invokeLater(() -> {
            id.setTextField(String.valueOf(empleado.getIdEmpleado()));
            firstname.setTextField(empleado.getNombre());
            lastname.setTextField(empleado.getApellido());
            phone.setTextField(empleado.getApellido());
            rol.setTextField(empleado.getPuesto().name());
            address.setText(empleado.getDireccion());
            rfc.setText(empleado.getRfc());
            salary.setTextField("$".concat(String.valueOf(empleado.getSueldo())));
            buys.setTextField("$".concat(String.valueOf(empleado.getVenta_semanal())));
            comis.setTextField(String.valueOf(empleado.getComision()));
            date_register.setText(empleado.getFecha_registro().toString());
            date_low.setText((empleado.getFecha_baja() != null) ? empleado.getFecha_baja().toString() : null);
            changeStatus();
        });
    }

    private void intComponents() {
        label_status = new JLabel();
        id = new FieldTextArea(String.valueOf(empleado.getIdEmpleado()));
        firstname = new FieldTextArea(empleado.getNombre());
        lastname = new FieldTextArea(empleado.getApellido());
        phone = new FieldTextArea(empleado.getTelefono());
        address = new FieldTextArea(empleado.getDireccion());
        rfc = new FieldTextArea(empleado.getRfc());
        rol = new FieldTextArea(empleado.getPuesto().name());
        salary = new FieldTextArea("$".concat(String.valueOf(empleado.getSueldo())));
        buys = new FieldTextArea("$".concat(String.valueOf(empleado.getVenta_semanal())));
        comis = new FieldTextArea(String.valueOf(empleado.getComision()));
        date_register = new FieldTextArea(empleado.getFecha_registro().toString());
        date_low = new FieldTextArea(empleado.getFecha_baja() != null ? empleado.getFecha_baja().toString() : null);
        buttonUpdate = new JButton("Actualizar");
        buttonActiveOrLow = new JButton();
    }

    private void init() {
        setLayout(new MigLayout("fill,wrap,insets 0 20 20 20 20", "fill,450:550"));
        add(new JLabel(new AvatarIcon(PanelInfoEmpleado.class.getResource("/resources/lgo.png"), 100, 100, 16)), "split 2,grow 0");
        add(createHeader("Empleado " + empleado.getNombre(), 1));
        add(body());
    }

    private JComponent createHeader(String title, int size) {
        JPanel panel = new JPanel(new MigLayout("fill,wrap,insets 5 10 5 10", "[fill]"));
        panel.add(new MyLabelTitle(title, JLabel.LEFT, (4 - size)));

        panel.add(label_status, "grow 0");
        return panel;
    }

    private JComponent body() {
        JPanel panel = new JPanel(new MigLayout("wrap 2,fillx,insets n", "fill", "fill"));
        buttonUpdate.putClientProperty(FlatClientProperties.STYLE, ""
                + "background:#FDC211;"
                + "foreground:#FFFFFF;"
                + "font:bold +0");
        buttonActiveOrLow.putClientProperty(FlatClientProperties.STYLE, ""
                + ((empleado.getEstado().name().equals(Empleado.Status.Activo.name())) ? "background:#66a73b;" : "background:#FD961A;")
                + "foreground:#FFFFFF;"
                + "font:bold +0");
        JLabel subTitleEmpleado = new JLabel("DATOS DEL EMPLEADO");
        subTitleEmpleado.putClientProperty(FlatClientProperties.STYLE, ""
                + "font: bold +1;"
                + "[light]foreground:tint($Label.foreground,30%);");
        panel.add(new JSeparator(), "span 2, grow 1");
        panel.add(subTitleEmpleado, "span 2,grow 0,wrap 10,al center");
        panel.add(getLabelSubTitle("Nombre:"));
        panel.add(getLabelSubTitle("Apellido:"));
        panel.add(firstname);
        panel.add(lastname);
        panel.add(getLabelSubTitle("Dirección:"));
        panel.add(getLabelSubTitle("RFC:"));
        panel.add(address);
        panel.add(rfc);
        panel.add(getLabelSubTitle("Telefono:"), "wrap");
        panel.add(phone, "wrap");
        JLabel subTitleProv = new JLabel("DATOS DEL PROVEEDOR");
        subTitleProv.putClientProperty(FlatClientProperties.STYLE, ""
                + "font: bold +1;"
                + "[light]foreground:tint($Label.foreground,30%);");
        panel.add(new JSeparator(), "span 2, grow 1");
        panel.add(subTitleProv, "span 2,grow 0,,wrap 10,al center");
        panel.add(getLabelSubTitle("Puesto:"));
        panel.add(getLabelSubTitle("Salario:"));
        panel.add(rol);
        panel.add(salary);
        panel.add(getLabelSubTitle("Fecha de Registro:"));
        panel.add(getLabelSubTitle("Fecha de Baja:"));
        panel.add(date_register);
        panel.add(date_low);
        panel.add(new JSeparator(), "span 2, grow 1");
        panel.add(createAccions(), "span 2,grow 0,al center");
        return panel;
    }

    private JComponent createAccions() {
        JPanel panel = new JPanel(new MigLayout("fill,insets n", "fill"));
        panel.add(buttonUpdate);
        panel.add(buttonActiveOrLow);
        return panel;
    }

    private JLabel getLabelSubTitle(String title) {
        JLabel label = new JLabel(title);
        label.putClientProperty(FlatClientProperties.STYLE, ""
                + "[light]foreground:lighten(@foreground,30%);"
                + "[dark]foreground:darken(@foreground,30%);"
                + "font:13");

        return label;
    }

    public void changeStatus() {
        label_status.setText((this.empleado.getEstado().name().equals(Empleado.Status.Activo.name())) ? "Activo" : "Inactivo");
        label_status.setIcon(new FlatSVGIcon((this.empleado.getEstado().name().equals(Empleado.Status.Activo.name())) ? "resources/icon/ic_active.svg" : "resources/icon/ic_inactive.svg"));
        label_status.putClientProperty(FlatClientProperties.STYLE, ""
                + "border:8,8,8,8;"
                + "arc:$Component.arc;"
                + ((this.empleado.getEstado().name().equals(Empleado.Status.Activo.name())) ? "background:fade(#1aad2c,10%);" : "background:fade(#F17027,10%);"));
        buttonActiveOrLow.setText((empleado.getEstado().name().equals(Empleado.Status.Activo.name())) ? "Baja" : "Remover Baja");
        buttonActiveOrLow.putClientProperty(FlatClientProperties.STYLE, ""
                + ((empleado.getEstado().name().equals(Empleado.Status.Activo.name())) ? "background:#F17027;" : "background:#66a73b;")
                + "foreground:#FFFFFF;"
                + "font:bold +0");
    }
}