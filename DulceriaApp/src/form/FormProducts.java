package form;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.components.FlatComboBox;
import components.CardProducto;
import components.Notify;
import dao.pool.PoolThreads;
import form.panels.PanelInfoProducto;
import form.panels.PanelRequestProducto;
import form.panels.PanelSearchProducto;
import form.panels.SimpleInputForms;
import form.panels.SimpleInputForms2;
import form.request.ProveedorRequest;
import form.request.RequestCategoria;
import form.request.RequestProducto;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.PopupMenu;
import model.Producto;
import net.miginfocom.swing.MigLayout;
import system.Form;
import utils.ResponsiveLayout;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;
import java.util.function.Consumer;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import model.Categoria;
import raven.modal.Drawer;
import raven.modal.ModalDialog;
import raven.modal.Toast;
import raven.modal.component.SimpleModalBorder;
import raven.modal.option.BorderOption;
import raven.modal.option.Location;
import raven.modal.option.Option;
import utils.ConfigModal;
import utils.Promiseld;
import utils.Request;

public class FormProducts extends Form {

    private final String KEY = getClass().getName();
    private LinkedList<Producto> listProductos;
    private ResponsiveLayout responsiveLayout;
    private JPanel panelProductos;
    private JScrollPane scrollProductos;

    private JButton butonAdd;
    private JButton butonSearch;
    private FlatComboBox<Object> comboBoxCategoria;
    private FlatComboBox<Object> comboBoxStatus;

    @Override
    public void formInit() {
        addListProductos();
    }

    @Override
    public void formOpen() {
        addListProductos();
    }

    @Override
    public void formRefresh() {
        addListProductos();
    }

    public void addListProductos() {
        if (Promiseld.checkPromiseId(KEY)) {
            return;
        }
        Promiseld.commit(KEY);
        PoolThreads.getInstance().getExecutorService().submit(() -> {
            try {
                listProductos = RequestProducto.getAllProductos();
                refreshPanelProductos(listProductos);
                comboBoxCategoria.setModel(new DefaultComboBoxModel<>(getCategoriasForComboBox()));
                comboBoxStatus.setModel(new DefaultComboBoxModel<>(getStatusForComboBox()));
            } catch (Exception ex) {
                Notify.getInstance().showToast(Toast.Type.ERROR, ex.getMessage());
            } finally {
                Promiseld.terminate(KEY);
            }
        });
    }

    public FormProducts() {
        initComponents();
        initListeners();
        init();
    }

    private void initComponents() {
        listProductos = new LinkedList<>();
        comboBoxCategoria = new FlatComboBox<>();
        comboBoxCategoria.setMaximumRowCount(8);

        comboBoxStatus = new FlatComboBox<>();
        comboBoxStatus.setMaximumRowCount(8);
        butonSearch = new JButton("Buscar Producto");
        butonAdd = new JButton("Agregar Producto") {
            @Override
            public boolean isDefaultButton() {
                return true;
            }
        };
    }

    private void initListeners() {
        comboBoxCategoria.addActionListener((e) -> aplicarFiltro());
        comboBoxStatus.addActionListener((e) -> aplicarFiltro());

        butonSearch.addActionListener((e) -> searchProducto());
    }

    private void aplicarFiltro() {
        try {
            Categoria categoriaSelect = comboBoxCategoria.getSelectedItem() instanceof Categoria
                    ? (Categoria) comboBoxCategoria.getSelectedItem() : null;
            Producto.Status estadoSelect = comboBoxStatus.getSelectedItem() instanceof Producto.Status
                    ? (Producto.Status) comboBoxStatus.getSelectedItem() : null;

            listProductos = RequestProducto.getProductsByCategoriaAndEstado(categoriaSelect, estadoSelect);
            refreshPanelProductos(listProductos);
        } catch (Exception ex) {
            System.out.println(ex.getLocalizedMessage());
        }
    }

    private Object[] getCategoriasForComboBox() throws Exception {
        LinkedList<Categoria> categorias = RequestCategoria.getCategoriasAll();
        List<Object> items = new LinkedList<>();
        items.add("All");
        items.addAll(categorias);
        return items.toArray();
    }

    private Object[] getStatusForComboBox() {
        List<Object> items = new LinkedList<>();
        items.add("All");
        items.add(Producto.Status.Disponible);
        items.add(Producto.Status.Agotado);
        return items.toArray();
    }

    private void init() {
        setLayout(new MigLayout("wrap,fill,insets 0 n 0 n", "[fill]", "[grow 0][fill]"));
        add(createHeader("Productos", "En el apartado de Productos puedes gestionar tus Productos", 1));
        add(body());
    }

