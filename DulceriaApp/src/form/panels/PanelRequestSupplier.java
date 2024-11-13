package form.panels;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.extras.components.FlatComboBox;
import com.formdev.flatlaf.extras.components.FlatLabel;
import components.MyJTextField;
import components.MyScrollPane;
import components.MyTxtAreaDescrip;
import components.Notify;
import form.FormProveedor;
import form.request.ProveedorRequest;
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
import model.Proveedor;
import net.miginfocom.swing.MigLayout;
import raven.modal.Toast;
import raven.modal.component.ModalBorderAction;
import raven.modal.component.SimpleModalBorder;
import raven.modal.listener.ModalController;
import raven.modal.toast.ToastPromise;
import utils.EstadosMx;
import utils.Request;

public class PanelRequestSupplier extends JPanel {

    private final String KEY = getClass().getName();

    private FormProveedor form;
    private MyTxtAreaDescrip description;
    private Request request;
    private Proveedor supplier;

    private MyJTextField inputFirtsName;
    private MyJTextField inputLastName;
    private JFormattedTextField inputPhone;
    private MyJTextField inputEmail;
    private FlatComboBox<String> inputState;
    private FlatComboBox<String> inputMunicipality;
    private MyJTextField inputStreet;
    private JFormattedTextField inputZip;

    private JButton button;

    //Add
    public PanelRequestSupplier(FormProveedor form, Request request) {
        this.form = form;
        this.request = request;

        initComponents();
        initListeners();
        init();
        addItemsSatates();
        fillFields();
    }

    //Update
    public PanelRequestSupplier(Proveedor supplier, FormProveedor form, Request request) {
        this.supplier = supplier;
        this.form = form;
        this.request = request;

        initComponents();
        initListeners();
        init();
        addItemsSatates();
        fillFields();
    }

