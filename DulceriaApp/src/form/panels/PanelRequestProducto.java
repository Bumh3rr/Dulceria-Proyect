package form.panels;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.components.FlatComboBox;
import components.MyJTextField;
import components.MyScrollPane;
import components.MyTxtAreaDescrip;
import components.Notify;
import form.request.ProveedorRequest;
import form.request.RequestCategoria;
import form.request.RequestProducto;
import java.awt.EventQueue;
import java.text.DecimalFormat;
import model.Producto;
import net.miginfocom.swing.MigLayout;
import raven.modal.component.ModalBorderAction;
import raven.modal.component.SimpleModalBorder;
import javax.swing.*;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import java.text.NumberFormat;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Categoria;
import model.Proveedor;
import raven.modal.ModalDialog;
import raven.modal.Toast;
import raven.modal.listener.ModalController;
import raven.modal.toast.ToastPromise;
import utils.ConfigModal;
import utils.Request;
import static utils.Request.INSERTS;
import static utils.Request.UPDATE;

public class PanelRequestProducto extends JPanel {

    private final String KEY = getClass().getName();
    private MyTxtAreaDescrip description;
    private Request request;
    private Producto producto;
    private PanelInfoProducto formInfo;

    private MyJTextField inputNombre;
    private MyJTextField inputMarca;
    private MyJTextField inputDescripcion;
    private JFormattedTextField inputStock;
    private JFormattedTextField inputPrecioCompra;
    private JFormattedTextField inputPrecioVenta;
    private FlatComboBox<Categoria> inputCategoria;
    private FlatComboBox<Proveedor> inputProveedor;
    private JButton button;
    private JButton buttonAddCategoria;
    private JButton buttonAddProveedor;

    //Add
    public PanelRequestProducto(Request request) {
        this.request = request;
        initComponents();
        initListeners();
        init();
        fillBoxCategorias();
        fillBoxProveedores();
    }

    //Update
    public PanelRequestProducto(Request request, Producto producto, PanelInfoProducto formInfo) {
        this.request = request;
        this.producto = producto;
        this.formInfo = formInfo;

        initComponents();
        initListeners();
        init();
        fillBoxCategorias();
        fillBoxProveedores();
    }

    private void initListeners() {

        switch (request) {
            case INSERTS -> {
                button.setText("Agregar");
                description.setText("Ingresa los Datos de tu Producto");
                button.addActionListener((e)
                        -> ModalBorderAction.getModalBorderAction(button).doAction(SimpleModalBorder.OK_OPTION)
                );
            }

            case UPDATE -> {
                button.setText("Actualizar");
                description.setText("Permite Modificar la información del Producto");
                inputNombre.setText(producto.getNombre());
                inputMarca.setText(producto.getMarca());
                inputDescripcion.setText(producto.getDescripcion());
                inputStock.setValue(producto.getStock());
                inputPrecioVenta.setValue(producto.getPrecio_Venta());
                inputPrecioCompra.setValue(producto.getPrecio_Compra());
                inputCategoria.setSelectedItem(producto.getCategoria());
                inputProveedor.setSelectedItem(producto.getProveedor());
            }
            default ->
                throw new AssertionError();
        }

        button.addActionListener((e) -> {
            ModalBorderAction.getModalBorderAction(button).doAction(SimpleModalBorder.OK_OPTION);
        });

        buttonAddCategoria.addActionListener((e) -> {
            PanelAddCategoria panelAdd = new PanelAddCategoria();
            ModalDialog.showModal(SwingUtilities.windowForComponent(this),
                    new SimpleModalBorder(panelAdd, "Agregar Categoria", SimpleModalBorder.DEFAULT_OPTION, (controller, action) -> {
                        if (action == SimpleModalBorder.OK_OPTION) {
                            panelAdd.commitInserts(controller);
                            fillBoxCategorias();
                        } else if (action == SimpleModalBorder.CANCEL_OPTION) {
                            controller.close();
                        }
                    }), ConfigModal.getModelShowDefault());
        });
        buttonAddProveedor.addActionListener((e)
                -> ModalBorderAction.getModalBorderAction(buttonAddProveedor).doAction(SimpleModalBorder.PROPERTIES));

    }

