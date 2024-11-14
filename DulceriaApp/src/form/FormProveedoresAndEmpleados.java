package form;

import com.formdev.flatlaf.FlatClientProperties;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import net.miginfocom.swing.MigLayout;
import system.Form;

public class FormProveedoresAndEmpleados extends Form {

    private JTabbedPane tabb;
    private FormProveedor formProveedor;
    private FormEmpleado formEmpleado;

    @Override
    public void formInit() {
        refreshFormSelect();
    }

    @Override
    public void formOpen() {
        refreshFormSelect();
    }

    @Override
    public void formRefresh() {
        refreshFormSelect();
    }

    private void refreshFormSelect() {
        if (tabb.getSelectedIndex() == 0) {
            formProveedor.refreshTabla();
        } else if (tabb.getSelectedIndex() == 1) {
            formEmpleado.refreshEmpleados();
        }
    }

    public FormProveedoresAndEmpleados() {
        initComponents();
        init();

    }

    private void initComponents() {
        tabb = new JTabbedPane();
        formProveedor = new FormProveedor();
        formEmpleado = new FormEmpleado();
    }

    private void init() {
        setLayout(new MigLayout("fillx,wrap,insets 7 15 7 15", "[fill]", "[][fill,grow]"));
        add(super.createHeader("Custom Table", "PUTOOO EMMA Y OSVY, LOS ANO Y LOS ODIO (NO JATE).", 1));
        add(createTab(), "grow,push,gapx 7 7");
    }

    private Component createTab() {
        tabb = new JTabbedPane();
        tabb.putClientProperty(FlatClientProperties.STYLE, ""
                + "tabType:card");
        tabb.addTab("Tabla Proveedores", createBorder(formProveedor));
        tabb.addTab("Tabla Empleados", createBorder(formEmpleado));
        return tabb;
    }

    private Component createBorder(Component component) {
        JPanel panel = new JPanel(new MigLayout("fill,insets 7 0 7 0", "[fill]", "[fill]"));
        panel.add(component);
        return panel;
    }

}