    private void initComponents() {
        try {
            description = new MyTxtAreaDescrip("");
            inputFirtsName = new MyJTextField();
            inputLastName = new MyJTextField();
            inputPhone = new JFormattedTextField();
            inputPhone.setFormatterFactory(new DefaultFormatterFactory(new MaskFormatter("###-###-####")));
            inputEmail = new MyJTextField();
            inputState = new FlatComboBox<>();
            inputState.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Selecione el Estado"}));
            inputState.setMaximumRowCount(8);
            inputMunicipality = new FlatComboBox<>();
            inputMunicipality.setMaximumRowCount(8);
            inputStreet = new MyJTextField();
            inputZip = new JFormattedTextField();
            inputZip.setFormatterFactory(new DefaultFormatterFactory(new MaskFormatter("#####")));

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
        inputState.addActionListener(x -> {
            if (inputState.getSelectedIndex() != 0) {
                inputMunicipality.removeAllItems();
                EstadosMx.getInstance().addItemsMunicipality(inputState.getSelectedItem().toString(), inputMunicipality);
            } else {
                inputMunicipality.removeAllItems();
            }
        });

        button.addActionListener((e) -> {
            switch (request) {
                case INSERTS ->
                    ModalBorderAction.getModalBorderAction(button).doAction(SimpleModalBorder.OK_OPTION);
                case UPDATE ->
                    ModalBorderAction.getModalBorderAction(button).doAction(SimpleModalBorder.YES_OPTION);
                default ->
                    throw new AssertionError();
            }
        });
    }

    private void fillFields() {
        switch (request) {

            case INSERTS -> {
                button.setText("Agregar");
                description.setText("Ingresa los Datos de tu proveedor");
            }

            case UPDATE -> {
//                button.setText("Actualizar");
//                description.setText("Permite Modificar la información del técnico");
//                inputFirtsName.setText(tecnico.getFirsName());
//                inputFirtsName.setEnabled(false);
//                inputLastName.setText(tecnico.getLastName());
//                inputLastName.setEnabled(false);
//                inputState.setSelectedItem((tecnico.getState() == null) ? "" : EstadosMx.getInstance().getStateName(tecnico.getState()));
//                inputMunicipality.setSelectedItem(tecnico.getMunicipality() == null ? "" : tecnico.getMunicipality());
//                inputPhone.setValue(tecnico.getPhone());
//                inputCologne.setText(tecnico.getCologne());
//                inputZip.setText(tecnico.getZip());
            }

            default ->
                throw new AssertionError();
        }
    }

    private void init() {
        setLayout(new MigLayout("fillx,insets 0", "[center]", "[center]"));

        JPanel panel = new JPanel(new MigLayout("wrap,fillx,insets 0 45 0 45", "fill,400!"));

        button.putClientProperty(FlatClientProperties.STYLE, ""
                + "foreground:#FFFFFF");

        description.putClientProperty(FlatClientProperties.STYLE, ""
                + "[light]foreground:lighten(@foreground,30%);"
                + "[dark]foreground:darken(@foreground,30%);"
                + "background:null");

        inputFirtsName.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Nombre");
        inputFirtsName.putClientProperty(FlatClientProperties.TEXT_FIELD_LEADING_ICON, new FlatSVGIcon("resources/icon/ic_name.svg", 0.35f));
        inputFirtsName.putClientProperty(FlatClientProperties.STYLE, ""
                + "iconTextGap:10;"
                + "showClearButton:true");

        inputLastName.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Apellidos");
        inputLastName.putClientProperty(FlatClientProperties.TEXT_FIELD_LEADING_ICON, new FlatSVGIcon("resources/icon/ic_name.svg", 0.35f));
        inputLastName.putClientProperty(FlatClientProperties.STYLE, ""
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

        inputEmail.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Correo Electrinico");
        inputEmail.putClientProperty(FlatClientProperties.TEXT_FIELD_LEADING_ICON, new FlatSVGIcon("resources/icon/ic_email.svg", 0.35f));
        inputEmail.putClientProperty(FlatClientProperties.STYLE, ""
                + "iconTextGap:10;"
                + "showClearButton:true");

        inputStreet.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Colonia");
        inputStreet.putClientProperty(FlatClientProperties.TEXT_FIELD_LEADING_ICON, new FlatSVGIcon("resources/icon/ic_address.svg", 0.35f));
        inputStreet.putClientProperty(FlatClientProperties.STYLE, ""
                + "iconTextGap:10;"
                + "showClearButton:true");

        inputZip.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Codigo Postal");
        inputZip.putClientProperty(FlatClientProperties.TEXT_FIELD_LEADING_ICON, new FlatSVGIcon("resources/icon/ic_zipcode.svg", 0.35f));
        inputZip.putClientProperty(FlatClientProperties.STYLE, ""
                + "iconTextGap:10;"
                + "showClearButton:true");

        JLabel jLabel = new JLabel("DATOS PERSONALES");
        jLabel.putClientProperty(FlatClientProperties.STYLE, ""
                + "font:bold +1");

        JLabel labelAddres = new FlatLabel();
        labelAddres.setText("DIRECCIÓN");
        labelAddres.putClientProperty(FlatClientProperties.STYLE, ""
                + "font:bold +1");

        panel.add(description);
        panel.add(jLabel, "grow 0,gapy 5,al center");
        panel.add(new JLabel("Nombre Completo"));
        panel.add(inputFirtsName, "split 2");
        panel.add(inputLastName);
        panel.add(new JLabel("Telefono"));
        panel.add(inputPhone);
        panel.add(new JLabel("Correo"));
        panel.add(inputEmail, "wrap 10");
        panel.add(labelAddres, "grow 0,al center");
        panel.add(new JLabel("Estado"));
        panel.add(inputState);
        panel.add(new JLabel("Municipio"));
        panel.add(inputMunicipality);
        panel.add(new JLabel("Calle"));
        panel.add(inputStreet);
        panel.add(new JLabel("Codigo Postal"));
        panel.add(inputZip);
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
                    toas.update("Verificando");
                    int insert = insert();
                    if (insert != -1) {
                        new Thread(() -> form.refreshTabla()).start();
                        toas.done(Toast.Type.SUCCESS, "Proveedor Agregado Exitoxamente");
                        controller.close();
                    } else {
                        toas.done(Toast.Type.ERROR, "Operación fallida");
                    }
                } catch (Exception e) {
                    if (e.getMessage().contains("Data too long")) {
                        toas.done(Toast.Type.WARNING, "Has Revasado el Limite de Caracteres\n"
                                + e.getLocalizedMessage());
                    } else {
                        toas.done(Toast.Type.ERROR, "Hubo un problema al Proveedor ala base de datos"
                                + "\nCausa: " + e.getLocalizedMessage());
                    }
                    controller.consume();
                }
            }
        });
    }

    private int insert() throws Exception {
        Toast.closeAll();

        if (toastIsEmptyCampos()) {
            return -1;
        }

        String firsName = inputFirtsName.getText().strip();
        String lastName = inputLastName.getText().strip();
        String phone = inputPhone.getValue() == null ? null : inputPhone.getText();
        String email = inputEmail.getText().isEmpty() ? null : inputEmail.getText().strip();
        String state = EstadosMx.getInstance().getStatesAbbreviation(inputState.getSelectedItem().toString());
        String municipality = (inputMunicipality.getSelectedItem() == null) ? null : inputMunicipality.getSelectedItem().toString();

        String street = inputStreet.getText().isEmpty() ? null : inputStreet.getText().strip();
        String zip = inputZip.getValue() == null ? null : inputZip.getText();
        LocalDateTime dateRegister = LocalDateTime.now();

        Proveedor proveedor = new Proveedor(firsName, lastName, phone, email, state, municipality, street, zip, dateRegister);

        if (proveedor.verifyNotEmpty()) {
            return ProveedorRequest.addProveedor(proveedor);
        }
        return -1;
    }

    private Boolean toastIsEmptyCampos() throws Exception {
        if (verifyInputEmpty(inputFirtsName, "Nombre")) {
            return true;
        }
        if (verifyInputEmpty(inputLastName, "Apellidos")) {
            return true;
        }
        if (verifyInputEmpty(inputPhone, "Telefono")) {
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
            JFormattedTextField op = (JFormattedTextField) field;
            if (op.getValue() == null) {
                Notify.getInstance().showToast(Toast.Type.WARNING, "Es requerido el campo " + str);
                return true;
            }
        }
        return false;
    }

    private void addItemsSatates() {
        EstadosMx.getInstance().addItemsStates(inputState);
    }

}
