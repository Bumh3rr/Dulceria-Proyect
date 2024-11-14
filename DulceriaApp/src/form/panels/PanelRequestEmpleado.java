package form.panels;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.extras.components.FlatComboBox;
import com.formdev.flatlaf.extras.components.FlatLabel;
import components.MyJTextField;
import components.MyScrollPane;
import components.MyTxtAreaDescrip;
import components.Notify;
import form.FormEmpleado;
import form.request.RequestEmpleado;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import javax.swing.text.NumberFormatter;
import model.Empleado;
import net.miginfocom.swing.MigLayout;
import raven.modal.Toast;
import raven.modal.component.ModalBorderAction;
import raven.modal.component.SimpleModalBorder;
import raven.modal.listener.ModalController;
import raven.modal.toast.ToastPromise;
import utils.Request;
import static utils.Request.INSERTS;
import static utils.Request.UPDATE;

public class PanelRequestEmpleado extends JPanel {

    private final String KEY = getClass().getName();
    private MyTxtAreaDescrip description;
    private Request request;
    private Empleado empleado;
    private FormEmpleado form;
    private PanelInfoEmpleado formInfo;

    private MyJTextField inputNombre;
    private MyJTextField inputApellido;
    private MyJTextField inputRFC;
    private MyJTextField inputDireccion;
    private JFormattedTextField inputPhone;
    private FlatComboBox<String> inputPuesto;
    private JFormattedTextField inputSueldo;

    private JButton button;

    //add
    public PanelRequestEmpleado(FormEmpleado form, Request request) {
        this.form = form;
        this.request = request;

        initComponents();
        initListeners();
        init();
    }

    //update
    public PanelRequestEmpleado(Empleado empleado, PanelInfoEmpleado formInfo, Request request) {
        this.empleado = empleado;
        this.formInfo = formInfo;
        this.request = request;

        initComponents();
        initListeners();
        init();
    }

    private void initComponents() {
        try {
            NumberFormatter decimalFormatter = new NumberFormatter(new DecimalFormat("#,##0.00"));
            decimalFormatter.setValueClass(Double.class);
            decimalFormatter.setMinimum(0.0);
            decimalFormatter.setMaximum(Double.MAX_VALUE);
            decimalFormatter.setAllowsInvalid(false);

            description = new MyTxtAreaDescrip("");
            inputNombre = new MyJTextField();
            inputApellido = new MyJTextField();

            inputPhone = new JFormattedTextField();
            inputPhone.setFormatterFactory(new DefaultFormatterFactory(new MaskFormatter("###-###-####")));

            inputPuesto = new FlatComboBox<>();
            inputPuesto.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Selecione el Estado", "VENDEDOR", "SUPERVISOR"}));
            inputPuesto.setMaximumRowCount(8);

            inputSueldo = new JFormattedTextField();
            inputSueldo.setFormatterFactory(new DefaultFormatterFactory(decimalFormatter));
            inputSueldo.setValue(Double.MIN_NORMAL);

            inputRFC = new MyJTextField();
            inputDireccion = new MyJTextField();

            button = new JButton() {
                @Override
                public boolean isDefaultButton() {
                    return true;
                }
            };
        } catch (ParseException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
        }

    }

    private void initListeners() {
        switch (request) {

            case INSERTS -> {
                button.setText("Agregar");
                description.setText("Ingresa los Datos de tu Empleado");
                button.addActionListener((e)
                        -> ModalBorderAction.getModalBorderAction(button).doAction(SimpleModalBorder.OK_OPTION)
                );

            }

            case UPDATE -> {
                button.setText("Actualizar");
                description.setText("Permite Modificar la información del Empleado");
                inputNombre.setText(empleado.getNombre());
                inputApellido.setText(empleado.getApellido());
                inputPhone.setText(empleado.getTelefono());
                inputPuesto.setSelectedItem(empleado.getPuesto());
                inputSueldo.setValue(empleado.getSueldo());
                inputRFC.setText(empleado.getRfc());
                inputDireccion.setText(empleado.getDireccion());
            }

            default ->
                throw new AssertionError();
        }
    }

