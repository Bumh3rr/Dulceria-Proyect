package form.panels;

import com.formdev.flatlaf.FlatClientProperties;
import components.FieldTextArea;
import components.MyLabelTitle;
import components.MyTxtAreaDescrip;
import dao.pool.PoolThreads;
import form.FormProducts;
import form.FormProveedor;
import form.request.RequestProducto;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingUtilities;
import model.Producto;
import net.miginfocom.swing.MigLayout;
import raven.extras.AvatarIcon;
import raven.modal.Drawer;
import raven.modal.ModalDialog;
import raven.modal.component.ModalBorderAction;
import raven.modal.component.SimpleModalBorder;
import utils.ConfigModal;
import utils.Request;

public class PanelInfoProducto extends JPanel {

    private FormProducts form;
    private Producto producto;
    private final String KEY = getClass().getName();

    private FieldTextArea fieldProductNombre;
    private FieldTextArea fieldMarca;
    private FieldTextArea fieldDescription;
    private FieldTextArea fieldStock;
    private FieldTextArea fieldPrecio_Venta;
    private FieldTextArea fieldPrecio_Compra;
    private FieldTextArea fieldIdProveedor;
    private FieldTextArea fieldNombreProveedor;
    private FieldTextArea fieldCategoria;

    private JButton buttonUpdate;
    private JButton buttonDelete;
    private JButton buttonClose;

    public PanelInfoProducto(Producto producto, FormProducts form) {
        this.producto = producto;
        this.form = form;
        intComponents();
        initListeners();
        init();
    }

    public void refreshFields() {
        try {
            this.producto = RequestProducto.getOneProducto(producto.getId());

            fieldProductNombre.setTextField(producto.getNombre());
            fieldMarca.setTextField(producto.getMarca());
            fieldDescription.setTextField(producto.getDescripcion());
            fieldStock.setTextField(String.valueOf(producto.getStock()));
            fieldPrecio_Venta.setTextField("$".concat(String.valueOf(producto.getPrecio_Venta())));
            fieldPrecio_Compra.setTextField("$".concat(String.valueOf(producto.getPrecio_Compra())));
            fieldIdProveedor.setTextField(String.valueOf(producto.getProveedor().getId()));
            fieldNombreProveedor.setTextField(producto.getProveedor().getFirst_name());
            fieldCategoria.setTextField(producto.getCategoria().getTipo());

        } catch (Exception e) {
            System.out.println(e.fillInStackTrace());
        }

    }

    private void intComponents() {

        fieldProductNombre = new FieldTextArea(producto.getNombre());
        fieldMarca = new FieldTextArea(producto.getMarca());
        fieldDescription = new FieldTextArea(producto.getDescripcion());
        fieldStock = new FieldTextArea(String.valueOf(producto.getStock()));
        fieldPrecio_Venta = new FieldTextArea("$".concat(String.valueOf(producto.getPrecio_Venta())));
        fieldPrecio_Compra = new FieldTextArea("$".concat(String.valueOf(producto.getPrecio_Compra())));
        fieldIdProveedor = new FieldTextArea(String.valueOf(producto.getProveedor().getId()));
        fieldNombreProveedor = new FieldTextArea(producto.getProveedor().getFirst_name());
        fieldCategoria = new FieldTextArea(producto.getCategoria().getTipo());

        buttonUpdate = new JButton("Actualizar");
        buttonDelete = new JButton("Eliminar");
        buttonClose = new JButton("Cerrar") {
            @Override
            public boolean isDefaultButton() {
                return true;
            }
        };

    }

    private void initListeners() {
        buttonUpdate.addActionListener((e) -> {
            PanelRequestProducto panelAdd = new PanelRequestProducto(Request.INSERTS, producto, this);
            ModalDialog.showModal(SwingUtilities.windowForComponent(this),
                    new SimpleModalBorder(panelAdd, "Agregar Producto", SimpleModalBorder.DEFAULT_OPTION, (controller, action) -> {
                        if (action == SimpleModalBorder.OK_OPTION) {
                            panelAdd.commitInserts(controller);
                        } else if (action == SimpleModalBorder.CANCEL_OPTION) {
                            controller.close();
                        }
                    }), ConfigModal.getModelShowDefault());

        });

        buttonDelete.addActionListener((e) -> {
            ModalBorderAction.getModalBorderAction(buttonDelete).doAction(SimpleModalBorder.CANCEL_OPTION);
        });
        buttonClose.addActionListener((e) -> {
            ModalBorderAction.getModalBorderAction(buttonClose).doAction(SimpleModalBorder.CLOSE_OPTION);
        });
    }

