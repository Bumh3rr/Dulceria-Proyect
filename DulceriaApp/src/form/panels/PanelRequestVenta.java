package form.panels;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.components.FlatComboBox;
import components.ButtonIcon;
import components.MyJTextField;
import components.MyScrollPane;
import components.Notify;
import dao.pool.PoolThreads;
import dao.request.RequestEmpleado;
import dao.request.RequestVenta;
import modal.CustomModal;
import model.*;
import net.miginfocom.swing.MigLayout;
import raven.modal.ModalDialog;
import raven.modal.Toast;
import raven.modal.listener.ModalController;
import raven.modal.toast.ToastPromise;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.SwingUtilities;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComponent;
import javax.swing.JSeparator;
import java.awt.*;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

public class PanelRequestVenta extends JPanel {
    public static String ID = "PanelRequestVenta";
    private final String KEY = getClass().getName();

    private JLabel tituleProducto;
    private JLabel tituleDetails;
    private JLabel label_precioTotal;
    private JLabel label_cantidadProductos;

    private static PanelAddProductsBuy panelAddProductsBuy;
    public static LinkedList<Producto.ProductoSelect> listProductsSelect;

    private FlatComboBox<MethodPayment> comboBoxMethodPayment;
    private FlatComboBox<Empleado> comboBoxEmpleado;
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
        PoolThreads.getInstance().getExecutorService().execute(() -> {
            try {
                LinkedList<Empleado> listaEmpleado = RequestEmpleado.getAllEmpleadosSimple();
                for (Empleado empleado : listaEmpleado) {
                    comboBoxEmpleado.addItem(empleado);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void initComponents() {
        listProductsSelect = new LinkedList<>();
        panelAddProductsBuy = new PanelAddProductsBuy();

        tituleProducto = new JLabel("Productos");
        tituleDetails = new JLabel("Detalles de la Venta");

        comboBoxMethodPayment = new FlatComboBox<>();
        comboBoxMethodPayment.setModel(new DefaultComboBoxModel<>(MethodPayment.values()));
        comboBoxEmpleado = new FlatComboBox<>();

        label_cantidadProductos = new JLabel("0");
        label_precioTotal = new JLabel("0.00");

        buttonAddProducts = new ButtonIcon("Agregar Productos", "resources/icon/ic_buys.svg", 0.4f, 3);
        buttonCancelBuy = new JButton("Cancelar Venta") {
            @Override
            public void updateUI() {
                putClientProperty(FlatClientProperties.STYLE, ""
                        + "background:#ff420a;"
                        + "foreground:#FFFFFF;" +
                        "font: bold 0");
                super.updateUI();
            }
        };
        buttonAddBuy = new JButton("Realizar Venta") {
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


    private Consumer<Boolean> createMethodBack() {
        return e -> {
            if (e) {
                label_precioTotal.setText(new DecimalFormat("#,###.00").format(listProductsSelect.stream().mapToDouble(Producto.ProductoSelect::precioTotal).sum()));
                label_cantidadProductos.setText(String.valueOf(listProductsSelect.stream().mapToInt(Producto.ProductoSelect::countSelect).sum()));
            }
        };
    }

    private void initListeners() {
        buttonAddProducts.addActionListener(e -> {
            ModalDialog.pushModal(new CustomModal(panelAddProductsBuy,
                            "Agregar Productos", "resources/icon/ic_buys.svg", PanelRequestVenta.ID, createMethodBack()),
                    PanelRequestVenta.ID);
        });


        buttonCancelBuy.addActionListener(e -> {
            ModalDialog.closeModal(PanelRequestVenta.ID);
            SwingUtilities.invokeLater(() -> {
                listProductsSelect = new LinkedList<>();
                panelAddProductsBuy = new PanelAddProductsBuy();
            });

        });

        buttonAddBuy.addActionListener((e) -> commitSale());

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

        tituleProducto.putClientProperty(FlatClientProperties.STYLE, ""
                + "font:bold +3");
        tituleDetails.putClientProperty(FlatClientProperties.STYLE, ""
                + "font:bold +3");

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

    private Boolean toastIsEmptyCampos() throws Exception {
        if (listProductsSelect.isEmpty()) {
            Notify.getInstance().showToast(Toast.Type.WARNING, "No se han seleccionado productos para la venta.");
            return true;
        }
        return false;
    }


    public void commitSale() {
        if (Toast.checkPromiseId(KEY)) {
            return;
        }

        Toast.showPromise(SwingUtilities.windowForComponent(this), "Agregar", Notify.getInstance().getSelectedOption(),
                new ToastPromise(KEY) {
                    @Override
                    public void execute(ToastPromise.PromiseCallback toas) {
                        try {
                            toas.update("Verificando");
                            if (createSale()) {
                                toas.done(Toast.Type.SUCCESS, "Venta Realizada con Éxito");
                                ModalDialog.closeModal(PanelRequestVenta.ID);
                            } else {
                                toas.done(Toast.Type.ERROR, "Operación fallida");
                            }
                        } catch (Exception e) {
                            if (e.getMessage().contains("Data too long")) {
                                toas.done(Toast.Type.WARNING, "Has Revasado el Limite de Caracteres\n"
                                        + e.getLocalizedMessage());
                            } else {
                                toas.done(Toast.Type.ERROR, "Hubo un problema al realizar la venta"
                                        + "\nCausa: " + e.getLocalizedMessage());
                            }
                        }
                    }
                });
    }

    private Boolean createSale() throws Exception {
        Toast.closeAll();
        if (toastIsEmptyCampos()) {
            return false;
        }
        Empleado empleado = (Empleado) comboBoxEmpleado.getSelectedItem();
        MethodPayment methodPayment = (MethodPayment) comboBoxMethodPayment.getSelectedItem();

        System.out.println(empleado);
        System.out.println(methodPayment);

        double venta_total = listProductsSelect.stream().mapToDouble(Producto.ProductoSelect::precioTotal).sum();
        int cantidad_total_productos = listProductsSelect.stream().mapToInt(Producto.ProductoSelect::countSelect).sum();

        Venta venta = new Venta(
                empleado,
                cantidad_total_productos,
                venta_total, 
                methodPayment.getValue(),
                LocalDateTime.now());

        List<DetalleVenta> list = listProductsSelect.stream().map(productoSelect ->
                new DetalleVenta(productoSelect.id(), productoSelect.precioTotal(),productoSelect.countSelect())).toList();

        return RequestVenta.registerSale(venta, list);

    }


    public enum MethodPayment {
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

        @Override
        public String toString() {
            return this.value;
        }
    }
}
