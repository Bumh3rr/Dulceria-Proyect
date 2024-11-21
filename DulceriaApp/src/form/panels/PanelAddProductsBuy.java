package form.panels;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.components.FlatSpinner;
import components.FieldSearch;
import components.MyScrollPane;
import form.request.RequestProducto;
import modal.ConfigModal;
import modal.cards.CardProductBuy;
import modal.cards.CardProducto;
import model.Producto;
import net.miginfocom.swing.MigLayout;
import raven.modal.ModalDialog;
import raven.modal.component.SimpleModalBorder;
import utils.ResponsiveLayout;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.function.Consumer;

import static form.panels.PanelRequestVenta.listProducts;

public class PanelAddProductsBuy extends JPanel {
    private FieldSearch inputSearch;
    private JButton buttonBack;

    private JButton buttonShowProductsAll;
    private JButton buttonSearch;

    private ResponsiveLayout responsiveLayout;
    private JPanel panelProductos;
    private JScrollPane scrollProductos;

    public PanelAddProductsBuy() {
        initComponents();
        initListeners();
        initUI();

        try {
            listProducts =  RequestProducto.getAllProductos();
            refreshPanelProductos(listProducts);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    private void initComponents() {
        buttonShowProductsAll = new JButton("Show All");
        inputSearch = new FieldSearch();
        buttonBack = new JButton("Volver");
        buttonSearch = new JButton("Buscar") {
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
        buttonShowProductsAll.addActionListener(e -> {

        });

    }

    private void initUI() {
        setLayout(new MigLayout("fill,insets 0 n n n"));
        JPanel panel = new JPanel(new MigLayout("wrap,fillx,insets n,width 850:1300", "fill", "[]20[]"));
        panel.add(createPanelSeach(),"");
        panel.add(createProductsContainers(),"h 500!");
        panel.add(createDetails());
        panel.add(buttonBack, "grow 0,al trail");
        add(new MyScrollPane(panel));
        updateUI();
        revalidate();
    }

    private JComponent createDetails() {
        JPanel panel = new JPanel(new MigLayout("wrap,fillx,insets 10", "fill", "[]20[]"));
        panel.putClientProperty(FlatClientProperties.STYLE, ""
                + "background:null");
        panel.add(new JLabel("Detalles de la Venta"), "wrap,al lead");
        panel.add(new JLabel("Productos Seleccionados"), "wrap,al lead");
        panel.add(new JLabel("Total a Pagar"), "wrap,al lead");
        return panel;
    }

    private JComponent createPanelSeach() {
        JPanel panel = new JPanel(new MigLayout("fillx, insets 3","[grow,fill]5[grow 0][grow 0]",""));
        panel.add(inputSearch);
        panel.add(buttonSearch);
        panel.add(buttonShowProductsAll);
        return panel;
    }


    private JComponent createProductsContainers() {
        responsiveLayout = new ResponsiveLayout(ResponsiveLayout.JustifyContent.START, new Dimension(-1, -1), 10, 10);
        responsiveLayout.setColumn(7);
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
        for (Producto producto : list) {
            panelProductos.add(new CardProductBuy(producto, createEventCard()));
        }
        panelProductos.repaint();
        panelProductos.revalidate();
        EventQueue.invokeLater(() -> scrollProductos.getVerticalScrollBar().setValue(0));
    }

    private Consumer<Producto> createEventCard() {
        return e -> {
            // Ver información
//            PanelInfoProducto panel = new PanelInfoProducto(e, this);
//            ModalDialog.showModal(this,
//                    new SimpleModalBorder(panel, "Información del Producto", SimpleModalBorder.DEFAULT_OPTION, (controller, action) -> {
//                        if (action == SimpleModalBorder.CLOSE_OPTION) {
//                            controller.close();
//                        }
//                    }), ConfigModal.getModelShowDefault());
        };

    }
}
