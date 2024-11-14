package form.panels;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import components.FieldTextArea;
import components.MyLabelTitle;
import components.Notify;
import form.FormEmpleado;
import form.request.RequestEmpleado;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingUtilities;
import model.Empleado;
import net.miginfocom.swing.MigLayout;
import raven.extras.AvatarIcon;
import raven.modal.ModalDialog;
import raven.modal.Toast;
import raven.modal.component.ModalBorderAction;
import raven.modal.component.SimpleModalBorder;
import raven.modal.toast.ToastPromise;
import utils.ConfigModal;
import utils.Request;

/**
 * PanelInfoEmpleado es un JPanel personalizado que muestra la información de un empleado.
 */
public class PanelInfoEmpleado extends JPanel {

    private Empleado empleado;
    private FormEmpleado form;

    private final String KEY = getClass().getName();
    private JLabel label_status;

    private FieldTextArea fieldID;
    private FieldTextArea fieldFirtsName;
    private FieldTextArea fieldLastName;
    private FieldTextArea fieldPhone;
    private FieldTextArea fieldRol;
    private FieldTextArea fieldAddres;
    private FieldTextArea fieldRFC;
    private FieldTextArea fieldSalary;
    private FieldTextArea fieldDateRegister;
    private FieldTextArea fieldDateLow;
    private FieldTextArea fieldBuys;
    private FieldTextArea fieldComis;

    private JButton buttonUpdate;
    private JButton buttonActiveOrLow;
    private JButton buttonClose;

    /**
     * Constructor de PanelInfoEmpleado.
     *
     * @param empleado El objeto Empleado con la información a mostrar.
     * @param form El formulario asociado a este panel.
     */
    public PanelInfoEmpleado(Empleado empleado, FormEmpleado form) {
        this.empleado = empleado;
        this.form = form;

        intComponents();
        initListeners();
        init();
    }

    /**
     * Refresca los campos del panel con la información actualizada del empleado.
     */
    public void refreshFields() {
        try {
            this.empleado = RequestEmpleado.getOneProducto(empleado.getIdEmpleado());

            fieldID.setTextField(String.valueOf(empleado.getIdEmpleado()));
            fieldFirtsName.setTextField(empleado.getNombre());
            fieldLastName.setTextField(empleado.getApellido());
            fieldPhone.setTextField(empleado.getApellido());
            fieldRol.setTextField(empleado.getPuesto().name());
            fieldAddres.setText(empleado.getDireccion());
            fieldRFC.setText(empleado.getRfc());
            fieldSalary.setTextField("$".concat(String.valueOf(empleado.getSueldo())));
            fieldBuys.setTextField("$".concat(String.valueOf(empleado.getVenta_semanal())));
            fieldComis.setTextField(String.valueOf(empleado.getComision()));
            fieldDateRegister.setText(empleado.getFecha_registro().toString());
            fieldDateLow.setText(empleado.getFecha_baja().toString());

        } catch (Exception e) {
            System.out.println(e.fillInStackTrace());
        }

    }

    /**
     * Inicializa los componentes del panel.
     */
    private void intComponents() {
        label_status = new JLabel();

        fieldID = new FieldTextArea(String.valueOf(empleado.getIdEmpleado()));
        fieldFirtsName = new FieldTextArea(empleado.getNombre());
        fieldLastName = new FieldTextArea(empleado.getApellido());
        fieldPhone = new FieldTextArea(empleado.getTelefono());
        fieldAddres = new FieldTextArea(empleado.getDireccion());
        fieldRFC = new FieldTextArea(empleado.getRfc());
        fieldRol = new FieldTextArea(empleado.getPuesto().name());
        fieldSalary = new FieldTextArea("$".concat(String.valueOf(empleado.getSueldo())));
        fieldBuys = new FieldTextArea("$".concat(String.valueOf(empleado.getVenta_semanal())));
        fieldComis = new FieldTextArea(String.valueOf(empleado.getComision()));
        fieldDateRegister = new FieldTextArea(empleado.getFecha_registro().toString());
        fieldDateLow = new FieldTextArea(empleado.getFecha_baja() != null ? empleado.getFecha_baja().toString() : null);

        buttonUpdate = new JButton("Actualizar");
        buttonActiveOrLow = new JButton((empleado.getEstado().name().equals(Empleado.Status.Activo.name())) ? "Baja" : "Remover Baja");
        buttonClose = new JButton("Cerrar") {
            @Override
            public boolean isDefaultButton() {
                return true;
            }
        };

    }