    private void initComponents() {

        NumberFormatter numberFormatter = new NumberFormatter(NumberFormat.getIntegerInstance());
        numberFormatter.setValueClass(Integer.class);
        numberFormatter.setMaximum(Integer.MAX_VALUE);
        numberFormatter.setAllowsInvalid(false);

        NumberFormatter decimalFormatter = new NumberFormatter(new DecimalFormat("#,##0.00"));
        decimalFormatter.setValueClass(Double.class);
        decimalFormatter.setMinimum(0.0);
        decimalFormatter.setMaximum(Double.MAX_VALUE);
        decimalFormatter.setAllowsInvalid(false);

        description = new MyTxtAreaDescrip("Agregar Producto, Agrega un nuevo producto a la base de datos");

        inputNombre = new MyJTextField();
        inputMarca = new MyJTextField();
        inputDescripcion = new MyJTextField();

        inputStock = new JFormattedTextField();
        inputStock.setFormatterFactory(new DefaultFormatterFactory(numberFormatter));
        inputStock.setValue(0);

        inputPrecioCompra = new JFormattedTextField();
        inputPrecioCompra.setFormatterFactory(new DefaultFormatterFactory(decimalFormatter));
        inputPrecioCompra.setValue(Double.MIN_NORMAL);


        inputPrecioVenta = new JFormattedTextField();
        inputPrecioVenta.setFormatterFactory(new DefaultFormatterFactory(decimalFormatter));
        inputPrecioVenta.setValue(Double.MIN_NORMAL);

        inputCategoria = new FlatComboBox<>();
        inputCategoria.setMaximumRowCount(8);

        inputProveedor = new FlatComboBox<>();
        inputProveedor.setMaximumRowCount(8);

        buttonAddProveedor = new JButton("Agregar Proveedor");
        buttonAddCategoria = new JButton("Agregar Categoria");
        button = new JButton("Agregar Producto") {
            @Override
            public boolean isDefaultButton() {
                return true;
            }
        };
    }

