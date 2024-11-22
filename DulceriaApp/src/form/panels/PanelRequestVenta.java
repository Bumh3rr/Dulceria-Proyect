package form.panels;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.components.FlatComboBox;
import components.ButtonIcon;
import components.MyJTextField;
import components.MyScrollPane;
import components.Notify;
import form.request.RequestEmpleado;
import form.request.RequestProducto;
import modal.CustomModal;
import model.Empleado;
import model.Producto;
import net.miginfocom.swing.MigLayout;
import raven.modal.ModalDialog;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class PanelRequestVenta extends JPanel {
    public static String ID = "PanelRequestVenta";

    private LinkedList<Empleado> listaEmpleado;
    private JLabel tituleCliente;
    private JLabel tituleProducto;
    private JLabel tituleDetails;
    private static JLabel label_precioTotal;
    private static JLabel label_cantidadProductos;
    public static LinkedList<Producto> listProducts;
    private static PanelAddProductsBuy panelAddProductsBuy;
    public static LinkedList<Producto.ProductoSelect> listProductsSelect;

    private FlatComboBox<MethodPayment> comboBoxMethodPayment;
    private FlatComboBox<Empleado> comboBoxEmpleado;
    private MyJTextField inputNameCliente;
    private ButtonIcon buttonAddProducts;
    private JButton buttonAddBuy;
    private JButton buttonCancelBuy;



    public PanelRequestVenta() {
        initComponents();
        initListeners();
        init();
        addItemsEmpleados();
    }

    private void addItemsEmpleados() {
        try {
            listaEmpleado = RequestEmpleado.getAllEmpleadosSimple();
            for (Empleado empleado : listaEmpleado){
                comboBoxEmpleado.addItem(empleado);
            }
        }catch (Exception e){
        }
    }

    private void initComponents() {
        listProductsSelect =new LinkedList<>();
        listaEmpleado =new LinkedList<>();
        SwingUtilities.invokeLater(() -> panelAddProductsBuy =new PanelAddProductsBuy());

        tituleCliente = new JLabel("Detalles del Cliente");
        tituleProducto =new JLabel("Productos");
        tituleDetails = new JLabel("Detalles de la Venta");
        inputNameCliente = new MyJTextField();

        comboBoxMethodPayment =new FlatComboBox<>();
        comboBoxMethodPayment.setModel(new DefaultComboBoxModel<>(MethodPayment.values()));
        comboBoxEmpleado =new FlatComboBox<>();
        label_cantidadProductos = new JLabel("0");
        label_precioTotal = new JLabel("0.00");

        buttonAddProducts = new ButtonIcon("Agregar Productos","resources/icon/ic_buys.svg",0.4f, 3);
        buttonCancelBuy = new JButton("Cancelar Venta"){
            @Override
            public void updateUI() {
                putClientProperty(FlatClientProperties.STYLE, ""
                        + "background:#ff420a;"
                        + "foreground:#FFFFFF;" +
                        "font: bold 0");
                super.updateUI();
            }
        };
        buttonAddBuy = new JButton("Realizar Venta"){
            @Override
            public boolean isDefaultButton() {
                return true;
            }
            @Override
            public void updateUI() {
                putClientProperty(FlatClientProperties.STYLE, ""
                        + "foreground:#FFFFFF;");
                super.updateUI();
            }
        };
    }
    private void initListeners() {
        buttonAddProducts.addActionListener(e -> {
            ModalDialog.pushModal(new CustomModal(panelAddProductsBuy, "Agregar Productos", "resources/icon/ic_buys.svg"),
                    PanelRequestVenta.ID);
        });
        buttonCancelBuy.addActionListener(e -> {
            ModalDialog.closeModal(PanelRequestVenta.ID);
            SwingUtilities.invokeLater(() ->{
                listProducts = new LinkedList<>();
                panelAddProductsBuy = new PanelAddProductsBuy();
            });

        });
    }
    private void init() {
        setLayout(new MigLayout("fillx,wrap,insets 0"));
        add(cardBuy(), "al center");
        updateUI();
        revalidate();
    }

    private JComponent cardBuy() {
        JPanel panel = new JPanel(new MigLayout("wrap 2,fillx,insets 0 25 n 25,hidemode 3", "[grow 0,trail]15[fill,350:450]"));
        panel.putClientProperty(FlatClientProperties.STYLE, ""
                + "background:null");

        tituleCliente.putClientProperty(FlatClientProperties.STYLE, ""
                + "font:bold +3");
        tituleProducto.putClientProperty(FlatClientProperties.STYLE, ""
                + "font:bold +3");
        tituleDetails.putClientProperty(FlatClientProperties.STYLE, ""
                + "font:bold +3");

        inputNameCliente.setPlaceholderText("Nombre del Cliente (Opcional)");
        inputNameCliente.putClientProperty(FlatClientProperties.STYLE, ""
                + "showClearButton:true");

        panel.add(tituleCliente, "wrap,al lead,gapy 10");
        panel.add(getLabel("Nombre:"));
        panel.add(inputNameCliente);

        panel.add(new JSeparator(), "span 2,gapy 5,grow 1");
        panel.add(tituleProducto, "wrap,gapy 5,al lead");
        panel.add(getLabel("Productos:"));
        panel.add(buttonAddProducts);

        panel.add(tituleDetails, "wrap,gapy 10,al lead");
        panel.add(getLabel("Empleado:"));
        panel.add(comboBoxEmpleado);
        panel.add(getLabel("Método de Pago:"));
        panel.add(comboBoxMethodPayment);
        panel.add(getLabel("Cantidad de Productos:"));
        panel.add(label_cantidadProductos);
        panel.add(getLabel("Total: $"));
        panel.add(label_precioTotal);

        panel.add(buttonCancelBuy, "span 2,grow 0,split 2");
        panel.add(buttonAddBuy, "grow 0");
        return new MyScrollPane(panel);
    }

    private JComponent getLabel(String text) {
        return new JLabel(text, JLabel.RIGHT);
    }


    public static enum MethodPayment {
        EFECTIVO("Efectivo"),
        TARJETA("Tarjeta"),
        TRANSFERENCIA("Transferencia");

        private String value;

        MethodPayment(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}