    private void init() {
        setLayout(new MigLayout("fillx,insets 0", "[center]", "[center]"));

        JPanel panel = new JPanel(new MigLayout("wrap,fillx,insets 0 45 0 45", "fill,400!"));

        button.putClientProperty(FlatClientProperties.STYLE, "" + "foreground:#FFFFFF");

        description.putClientProperty(FlatClientProperties.STYLE, ""
                + "[light]foreground:lighten(@foreground,30%);"
                + "[dark]foreground:darken(@foreground,30%);"
                + "background:null");

        inputNombre.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Nombre");
        inputNombre.putClientProperty(FlatClientProperties.TEXT_FIELD_LEADING_ICON, new FlatSVGIcon("resources/icon/ic_name.svg", 0.35f));
        inputNombre.putClientProperty(FlatClientProperties.STYLE, ""
                + "iconTextGap:10;"
                + "showClearButton:true");

        inputApellido.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Apellido");
        inputApellido.putClientProperty(FlatClientProperties.TEXT_FIELD_LEADING_ICON, new FlatSVGIcon("resources/icon/ic_name.svg", 0.35f));
        inputApellido.putClientProperty(FlatClientProperties.STYLE, ""
                + "iconTextGap:10;"
                + "showClearButton:true");

        JLabel lbLada = new JLabel("+52", new FlatSVGIcon("resources/icon/ic_phone.svg", 0.35f), JLabel.RIGHT);
        lbLada.putClientProperty(FlatClientProperties.STYLE, ""
                + "border:0,8,0,0;"
                + "[light]foreground:lighten(@foreground,30%);"
                + "[dark]foreground:darken(@foreground,30%);");

        inputPhone.putClientProperty(FlatClientProperties.TEXT_FIELD_LEADING_COMPONENT, lbLada);
        inputPhone.putClientProperty(FlatClientProperties.STYLE, ""
                + "iconTextGap:10;"
                + "showClearButton:true");

        inputDireccion.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Direccion");
        inputDireccion.putClientProperty(FlatClientProperties.TEXT_FIELD_LEADING_ICON, new FlatSVGIcon("resources/icon/ic_address.svg", 0.35f));
        inputDireccion.putClientProperty(FlatClientProperties.STYLE, ""
                + "iconTextGap:10;"
                + "showClearButton:true");

        inputRFC.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Ingresa el RFC de 13 Digitos");
        inputRFC.putClientProperty(FlatClientProperties.STYLE, ""
                + "showClearButton:true");

        JLabel signo = new JLabel("$", JLabel.RIGHT);
        signo.putClientProperty(FlatClientProperties.STYLE, ""
                + "border:0,8,0,0;"
                + "[light]foreground:lighten(@foreground,30%);"
                + "[dark]foreground:darken(@foreground,30%);");

        inputSueldo.putClientProperty(FlatClientProperties.TEXT_FIELD_LEADING_COMPONENT, signo);
        inputSueldo.putClientProperty(FlatClientProperties.STYLE, ""
                + "iconTextGap:10;"
                + "showClearButton:true");

        JLabel jLabel = new JLabel("DATOS PERSONALES");
        jLabel.putClientProperty(FlatClientProperties.STYLE, ""
                + "font:bold +1");

        JLabel labelAddres = new FlatLabel();
        labelAddres.setText("DATOS DEL TRABAJO");
        labelAddres.putClientProperty(FlatClientProperties.STYLE, ""
                + "font:bold +1");

        panel.add(description);
        panel.add(jLabel, "grow 0,gapy 5,al center");
        panel.add(new JLabel("Nombre Completo"));
        panel.add(inputNombre, "split 2");
        panel.add(inputApellido);
        panel.add(new JLabel("Telefono"));
        panel.add(inputPhone);
        panel.add(new JLabel("RFC"));
        panel.add(inputRFC);
        panel.add(new JLabel("Direccion"));
        panel.add(inputDireccion);
        panel.add(labelAddres, "grow 0,al center");
        panel.add(new JLabel("Puesto"));
        panel.add(inputPuesto);
        panel.add(new JLabel("Sueldo"));
        panel.add(inputSueldo);
        panel.add(button, "grow 0,gapy 10,al trail");

        add(new MyScrollPane(panel));
        updateUI();
        revalidate();
    }

    public void commitInserts(ModalController controller) {
        if (Toast.checkPromiseId(KEY)) {
            controller.consume();
            return;
        }

        Toast.showPromise(SwingUtilities.windowForComponent(form), "Agregar", Notify.getInstance().getSelectedOptionTop(),
                new ToastPromise(KEY) {
            @Override
            public void execute(ToastPromise.PromiseCallback toas) {
                try {
                    controller.consume();
                    toas.update("Verificando");
                    if (insert()) {
                        new Thread(() -> form.refreshEmpleados()).start();
                        toas.done(Toast.Type.SUCCESS, "Empleado Agregado Exitoxamente");
                        controller.close();
                    } else {
                        controller.consume();
                        toas.done(Toast.Type.ERROR, "Operación fallida");
                    }
                } catch (Exception e) {
                    if (e.getMessage().contains("Data too long")) {
                        toas.done(Toast.Type.WARNING, "Has Revasado el Limite de Caracteres\n"
                                + e.getLocalizedMessage());
                    } else {
                        toas.done(Toast.Type.ERROR, "Surgió un problema al agregar al Empleado ala base de datos"
                                + "\nCausa: " + e.getLocalizedMessage());
                    }
                    controller.consume();
                }
            }
        });
    }

