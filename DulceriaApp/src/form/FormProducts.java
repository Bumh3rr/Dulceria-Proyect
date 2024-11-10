package form;

import com.formdev.flatlaf.FlatClientProperties;
import components.CardProducto;
import dao.CategoriaDao;
import dao.PoolThreads;
import dao.ProductoDao;
import form.panels.PanelRequestProducto;
import form.request.RequestProducto;
import model.Producto;
import net.miginfocom.swing.MigLayout;
import system.Form;
import utils.ResponsiveLayout;


import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.function.Consumer;

import model.Categoria;
import raven.modal.ModalDialog;
import raven.modal.component.SimpleModalBorder;
import utils.ConfigModal;

public class FormProducts extends Form {

    private final String KEY = getClass().getName();
    private LinkedList<Producto> listProductos;
    private ResponsiveLayout responsiveLayout;
    private JPanel panelProductos;

    @Override
    public void formInit() {
        addListProdcuctos();
    }

    @Override
    public void formOpen() {

    }

    @Override
    public void formRefresh() {
        addListProdcuctos();
    }

    public void addListProdcuctos() {
        try {
            listProductos = RequestProducto.getAllProductos();
            refreshPanelProductos(listProductos);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public FormProducts() {
        initComponents();
        initListeners();
        init();
    }

    private void initComponents() {
        listProductos = new LinkedList<>();
    }

    private void initListeners() {

    }

    private void init() {
        setLayout(new MigLayout("wrap,fill,insets 0 n 0 n", "[fill]", "[grow 0][fill]"));
        add(createHeader("Productos", "En el apartado de Productos puedes gestionar tus Productos", 1));
        add(body());
    }

    private JComponent body() {
        JPanel panel = new JPanel(new MigLayout("fillx,wrap", "[fill]", "[][fill]"));
        JButton buton = new JButton("Agregar Producto") {
            @Override
            public boolean isDefaultButton() {
                return true;
            }
        };
        buton.putClientProperty(FlatClientProperties.STYLE, ""
                + "foreground:#FFFFFF");

        //Agregar Producto
        buton.addActionListener((e) -> {
            //Instance Panel
            PanelRequestProducto panelAdd = new PanelRequestProducto();

            ModalDialog.showModal(SwingUtilities.windowForComponent(this),
                    new SimpleModalBorder(panelAdd, "Agregar Producto", SimpleModalBorder.DEFAULT_OPTION, (controller, action) -> {
                        if (action == SimpleModalBorder.OK_OPTION) {
                            panelAdd.commitInserts(controller);
                        } else if (action == SimpleModalBorder.CANCEL_OPTION) {
                            controller.close();
                        }
                    }), ConfigModal.getModelShowDefault());
        });

        panel.add(buton, "grow 0,al trail");
        panel.add(createTechnicalContainers(), "gapx 0 2");

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
        JScrollPane scrollPane = new JScrollPane(panelProductos);
        scrollPane.putClientProperty(FlatClientProperties.STYLE, ""
                + "[light]background:darken(@background,3%);"
                + "[dark]background:lighten(@background,3%);");
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getHorizontalScrollBar().setUnitIncrement(10);
        scrollPane.getVerticalScrollBar().setUnitIncrement(10);
        scrollPane.getHorizontalScrollBar().putClientProperty(FlatClientProperties.STYLE, ""
                + "trackArc:$ScrollBar.thumbArc;"
                + "thumbInsets:0,0,0,0;"
                + "width:5;");
        scrollPane.getVerticalScrollBar().putClientProperty(FlatClientProperties.STYLE, ""
                + "trackArc:$ScrollBar.thumbArc;"
                + "thumbInsets:0,0,0,0;"
                + "width:5;");
        return scrollPane;
    }


    private void refreshPanelProductos(LinkedList<Producto> list) throws Exception {
        panelProductos.removeAll();
        for (Producto tecnico : list) {
            panelProductos.add(new CardProducto(tecnico, createEventCard()));
        }
        panelProductos.repaint();
        panelProductos.revalidate();
    }


    private Consumer<Producto> createEventCard() {
        return e -> {
//            // View Info
//            PanelInfoTechnician panel = new PanelInfoTechnician(e, this);
//            ModalDialog.showModal(GlassPanePopup.getMainFrame(),
//                    new SimpleModalBorder(panel, "Información del Tecnico", SimpleModalBorder.DEFAULT_OPTION, (controller, action) -> {
//                        if (action == SimpleModalBorder.CANCEL_OPTION) {
//                            controller.consume();
//                            panel.showDeleteTecnico(controller);
//                        } else if (action == SimpleModalBorder.CLOSE_OPTION) {
//                            controller.close();
//                        }
//                    }), Verify.option);
        };

    }


}