    /**
     * Inicializa los listeners de los componentes.
     */
    private void initListeners() {
        buttonUpdate.addActionListener((e) -> {
            PanelRequestEmpleado panelAdd = new PanelRequestEmpleado(empleado, this, Request.UPDATE);
            ModalDialog.showModal(SwingUtilities.windowForComponent(this),
                    new SimpleModalBorder(panelAdd, "Actualizar Empleado", SimpleModalBorder.DEFAULT_OPTION, (controller, action) -> {
                        if (action == SimpleModalBorder.OK_OPTION) {
                            panelAdd.commitInserts(controller);
                        } else if (action == SimpleModalBorder.CANCEL_OPTION) {
                            controller.close();
                        }
                    }), ConfigModal.getModelShowRigth());

        });

        buttonActiveOrLow.addActionListener((e) -> {
            ModalBorderAction.getModalBorderAction(buttonActiveOrLow).doAction(SimpleModalBorder.CANCEL_OPTION);
        });
        buttonClose.addActionListener((e) -> {
            ModalBorderAction.getModalBorderAction(buttonClose).doAction(SimpleModalBorder.CLOSE_OPTION);
        });
    }

    /**
     * Configura el diseño y propiedades del panel.
     */
    private void init() {
        setLayout(new MigLayout("fill,wrap,insets 0 20 20 20 20", "fill,450:550"));
        add(new JLabel(new AvatarIcon(PanelInfoEmpleado.class.getResource("/resources/lgo.png"), 100, 100, 16)), "split 2,grow 0");
        add(createHeader("Empleado " + empleado.getNombre(), 1));
        add(body());
    }

    /**
     * Crea el encabezado del panel.
     *
     * @param title El título del encabezado.
     * @param size El tamaño del encabezado.
     * @return Un componente JComponent que representa el encabezado.
     */
    public JComponent createHeader(String title, int size) {
        JPanel panel = new JPanel(new MigLayout("fill,wrap,insets 5 10 5 10", "[fill]"));
        panel.add(new MyLabelTitle(title, JLabel.LEFT, (4 - size)));
        changeStatusLabel(this.empleado);
        panel.add(label_status, "grow 0");
        return panel;
    }

    /**
     * Crea el cuerpo del panel con los campos de información del empleado.
     *
     * @return Un componente JComponent que representa el cuerpo del panel.
     */
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

        buttonClose.putClientProperty(FlatClientProperties.STYLE, ""
                + "foreground:#FFFFFF");

        JLabel subTitleEmpleado = new JLabel("DATOS DEL EMPLEADO");
        subTitleEmpleado.putClientProperty(FlatClientProperties.STYLE, ""
                + "font: bold +1;"
                + "[light]foreground:tint($Label.foreground,30%);");

        panel.add(new JSeparator(), "span 2, grow 1");
        panel.add(subTitleEmpleado, "span 2,grow 0,wrap 10,al center");
        panel.add(getLabelSubTitle("Nombre:"));
        panel.add(getLabelSubTitle("Apellido:"));
        panel.add(fieldFirtsName);
        panel.add(fieldLastName);
        panel.add(getLabelSubTitle("Dirección:"));
        panel.add(getLabelSubTitle("RFC:"));
        panel.add(fieldAddres);
        panel.add(fieldRFC);
        panel.add(getLabelSubTitle("Telefono:"), "wrap");
        panel.add(fieldPhone, "wrap");

