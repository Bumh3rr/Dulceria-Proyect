package form.panels;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.extras.components.FlatComboBox;
import components.MyJTextField;
import components.MyScrollPane;
import components.MyTxtAreaDescrip;
import form.FormProducts;
import form.FormProveedor;
import model.Producto;
import net.miginfocom.swing.MigLayout;
import raven.modal.component.ModalBorderAction;
import raven.modal.component.SimpleModalBorder;
import javax.swing.*;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import java.text.NumberFormat;
import java.util.LinkedList;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Proveedor;

public class PanelRequestProducto extends JPanel {
    private final String KEY = getClass().getName();
    private MyTxtAreaDescrip description;
    private Producto producto;

    private MyJTextField inputNombre;
    private MyJTextField inputMara;
    private MyJTextField inputDescripcion;
    private JFormattedTextField inputStock;
    private JFormattedTextField inputPrecioCompra;
    private JFormattedTextField inputPrecioVenta;
    private FlatComboBox<String> inputCategoria;
    private FlatComboBox<Proveedor> inputProveedor;
    private JButton botton;


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
    }

    private void initComponents() {

        NumberFormat numberFormat = NumberFormat.getIntegerInstance();
        NumberFormatter numberFormatter = new NumberFormatter(numberFormat);
        numberFormatter.setValueClass(Integer.class);
        numberFormatter.setAllowsInvalid(false);
        numberFormatter.setMinimum(0);
        // Opcional: establece un mínimo de 0 (sin números negativos)
        NumberFormatter numberFormatterFloat = new NumberFormatter(NumberFormat.getIntegerInstance());
        numberFormatterFloat.setValueClass(Float.class);
        numberFormatterFloat.setAllowsInvalid(false);
        numberFormatterFloat.setMinimum(0); // Opcional: establece un mínimo de 0 (sin números negativos)


        description = new MyTxtAreaDescrip("Agregar Producto, Agrega un nuevo producto a la base de datos");

        inputNombre = new MyJTextField();
        inputMara = new MyJTextField();
        inputDescripcion = new MyJTextField();

        inputStock = new JFormattedTextField();
        inputStock.setFormatterFactory(new DefaultFormatterFactory(numberFormatter));

        inputPrecioCompra = new JFormattedTextField();
        inputPrecioCompra.setFormatterFactory(new DefaultFormatterFactory(numberFormatterFloat));

        inputPrecioVenta = new JFormattedTextField();
        inputPrecioVenta.setFormatterFactory(new DefaultFormatterFactory(numberFormatterFloat));

        inputCategoria = new FlatComboBox<>();
        inputCategoria.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Selecione la Categoria"}));
        inputCategoria.setMaximumRowCount(8);

        inputProveedor = new FlatComboBox<>();
        inputProveedor.setMaximumRowCount(8);

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

        description.putClientProperty(FlatClientProperties.STYLE, ""
                + "[light]foreground:lighten(@foreground,30%);"
                + "[dark]foreground:darken(@foreground,30%);"
                + "background:null");

        inputNombre.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Nombre del Producto");
        inputNombre.putClientProperty(FlatClientProperties.TEXT_FIELD_LEADING_ICON, new FlatSVGIcon("resources/icon/ic_name.svg", 0.35f));
        inputNombre.putClientProperty(FlatClientProperties.STYLE, ""
                + "iconTextGap:10;"
                + "showClearButton:true");

        inputMara.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Marca");
        inputMara.putClientProperty(FlatClientProperties.TEXT_FIELD_LEADING_ICON, new FlatSVGIcon("resources/icon/ic_name.svg", 0.35f));
        inputMara.putClientProperty(FlatClientProperties.STYLE, ""
                + "iconTextGap:10;"
                + "showClearButton:true");

        inputDescripcion.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Descripcion del Producto");
        inputDescripcion.putClientProperty(FlatClientProperties.TEXT_FIELD_LEADING_ICON, new FlatSVGIcon("resources/icon/ic_email.svg", 0.35f));
        inputDescripcion.putClientProperty(FlatClientProperties.STYLE, ""
                + "iconTextGap:10;"
                + "showClearButton:true");

        inputStock.putClientProperty(FlatClientProperties.TEXT_FIELD_LEADING_ICON, new FlatSVGIcon("resources/icon/ic_address.svg", 0.35f));
        inputStock.putClientProperty(FlatClientProperties.STYLE, ""
                + "iconTextGap:10;"
                + "showClearButton:true");

        inputPrecioCompra.putClientProperty(FlatClientProperties.TEXT_FIELD_LEADING_ICON, new FlatSVGIcon("resources/icon/ic_zipcode.svg", 0.35f));
        inputPrecioCompra.putClientProperty(FlatClientProperties.STYLE, ""
                + "iconTextGap:10;"
                + "showClearButton:true");
        
        inputPrecioVenta.putClientProperty(FlatClientProperties.TEXT_FIELD_LEADING_ICON, new FlatSVGIcon("resources/icon/ic_zipcode.svg", 0.35f));
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
        panel.add(inputCategoria);
        panel.add(new JLabel("Proveedor"));
        panel.add(inputProveedor);
        panel.add(botton, "grow 0,gapy 10,al trail");

        add(new MyScrollPane(panel));
        updateUI();
        revalidate();
    }

    private void fillBoxCategorias() {
        try {
            LinkedList<Proveedor> allProveedors = FormProveedor.ProveedorRequest.getAllProveedors();
            for (Proveedor allProveedor : allProveedors) {
                inputProveedor.addItem(allProveedor);
            }
        } catch (Exception ex) {
            Logger.getLogger(PanelRequestProducto.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    private void fillBoxProveedores() {
        
    }

}