    private JComponent body() {
        JPanel panel = new JPanel(new MigLayout("fillx,wrap", "[fill]"));

        butonAdd.putClientProperty(FlatClientProperties.STYLE, ""
                + "foreground:#FFFFFF");

        //Agregar Producto
        butonAdd.addActionListener((e) -> {
            //Instance Panel
            PanelRequestProducto panelAdd = new PanelRequestProducto(Request.INSERTS);
            ModalDialog.showModal(SwingUtilities.windowForComponent(this),
                    new SimpleModalBorder(panelAdd, "Agregar Producto", SimpleModalBorder.DEFAULT_OPTION, (controller, action) -> {
                        if (action == SimpleModalBorder.OK_OPTION) {
                            controller.consume();
                            panelAdd.commitInserts(controller);
                        } else if (action == SimpleModalBorder.PROPERTIES) {
                            Drawer.setSelectedItemClass(FormProveedor.class);
                            controller.close();
                        } else if (action == SimpleModalBorder.CANCEL_OPTION) {
                            controller.close();
                        }
                    }), ConfigModal.getModelShowDefault());
        });

        panel.add(createComboxs(), "split 2,grow 0,al left");
        panel.add(butonAdd, "grow 0,al trail");
        panel.add(createTechnicalContainers(), "gapx 0 2");
        return panel;
    }

    private JComponent createComboxs() {
        JPanel panel = new JPanel(new MigLayout("fillx,insets 3", "[fill,grow 0][][fill,grow 0][]"));
        panel.add(new JLabel("Categorias:"));
        panel.add(comboBoxCategoria, "w 150!");
        panel.add(new JLabel("Estado:"));
        panel.add(comboBoxStatus, "w 150!");
        panel.add(butonSearch,"grow 0");
        return panel;
    }

    private JComponent createTechnicalContainers() {
        responsiveLayout = new ResponsiveLayout(ResponsiveLayout.JustifyContent.FIT_CONTENT, new Dimension(-1, -1), 10, 10);
        panelProductos = new JPanel(responsiveLayout);
        panelProductos.putClientProperty(FlatClientProperties.STYLE, ""
                + "[light]background:darken(@background,3%);"
                + "[dark]background:lighten(@background,3%)");
        panelProductos.putClientProperty(FlatClientProperties.STYLE, ""
                + "border:10,10,10,10;");
        scrollProductos = new JScrollPane(panelProductos);
        scrollProductos.putClientProperty(FlatClientProperties.STYLE, ""
                + "[light]background:darken(@background,3%);"
                + "[dark]background:lighten(@background,3%);");
        scrollProductos.setBorder(BorderFactory.createEmptyBorder());
        scrollProductos.getHorizontalScrollBar().setUnitIncrement(10);
        scrollProductos.getVerticalScrollBar().setUnitIncrement(10);
        scrollProductos.getHorizontalScrollBar().putClientProperty(FlatClientProperties.STYLE, ""
                + "trackArc:$ScrollBar.thumbArc;"
                + "thumbInsets:0,0,0,0;"
                + "width:5;");
        scrollProductos.getVerticalScrollBar().putClientProperty(FlatClientProperties.STYLE, ""
                + "trackArc:$ScrollBar.thumbArc;"
                + "thumbInsets:0,0,0,0;"
                + "width:5;");
        return scrollProductos;
    }

    private void refreshPanelProductos(LinkedList<Producto> list) throws Exception {
        panelProductos.removeAll();
        for (Producto tecnico : list) {
            panelProductos.add(new CardProducto(tecnico, createEventCard()));
        }
        panelProductos.repaint();
        panelProductos.revalidate();
        EventQueue.invokeLater(() -> scrollProductos.getVerticalScrollBar().setValue(0));
    }

    private Consumer<Producto> createEventCard() {
        return e -> {
            // View Info
            PanelInfoProducto panel = new PanelInfoProducto(e, this);
            ModalDialog.showModal(this,
                    new SimpleModalBorder(panel, "Información del Producto", SimpleModalBorder.DEFAULT_OPTION, (controller, action) -> {
                        if (action == SimpleModalBorder.CLOSE_OPTION) {
                            controller.close();
                        }
                    }), ConfigModal.getModelShowDefault());
        };

    }

    private void searchProducto() {
        final String id = "input";
        PanelSearchProducto panel = new PanelSearchProducto();
        ModalDialog.showModal(this, new SimpleModalBorder(
                panel, "Busqueda de Producto", SimpleModalBorder.DEFAULT_OPTION,
                (controller, action) -> {
                    if (action == SimpleModalBorder.OK_OPTION) {
                        controller.consume();
                        panel.searchProducto();
                    }else if(action  == SimpleModalBorder.CANCEL_OPTION){
                        controller.close();
                    }
                }), ConfigModal.getModelShowDefault(), id);
    }
    
    

    

}