        JLabel subTitleProv = new JLabel("DATOS DEL PROVEEDOR");
        subTitleProv.putClientProperty(FlatClientProperties.STYLE, ""
                + "font: bold +1;"
                + "[light]foreground:tint($Label.foreground,30%);");

        panel.add(new JSeparator(), "span 2, grow 1");
        panel.add(subTitleProv, "span 2,grow 0,,wrap 10,al center");
        panel.add(getLabelSubTitle("Puesto:"));
        panel.add(getLabelSubTitle("Salario:"));
        panel.add(fieldRol);
        panel.add(fieldSalary);
        panel.add(getLabelSubTitle("Fecha de Registro:"));
        panel.add(getLabelSubTitle("Fecha de Baja:"));
        panel.add(fieldDateRegister);
        panel.add(fieldDateLow);

        panel.add(new JSeparator(), "span 2, grow 1");
        panel.add(createAccions(), "span 2,grow 0,al center");

        return panel;
    }

    /**
     * Crea el panel de acciones con los botones de actualización, activación/baja y cierre.
     *
     * @return Un componente JComponent que representa el panel de acciones.
     */
    private JComponent createAccions() {
        JPanel panel = new JPanel(new MigLayout("fill,insets n", "fill"));
        panel.add(buttonUpdate);
        panel.add(buttonActiveOrLow);
        panel.add(buttonClose);
        return panel;
    }

    /**
     * Crea un JLabel con el subtítulo especificado.
     *
     * @param title El texto del subtítulo.
     * @return Un JLabel con el subtítulo.
     */
    private JLabel getLabelSubTitle(String title) {
        JLabel label = new JLabel(title);
        label.putClientProperty(FlatClientProperties.STYLE, ""
                + "[light]foreground:lighten(@foreground,30%);"
                + "[dark]foreground:darken(@foreground,30%);"
                + "font:13");

        return label;
    }

    /**
     * Realiza la baja o activación del empleado.
     *
     * @param id El ID del empleado.
     * @param request El estado solicitado para el empleado.
     */
    public void commitLow(int id, Empleado.Status request) {
        if (Toast.checkPromiseId(KEY)) {
            return;
        }

        Toast.showPromise(SwingUtilities.windowForComponent(this), (empleado.getEstado().name().equals(Empleado.Status.Activo.name())) ? "Baja" : "Remover Baja", Notify.getInstance().getSelectedOption(),
                new ToastPromise(KEY) {
                    @Override
                    public void execute(ToastPromise.PromiseCallback toas) {
                        try {
                            toas.update("Verificando");
                            if (RequestEmpleado.setDateLowEmpleado(id, (empleado.getEstado().name().equals(Empleado.Status.Activo.name())) ? null : Timestamp.valueOf(LocalDateTime.now()))) {
                                new Thread(() -> form.formOpen()).start();
                                refreshFields();
                                toas.done(Toast.Type.SUCCESS, "Operación Exitosamente");
                            } else {
                                toas.done(Toast.Type.ERROR, "Operación fallida");
                            }
                        } catch (Exception e) {
                            toas.done(Toast.Type.ERROR, e.getLocalizedMessage());
                        }
                    }
                });
    }

    /**
     * Cambia el estado del JLabel que muestra el estado del empleado.
     *
     * @param empleado El objeto Empleado con la información del estado.
     */
    public void changeStatusLabel(Empleado empleado) {
        label_status.setText((empleado.getEstado().name().equals(Empleado.Status.Activo.name())) ? "Activo" : "Inactivo");
        label_status.setIcon(new FlatSVGIcon((empleado.getEstado().name().equals(Empleado.Status.Activo.name())) ? "resources/icon/ic_active.svg" : "resources/icon/ic_inactive.svg"));
        label_status.putClientProperty(FlatClientProperties.STYLE, ""
                + "border:8,8,8,8;"
                + "arc:$Component.arc;"
                + ((empleado.getEstado().name().equals(Empleado.Status.Activo.name())) ? "background:fade(#1aad2c,10%);" : "background:fade(#F17027,10%);"));
    }
}