    private void init() {
        setLayout(new MigLayout("fill,wrap,insets 0 n 0 n", "fill,450!"));
        add(new JLabel(new AvatarIcon(getClass().getResource("/resources/dulce.png"), 100, 100, 16)), "split 2,grow 0");
        add(createHeader("Producto", "ID: " + producto.getId(), 1));
        add(body());
    }

    public JComponent createHeader(String title, String description, int size) {
        JPanel panel = new JPanel(new MigLayout("fill,wrap,insets 5 10 5 10", "[fill]"));
        panel.add(new MyLabelTitle(title, JLabel.LEFT, (4 - size)));
        panel.add(new MyTxtAreaDescrip(description), "grow 0");
        return panel;
    }

    private JComponent body() {
        JPanel panel = new JPanel(new MigLayout("wrap 2,fillx,insets 0 n 0 n", "fill", "fill"));

        buttonDelete.putClientProperty(FlatClientProperties.STYLE, ""
                + "background:#FF5733;"
                + "foreground:#FFFFFF;"
                + "font:bold +0");
        buttonUpdate.putClientProperty(FlatClientProperties.STYLE, ""
                + "background:#FDC211;"
                + "foreground:#FFFFFF;"
                + "font:bold +0");

        buttonClose.putClientProperty(FlatClientProperties.STYLE, ""
                + "foreground:#FFFFFF");

        JLabel subTitleProduct = new JLabel("DATOS DEL PRODUCTO");
        subTitleProduct.putClientProperty(FlatClientProperties.STYLE, ""
                + "font: bold +1;"
                + "[light]foreground:tint($Label.foreground,30%);");

        panel.add(new JSeparator(), "span 2, grow 1");
        panel.add(subTitleProduct, "span 2,grow 0,wrap 10,al center");
        panel.add(getLabelSubTitle("Nombre:"));
        panel.add(fieldProductNombre);
        panel.add(getLabelSubTitle("Marca:"));
        panel.add(fieldMarca);
        panel.add(getLabelSubTitle("Descripcion:"));
        panel.add(fieldDescription);
        panel.add(getLabelSubTitle("Unidades Disponibles:"));
        panel.add(fieldStock);
        panel.add(getLabelSubTitle("Categoria:"));
        panel.add(fieldCategoria);
        panel.add(getLabelSubTitle("Precio Compra:"));
        panel.add(fieldPrecio_Compra);
        panel.add(getLabelSubTitle("Precio Venta:"));
        panel.add(fieldPrecio_Venta, "wrap");

        JLabel subTitleProv = new JLabel("DATOS DEL PROVEEDOR");
        subTitleProv.putClientProperty(FlatClientProperties.STYLE, ""
                + "font: bold +1;"
                + "[light]foreground:tint($Label.foreground,30%);");

        panel.add(new JSeparator(), "span 2, grow 1");
        panel.add(subTitleProv, "span 2,grow 0,,wrap 10,al center");
        panel.add(getLabelSubTitle("ID:"));
        panel.add(fieldIdProveedor);
        panel.add(getLabelSubTitle("Nombre:"));
        panel.add(fieldNombreProveedor);

        panel.add(new JSeparator(), "span 2, grow 1");
        panel.add(createAccions(), "span 2,grow 0,al center");

        return panel;
    }

    private JComponent createAccions() {
        JPanel panel = new JPanel(new MigLayout("fill,insets n", "fill"));
        panel.add(buttonUpdate);
        panel.add(buttonDelete);
        panel.add(buttonClose);
        return panel;
    }

    private JLabel getLabelSubTitle(String title) {
        JLabel label = new JLabel(title, JLabel.TRAILING);
        label.putClientProperty(FlatClientProperties.STYLE, ""
                + "[light]foreground:lighten(@foreground,30%);"
                + "[dark]foreground:darken(@foreground,30%);"
                + "font:13");

        return label;
    }
}