    private void init() {
        setLayout(new MigLayout("fillx,insets 0", "[center]", "[center]"));

        JPanel panel = new JPanel(new MigLayout("wrap,fillx,insets 0 45 0 45", "fill,400!"));

        button.putClientProperty(FlatClientProperties.STYLE, "" + "foreground:#FFFFFF");
        buttonAddCategoria.putClientProperty(FlatClientProperties.STYLE, "" + "foreground:#4f4f4f");

        description.putClientProperty(FlatClientProperties.STYLE, ""
                + "[light]foreground:lighten(@foreground,30%);"
                + "[dark]foreground:darken(@foreground,30%);"
                + "background:null");

        inputNombre.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Nombre del Producto");
        inputNombre.putClientProperty(FlatClientProperties.STYLE, ""
                + "showClearButton:true");

        inputMarca.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Marca");
        inputMarca.putClientProperty(FlatClientProperties.STYLE, ""
                + "showClearButton:true");

        inputDescripcion.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Descripcion del Producto");
        inputDescripcion.putClientProperty(FlatClientProperties.STYLE, ""
                + "showClearButton:true");

        inputStock.putClientProperty(FlatClientProperties.STYLE, ""
                + "showClearButton:true");

        JLabel signo = new JLabel("$", JLabel.RIGHT);
        signo.putClientProperty(FlatClientProperties.STYLE, ""
                + "border:0,8,0,0;"
                + "[light]foreground:lighten(@foreground,30%);"
                + "[dark]foreground:darken(@foreground,30%);");

        inputPrecioCompra.putClientProperty(FlatClientProperties.TEXT_FIELD_LEADING_COMPONENT, signo);
        inputPrecioCompra.putClientProperty(FlatClientProperties.STYLE, ""
                + "iconTextGap:10;"
                + "showClearButton:true");

        JLabel signo2 = new JLabel("$", JLabel.RIGHT);
        signo2.putClientProperty(FlatClientProperties.STYLE, ""
                + "border:0,8,0,0;"
                + "[light]foreground:lighten(@foreground,30%);"
                + "[dark]foreground:darken(@foreground,30%);");

        inputPrecioVenta.putClientProperty(FlatClientProperties.TEXT_FIELD_LEADING_COMPONENT, signo2);
        inputPrecioVenta.putClientProperty(FlatClientProperties.STYLE, ""
                + "iconTextGap:10;"
                + "showClearButton:true");

        panel.add(description);
        panel.add(new JLabel("Producto"));
        panel.add(inputNombre, "split 2");
        panel.add(inputMarca);
        panel.add(new JLabel("Descripción"));
        panel.add(inputDescripcion);
        panel.add(new JLabel("Unidades Disponibles"));
        panel.add(inputStock, "wrap 10");
        panel.add(new JLabel("Precio Venta"));
        panel.add(inputPrecioVenta);
        panel.add(new JLabel("Precio Compra"));
        panel.add(inputPrecioCompra);
        panel.add(new JLabel("Categoria"));
        panel.add(inputCategoria, "split 2");
        panel.add(buttonAddCategoria, "grow 0");
        panel.add(new JLabel("Proveedor"));
        panel.add(inputProveedor, "split 2");
        panel.add(buttonAddProveedor, "grow 0");
        panel.add(button, "grow 0,gapy 10,al trail");

        add(new MyScrollPane(panel));
        updateUI();
        revalidate();
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
                        toas.done(Toast.Type.ERROR, "Hubo un problema al Actualizar los datos del Producto en la Base de datos"
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
        producto.setNombre(inputNombre.getText());
        producto.setMarca(inputMarca.getText());
        producto.setDescripcion(inputDescripcion.getText());
        producto.setStock(inputStock.getValue() != null ? Integer.parseInt(inputStock.getValue().toString()) : 0);
        producto.setCategoria((Categoria) inputCategoria.getSelectedItem());
        producto.setPrecio_Venta(Double.parseDouble(inputPrecioVenta.getValue().toString()));
        producto.setPrecio_Compra(Double.parseDouble(inputPrecioCompra.getValue().toString()));
        producto.setProveedor((Proveedor) inputProveedor.getSelectedItem());
        return RequestProducto.setProducto(producto);
    }

    public void commitInserts(ModalController controller) {
        if (Toast.checkPromiseId(KEY)) {
            controller.consume();
            return;
        }

        Toast.showPromise(SwingUtilities.windowForComponent(this), "Agregar", Notify.getInstance().getSelectedOption(),
                new ToastPromise(KEY) {
            @Override
            public void execute(ToastPromise.PromiseCallback toas) {
                try {
                    toas.update("Verificando");
                    if (insert()) {
                        toas.done(Toast.Type.SUCCESS, "Producto Agregado Exitoxamente");
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

    private Boolean insert() throws Exception {
        Toast.closeAll();

        if (toastIsEmptyCampos()) {
            return false;
        }

        String nombre = inputNombre.getText().strip();
        String marca = inputMarca.getText().strip();
        String descripcion = inputDescripcion.getText().strip();
        int stock = inputStock.getValue() != null ? Integer.parseInt(inputStock.getValue().toString()) : 0;
        double precioCompra = inputPrecioCompra.getValue() == null ? 0 : Double.parseDouble(inputPrecioCompra.getText());
        double precioVenta = inputPrecioVenta.getValue() == null ? 0 : Double.parseDouble(inputPrecioVenta.getText());
        Categoria categoria = (Categoria) inputCategoria.getSelectedItem();
        Proveedor proveedor = (Proveedor) inputProveedor.getSelectedItem();

        return RequestProducto.addProducto(new Producto(
                nombre,
                marca,
                descripcion,
                stock,
                precioCompra,
                precioVenta,
                categoria,
                proveedor));
    }

    private Boolean toastIsEmptyCampos() throws Exception {
        if (verifyInputEmpty(inputNombre, "Nombre del Producto")) {
            return true;
        }
        if (verifyInputEmpty(inputMarca, "Marca")) {
            return true;
        }
        if (inputPrecioVenta.getValue() == null || Double.parseDouble(inputPrecioVenta.getValue().toString()) == Double.MIN_NORMAL) {
            Notify.getInstance().showToast(Toast.Type.WARNING, "Es requerido el campo Precio Venta");
            return true;
        }
        if (inputPrecioCompra.getValue() == null || Double.parseDouble(inputPrecioCompra.getValue().toString()) == Double.MIN_NORMAL) {
            Notify.getInstance().showToast(Toast.Type.WARNING, "Es requerido el campo Precio Compra");
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

    private void fillBoxCategorias() {
        EventQueue.invokeLater(() -> {
            try {
                inputCategoria.removeAllItems();
                LinkedList<Categoria> categorias = RequestCategoria.getCategoriasAll();
                for (Categoria categoria : categorias) {
                    inputCategoria.addItem(categoria);
                }
            } catch (Exception ex) {
                Logger.getLogger(PanelRequestProducto.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

    }

    private void fillBoxProveedores() {
        try {
            LinkedList<Proveedor> allProveedors = ProveedorRequest.getAllProveedors();
            for (Proveedor allProveedor : allProveedors) {
                inputProveedor.addItem(allProveedor);
            }
        } catch (Exception ex) {
            Logger.getLogger(PanelRequestProducto.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
