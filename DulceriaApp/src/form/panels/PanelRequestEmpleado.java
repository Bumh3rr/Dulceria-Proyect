package form.panels;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.components.FlatComboBox;
import com.formdev.flatlaf.extras.components.FlatLabel;
import components.input.InputDecimal;
import components.input.InputText;
import components.MyScrollPane;
import components.MyTxtAreaDescrip;
import components.button.ButtonDefault;
import components.input.InputTextPhone;
import java.time.LocalDateTime;
import java.util.Optional;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import model.Empleado;
import net.miginfocom.swing.MigLayout;
import raven.modal.Toast;
import utils.CheckExpression;
import utils.CheckInput;

public class PanelRequestEmpleado extends JPanel {
    private InputText firstname, lastname, rfc,address;
    private InputTextPhone phone;
    private FlatComboBox<String> puesto;
    private JFormattedTextField sueldo;
    private JButton button;

    public void installEventButton(Runnable event) {
        button.addActionListener((e) -> event.run());
    }

    public PanelRequestEmpleado setEmpleado(Empleado empleado){
        firstname.setText(empleado.getNombre());
        lastname.setText(empleado.getApellido());
        phone.setValue(empleado.getTelefono());
        address.setText(empleado.getDireccion());
        rfc.setText(empleado.getRfc());
        puesto.setSelectedItem(empleado.getPuesto().toString());
        sueldo.setValue(empleado.getSueldo());
        button.setText("Actualizar");
        return this;
    }

    public PanelRequestEmpleado() {
        initComponents();
        init();
    }

    private void initComponents() {
        firstname = new InputText("Ingresa el Nombre", 45).setIcon("resources/icon/ic_name.svg");
        lastname = new InputText("Ingresa el Apellido", 45).setIcon("resources/icon/ic_name.svg");
        phone = new InputTextPhone();
        puesto = new FlatComboBox<>();
        puesto.setModel(new DefaultComboBoxModel<>(new String[]{"Seleccione el Estado", "VENDEDOR", "SUPERVISOR"}));
        puesto.setMaximumRowCount(8);
        sueldo = new InputDecimal(50000);
        rfc = new InputText("Ingresa el RFC de 13 Dígitos", 15);
        address = new InputText("Ingresa la Dirección", 45).setIcon("resources/icon/ic_address.svg");
        button = new ButtonDefault("Aceptar");
    }

    private void init() {
        setLayout(new MigLayout("fillx,insets 0", "[center]", "[center]"));
        JPanel panel = new JPanel(new MigLayout("wrap,fillx,insets 0 45 0 45", "fill,400!"));

        JLabel jLabel = new JLabel("DATOS PERSONALES");
        jLabel.putClientProperty(FlatClientProperties.STYLE, ""
                + "font:bold +1");
        JLabel labelAddres = new FlatLabel();
        labelAddres.setText("DATOS DEL TRABAJO");
        labelAddres.putClientProperty(FlatClientProperties.STYLE, ""
                + "font:bold +1");

        panel.add(jLabel, "grow 0,al center");
        panel.add(new JLabel("Nombre Completo"));
        panel.add(firstname, "split 2");
        panel.add(lastname);
        panel.add(new JLabel("Telefono"));
        panel.add(phone);
        panel.add(new JLabel("RFC"));
        panel.add(rfc);
        panel.add(new JLabel("Dirección"));
        panel.add(address);
        panel.add(labelAddres, "grow 0,al center");
        panel.add(new JLabel("Puesto"));
        panel.add(puesto);
        panel.add(new JLabel("Sueldo"));
        panel.add(sueldo);
        panel.add(button, "grow 0,gapy 10,al trail");
        add(new MyScrollPane(panel));
    }

    public Optional<Empleado> getValue(){
        Toast.closeAll();
        if (checkInputs()) return Optional.empty();
        String nombre = firstname.getText().strip();
        String apellido = lastname.getText().strip();
        String telefono = phone.getText();
        String direccion = address.getText().isEmpty() ? null : address.getText();
        String rfc = this.rfc.getText().isEmpty() ? null : this.rfc.getText();
        Empleado.Puesto puesto = Empleado.Puesto.valueOf(this.puesto.getSelectedItem().toString());
        Empleado.Status estado = Empleado.Status.Activo;
        Double sueldo = this.sueldo.getValue() == null ? 0.00 : Double.valueOf(this.sueldo.getValue().toString());
        LocalDateTime dateRegister = LocalDateTime.now();

        Empleado empleado = Empleado.builder()
                .nombre(nombre)
                .apellido(apellido)
                .telefono(telefono)
                .direccion(direccion)
                .rfc(rfc)
                .puesto(puesto)
                .estado(estado)
                .sueldo(sueldo)
                .fecha_registro(dateRegister)
                .build();
        return Optional.ofNullable(empleado);
    }

    private boolean checkInputs() {
        // Datos requeridos
        if (CheckInput.isInvalidInput(firstname.getText(), CheckExpression::isNameValid, "Nombre", "solo debe contener letras"))
            return true;
        if (CheckInput.isInvalidInput(lastname.getText(), CheckExpression::isNameValid, "Apellidos", "solo debe contener letras"))
            return true;
        if (CheckInput.isInvalidSelection(this.puesto.getSelectedIndex(), "Puesto")) return true;
        if (CheckInput.isNullInput(phone.getValue(), "Teléfono")) return true;

        // Opcionales
        if (CheckInput.isOptionalInvalidInput(rfc.getText(), CheckExpression::isValidRFCTaller, "RFC")) return true;
        return false;
    }

}
