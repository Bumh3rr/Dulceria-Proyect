package form;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.components.FlatScrollPane;
import com.formdev.flatlaf.extras.components.FlatTable;
import components.Notify;
import dao.pool.PoolThreads;
import dao.request.RequestDetalleVenta;
import form.panels.PanelInfoVenta;
import form.panels.PanelRequestVenta;
import dao.request.RequestVenta;

import java.util.LinkedList;

import modal.ConfigModal;
import modal.CustomModal;
import model.DetalleVenta;
import net.miginfocom.swing.MigLayout;
import raven.modal.ModalDialog;
import system.Form;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.JComponent;
import javax.swing.BorderFactory;
import javax.swing.table.DefaultTableCellRenderer;

import model.Venta;
import raven.modal.Toast;
import system.FormManager;
import utils.Promiseld;

public class FormBuys extends Form {

    private final String KEY = "FormBuys";
    private Table panelTable;
    private JButton button_view;
    private JButton button_create;
    private LinkedList<Venta> listSale;

    @Override
    public void formInit() {
        addListSale();
    }

    @Override
    public void formRefresh() {
        addListSale();
    }

    public void addListSale() {
        if (Promiseld.checkPromiseId(KEY)) {
            return;
        }
        Promiseld.commit(KEY);
        PoolThreads.getInstance().getExecutorService().submit(() -> {
            try {
                listSale = RequestVenta.getSaleAll();
                panelTable.setData(listSale);
            } catch (Exception ex) {
                Notify.getInstance().showToast(Toast.Type.ERROR, ex.getMessage());
            } finally {
                Promiseld.terminate(KEY);
            }
        });
    }

    public FormBuys() {
        initComponents();
        initListeners();
        init();
    }

    private void initComponents() {
        panelTable = new Table();
        button_view = new JButton("Visualizar Venta");
        button_create = new JButton("Realizar Venta") {
            @Override
            public boolean isDefaultButton() {
                return true;
            }
        };
    }

    private void initListeners() {
        button_create.addActionListener(e -> showPanelNewBuy());
        button_view.addActionListener(e -> methodViewInfoBuy());
    }


    private void init() {
        setLayout(new MigLayout("fillx,wrap,insets 7 15 7 15", "[fill]"));
        add(super.createHeader("Ventas", "Administra la información de proveedores y empleados de manera sencilla y organizada. Visualiza, agrega y edita los datos según tus necesidades.", 1));
        add(body(), "grow,push,gapx 7 7");
        updateUI();
        revalidate();
    }

    private JComponent body() {
        JPanel panel = new JPanel(new MigLayout("wrap,fillx,insets 0"));
        panel.putClientProperty(FlatClientProperties.STYLE, ""
                + "background:null");
        panel.add(createButtonsAcciones(), "al trail");
        panel.add(panelTable, "grow, push");
        return panel;
    }

    private JComponent createButtonsAcciones() {
        JPanel panel = new JPanel(new MigLayout("fill"));
        panel.putClientProperty(FlatClientProperties.STYLE, ""
                + "background:null");
        button_create.putClientProperty(FlatClientProperties.STYLE, ""
                + "foreground:#FFFFFF");
        panel.add(button_view);
        panel.add(button_create);
        return panel;
    }

    private void showPanelNewBuy() {
        ModalDialog.showModal(this,
                new CustomModal(new PanelRequestVenta(), "Realizar Venta", "resources/icon/ic_newNote.svg"),
                ConfigModal.getModelShowModalPush(), PanelRequestVenta.ID);
    }


    private void methodViewInfoBuy() {
        try {
            int row = panelTable.table.getSelectedRow();
            if (row == -1) {
                Notify.getInstance().showToast(Toast.Type.WARNING,"Seleccione una venta para visualizar");
                return;
            }

            Venta venta = listSale.get(row);
            LinkedList<DetalleVenta.DetalleVentaSub> detalles = RequestDetalleVenta.getDetallesVentaAll(venta.getId_venta());

            ModalDialog.showModal(this,
                    new CustomModal(FormManager.getPanelInfoVenta(venta, detalles), "Detalles de Venta", "resources/icon/ic_info.svg"),
                    ConfigModal.getModelShowDefault(),PanelRequestVenta.ID);

        } catch (Exception e) {
            Notify.getInstance().showToast(Toast.Type.ERROR, e.getLocalizedMessage());
        }
    }


    private class Table extends JPanel {

        private JTable table;
        private JScrollPane scrollPane;
        private DefaultTableModel model;

        private String[] columnNames = {"ID Venta", "Venta Total $", "Cantidad Total", "Método de Pago", "Fecha Venta"};

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
            setLayout(new MigLayout("wrap,fillx,insets n", "fill", "[]-1[fill,grow]"));
            putClientProperty(FlatClientProperties.STYLE, ""
                    + "arc:16;"
                    + "background:$Table.background");

            scrollPane.setBorder(BorderFactory.createEmptyBorder());
            table.setModel(model);

            //Size fields
            table.getColumnModel().getColumn(0).setMaxWidth(60);
            table.getColumnModel().getColumn(1).setPreferredWidth(90);
            table.getColumnModel().getColumn(2).setPreferredWidth(60);
            table.getColumnModel().getColumn(3).setPreferredWidth(60);
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

        public void setData(LinkedList<Venta> ventas) {
            cleanData();
            for (Venta venta : ventas) {
                model.addRow(venta.getVentaArray());
            }
        }

        public void cleanData() {
            while (model.getRowCount() > 0) {
                model.removeRow(0);
            }
        }
    }
}