    private Boolean insert() throws Exception {
        Toast.closeAll();
        if (toastIsEmptyCampos()) {
            return false;
        }
        String nombre = inputNombre.getText().strip();
        String apellido = inputApellido.getText().strip();
        String telefono = inputPhone.getText();
        String direccion = inputDireccion.getText().isEmpty() ? null : inputDireccion.getText();
        String rfc = inputRFC.getText().isEmpty() ? null : inputRFC.getText();
        Empleado.Puesto puesto = Empleado.Puesto.valueOf(inputPuesto.getSelectedItem().toString());
        Empleado.Status estado = Empleado.Status.Activo;
        Double sueldo = inputSueldo.getValue() == null ? 0.00 : Double.valueOf(inputSueldo.getValue().toString());
        LocalDateTime dateRegister = LocalDateTime.now();

        return RequestEmpleado.addEmpleado(new Empleado(nombre, apellido, telefono, direccion, rfc, puesto, estado, sueldo, dateRegister));
    }

    public void commitUpdate(ModalController controller) {
        if (Toast.checkPromiseId(KEY)) {
            controller.consume();
            return;
        }

        Toast.showPromise(SwingUtilities.windowForComponent(this), "Actualización", Notify.getInstance().getSelectedOptionTop(),
                new ToastPromise(KEY) {
            @Override
            public void execute(ToastPromise.PromiseCallback toas) {
                try {
                    toas.update("Verificando");
                    if (update()) {
                        new Thread(() -> formInfo.refreshFields()).start();
                        toas.done(Toast.Type.SUCCESS, "Producto Actualizado Exitoxamente");
                        controller.close();
                    } else {
                        toas.done(Toast.Type.ERROR, "Operación fallida");
                    }

                } catch (Exception e) {
                    if (e.getMessage().contains("Data too long")) {
                        toas.done(Toast.Type.WARNING, "Has Revasado el Limite de Caracteres\n"
                                + e.getCause().toString());
                    } else {
                        toas.done(Toast.Type.ERROR, "Hubo un problema al Actualizar los datos del Empleado en la Base de datos"
                                + "\nCausa: " + e.getCause().toString());
                    }
                    controller.consume();
                }
            }
        });
    }

    private Boolean update() throws Exception {
        Toast.closeAll();
        if (toastIsEmptyCampos()) {
            return false;
        }
        
        empleado.setNombre(!inputNombre.getText().isEmpty() ? inputNombre.getText() : null);
        empleado.setApellido(!inputApellido.getText().isEmpty() ? inputApellido.getText() : null);
        empleado.setTelefono(inputPhone.getText());
        empleado.setDireccion(inputDireccion.getText().isEmpty() ? null : inputDireccion.getText());
        empleado.setRfc(inputRFC.getText().isEmpty() ? null : inputRFC.getText());
        empleado.setPuesto(Empleado.Puesto.valueOf(inputPuesto.getSelectedItem().toString()));
        empleado.setSueldo(inputSueldo.getValue() == null ? 0.00 : Double.valueOf(inputSueldo.getValue().toString()));

        return RequestEmpleado.setEmpleado(empleado);
    }

    private Boolean toastIsEmptyCampos() throws Exception {
        if (verifyInputEmpty(inputNombre, "Nombre")) {
            return true;
        }
        if (verifyInputEmpty(inputApellido, "Apellidos")) {
            return true;
        }
        if (inputPhone.getValue() == null) {
            Notify.getInstance().showToast(Toast.Type.WARNING, "Es requerido el campo Telefono");
            return true;
        }
        if (inputPuesto.getSelectedIndex() == 0) {
            Notify.getInstance().showToast(Toast.Type.WARNING, "Es requerido el campo Puesto");
            return true;
        }
        return false;
    }

    private Boolean verifyInputEmpty(JTextField field, String str) throws Exception {
        try {
            String text = String.valueOf(field.getText().strip());
            if (text.isEmpty()) {
                Notify.getInstance().showToast(Toast.Type.WARNING, "Es requerido el campo " + str);
                return true;
            }
        } catch (Exception e) {
            return true;
        }
        return false;
    }

}
