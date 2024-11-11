package form.panels;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.extras.components.FlatComboBox;
import components.MyJTextField;
import components.MyScrollPane;
import components.MyTxtAreaDescrip;
import components.Notify;
import form.FormProducts;
import form.FormProveedor;
import form.request.RequestCategoria;
import form.request.RequestProducto;
import java.awt.EventQueue;
import java.text.DecimalFormat;
import model.Producto;
import net.miginfocom.swing.MigLayout;
import raven.modal.Drawer;
import raven.modal.component.ModalBorderAction;
import raven.modal.component.SimpleModalBorder;
import javax.swing.*;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import java.text.NumberFormat;
import java.time.LocalDateTime;
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
import utils.EstadosMx;

public class PanelRequestProducto extends JPanel {
    
    private final String KEY = getClass().getName();
    private MyTxtAreaDescrip description;
    
    private MyJTextField inputNombre;
    private MyJTextField inputMara;
    private MyJTextField inputDescripcion;
    private JFormattedTextField inputStock;
    private JFormattedTextField inputPrecioCompra;
    private JFormattedTextField inputPrecioVenta;
    private FlatComboBox<Categoria> inputCategoria;
    private FlatComboBox<Proveedor> inputProveedor;
    private JButton botton;
    private JButton buttonAddCategoria;
    private JButton buttonAddProveedor;

    public PanelRequestProducto() {
        initComponents();
        initListeners();
        init();
        fillBoxCategorias();
        fillBoxProveedores();
    }
    
    private void initListeners() {
        botton.addActionListener((e) -> {
            ModalBorderAction.getModalBorderAction(botton).doAction(SimpleModalBorder.OK_OPTION);
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
        buttonAddProveedor.addActionListener((e)-> Drawer.setSelectedItemClass(FormProveedor.class));
    }
    
    private void initComponents() {
        
        NumberFormatter numberFormatter = new NumberFormatter(NumberFormat.getIntegerInstance());
//        NumberFormatter numberFormatter = new NumberFormatter(NumberFormat.getIntegerInstance());
        numberFormatter.setValueClass(Integer.class);
        numberFormatter.setMinimum(0);
        numberFormatter.setMaximum(Integer.MAX_VALUE);
        numberFormatter.setAllowsInvalid(false);
        
        NumberFormatter decimalFormatter = new NumberFormatter(new DecimalFormat("#,##0.00"));
        decimalFormatter.setValueClass(Double.class);
        decimalFormatter.setMinimum(0.0);
        decimalFormatter.setMaximum(Double.MAX_VALUE);
        decimalFormatter.setAllowsInvalid(false);
        
        description = new MyTxtAreaDescrip("Agregar Producto, Agrega un nuevo producto a la base de datos");
        
        inputNombre = new MyJTextField();
        inputMara = new MyJTextField();
        inputDescripcion = new MyJTextField();
        
        inputStock = new JFormattedTextField();
        inputStock.setFormatterFactory(new DefaultFormatterFactory(numberFormatter));
        
        inputPrecioCompra = new JFormattedTextField();
        inputPrecioCompra.setFormatterFactory(new DefaultFormatterFactory(decimalFormatter));
        
        inputPrecioVenta = new JFormattedTextField();
        inputPrecioVenta.setFormatterFactory(new DefaultFormatterFactory(decimalFormatter));
        
        inputCategoria = new FlatComboBox<>();
        inputCategoria.setMaximumRowCount(8);
        
        inputProveedor = new FlatComboBox<>();
        inputProveedor.setMaximumRowCount(8);

        buttonAddProveedor = new JButton("Agregar Proveedor");
        buttonAddCategoria = new JButton("Agregar Categoria");
        botton = new JButton("Agregar Producto") {
            @Override
            public boolean isDefaultButton() {
                return true;
            }
        };
    }
    
    private void init() {
        setLayout(new MigLayout("fillx,insets 0", "[center]", "[center]"));
        
        JPanel panel = new JPanel(new MigLayout("wrap,fillx,insets 0 45 0 45", "fill,400!"));
        
        botton.putClientProperty(FlatClientProperties.STYLE, "" + "foreground:#FFFFFF");
        buttonAddCategoria.putClientProperty(FlatClientProperties.STYLE, "" + "foreground:#4f4f4f");
        
        description.putClientProperty(FlatClientProperties.STYLE, ""
                + "[light]foreground:lighten(@foreground,30%);"
                + "[dark]foreground:darken(@foreground,30%);"
                + "background:null");
        
        inputNombre.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Nombre del Producto");
        inputNombre.putClientProperty(FlatClientProperties.STYLE, ""
                + "showClearButton:true");
        
        inputMara.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Marca");
        inputMara.putClientProperty(FlatClientProperties.STYLE, ""
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
        panel.add(inputMara);
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
        panel.add(botton, "grow 0,gapy 10,al trail");
        
        add(new MyScrollPane(panel));
        updateUI();
        revalidate();
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
//                        new Thread(() -> form.formOpen()).start();
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
        String marca = inputMara.getText().strip();
        String descripcion = inputDescripcion.getText().strip();
        int stock = inputStock.getValue() == null ? 0 : Integer.parseInt(inputStock.getText());
        double precioCompra = inputPrecioCompra.getValue() == null ? 0 : Double.parseDouble(inputPrecioCompra.getText());
        double precioVenta = inputPrecioVenta.getValue() == null ? 0 : Double.parseDouble(inputPrecioVenta.getText());
        Categoria categoria = (Categoria) inputCategoria.getSelectedItem();
        Proveedor proveedor = (Proveedor) inputProveedor.getSelectedItem();
        
        Producto producto = new Producto(
                nombre,
                marca,
                descripcion,
                stock,
                precioCompra,
                precioVenta,
                categoria,
                proveedor);
        
        System.out.println(producto);
        
        if (producto.verifyNotEmpty()) {
            return RequestProducto.addProducto(producto);
        }
        return false;
    }
    
    private Boolean toastIsEmptyCampos() throws Exception {
        if (verifyInputEmpty(inputNombre, "Nombre del Producto")) {
            return true;
        }
        if (verifyInputEmpty(inputMara, "Marca")) {
            return true;
        }
        if (verifyInputEmpty(inputStock, "Unidades")) {
            return true;
        }
        if (verifyInputEmpty(inputPrecioVenta, "Precio Venta")) {
            return true;
        }
        if (verifyInputEmpty(inputPrecioCompra, "Precio Compra")) {
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
                return false;
            }
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
            LinkedList<Proveedor> allProveedors = FormProveedor.ProveedorRequest.getAllProveedors();
            for (Proveedor allProveedor : allProveedors) {
                inputProveedor.addItem(allProveedor);
            }
        } catch (Exception ex) {
            Logger.getLogger(PanelRequestProducto.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
