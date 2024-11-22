package form.panels;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.components.FlatScrollPane;
import com.formdev.flatlaf.extras.components.FlatTable;
import components.FieldSearch;
import components.MyScrollPane;
import form.request.RequestProducto;
import modal.cards.CardProductBuy;
import model.Producto;
import net.miginfocom.swing.MigLayout;
import raven.modal.ModalDialog;
import utils.ResponsiveLayout;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
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

    private Table panelTable;

    public PanelAddProductsBuy() {
        initComponents();
        initListeners();
        initUI();

        try {
            listProducts = RequestProducto.getAllProductos();
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
        panelTable = new Table();

    }

    private void initListeners() {
        buttonShowProductsAll.addActionListener(e -> {

        });

        buttonBack.addActionListener((e)->{
            ModalDialog.popModel(PanelRequestVenta.ID);
        });

    }

    private void initUI() {
        setLayout(new MigLayout("fill,insets 0 n n n"));
        JPanel panel = new JPanel(new MigLayout("wrap,fillx,insets n,width 850:1300", "fill", "[]20[]"));
        panel.add(createPanelSeach(), "");
        panel.add(createProductsContainers(), "h 250!");
        panel.add(panelTable,"grow");
        panel.add(buttonBack, "grow 0,al trail");
        add(new MyScrollPane(panel));
        updateUI();
        revalidate();
    }

    private JComponent createPanelSeach() {
        JPanel panel = new JPanel(new MigLayout("fillx, insets 3", "[grow,fill]5[grow 0][grow 0]", ""));
        panel.add(inputSearch);
        panel.add(buttonSearch);
        panel.add(buttonShowProductsAll);
        return panel;
    }

    private JComponent createProductsContainers() {
        responsiveLayout = new ResponsiveLayout(ResponsiveLayout.JustifyContent.FIT_CONTENT, new Dimension(-1, -1), 10, 10);
//        responsiveLayout.setColumn(5);
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

    private Consumer<Producto.ProductoSelect> createEventCard() {
        return e -> {
           PanelRequestVenta.listProductsSelect.add(e);
            panelTable.setData(PanelRequestVenta.listProductsSelect);
        };
    }


    public static class Table extends JPanel {

        private JTable table;
        private JScrollPane scrollPane;
        private DefaultTableModel model;
        private String[] columnNames = {"ID Producto", "Nombre", "Cantidad", "Preció venta", "Precio Total"};

        /**
         * Constructor de Table.
         */
        public Table() {
            initComponentsTable();
            initTable();
        }

        /**
         * Inicializa los componentes de la tabla.
         */
        private void initComponentsTable() {
            table = new FlatTable();
            scrollPane = new FlatScrollPane();
            model = new DefaultTableModel(columnNames, 0) {
                boolean[] canEdit = new boolean[]{
                        false, false, false, false, false
                };

                public boolean isCellEditable(int rowIndex, int columnIndex) {
                    return canEdit[columnIndex];
                }
            };
        }

        /**
         * Inicializa la tabla.
         */
        private void initTable() {
            setLayout(new MigLayout("wrap,fillx,insets n", "fill","grow 0"));
            putClientProperty(FlatClientProperties.STYLE, ""
                    + "arc:16;"
                    + "background:$Table.background");

            scrollPane.setBorder(BorderFactory.createEmptyBorder());
            table.setModel(model);

            //Size fields
            table.getColumnModel().getColumn(0).setMaxWidth(60);
            table.getColumnModel().getColumn(1).setPreferredWidth(90);
            table.getColumnModel().getColumn(2).setPreferredWidth(60);
            table.getColumnModel().getColumn(3).setPreferredWidth(50);
            table.getColumnModel().getColumn(4).setPreferredWidth(50);


            //Center Data
            DefaultTableCellRenderer defaultTableCellRenderer = new DefaultTableCellRenderer();
            defaultTableCellRenderer.setHorizontalAlignment(SwingConstants.CENTER);
            for (int i = 0; i < table.getColumnCount(); i++) {
                table.getColumnModel().getColumn(i).setCellRenderer(defaultTableCellRenderer);
            }

            //Styles
            table.getTableHeader().putClientProperty(FlatClientProperties.STYLE, ""
                    + "height:30;"
                    + "hoverBackground:null;"
                    + "pressedBackground:fade($Component.accentColor,5%);"
                    + "separatorColor:$TableHeader.background;");
            table.putClientProperty(FlatClientProperties.STYLE, ""
                    + "rowHeight:40;"
                    + "showHorizontalLines:true;"
                    + "intercellSpacing:0,1;"
                    + "selectionArc:20;"
                    + "cellFocusColor:$TableHeader.hoverBackground;"
                    + "selectionBackground:fade($Component.accentColor,10%);"
                    + "selectionInactiveBackground:$TableHeader.hoverBackground;"
                    + "selectionForeground:$Table.foreground;");

            scrollPane.setViewportView(table);

            add(scrollPane);
            updateUI();
            revalidate();
        }

        /**
         * Establece los datos de la tabla.
         *
         * @param listProductos una lista de objetos Producto.ProductoSelect
         */
        public void setData(LinkedList<Producto.ProductoSelect> listProductos) {
            cleanData();
            for (Producto.ProductoSelect productos : listProductos) {
                model.addRow(productos.toObject());
            }
        }

        /**
         * Limpia los datos de la tabla.
         */
        public void cleanData() {
            while (model.getRowCount() > 0) {
                model.removeRow(0);
            }
        }
    }

}
