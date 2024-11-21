
package form;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.components.FlatScrollPane;
import com.formdev.flatlaf.extras.components.FlatTable;
import form.panels.PanelRequestVenta;
import modal.ConfigModal;
import modal.CustomModal;
import model.Proveedor;
import net.miginfocom.swing.MigLayout;
import raven.modal.ModalDialog;
import system.Form;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class FormBuys extends Form {
    private Table panelTable;
    private JButton button_view;
    private JButton button_create;

    public FormBuys() {
        initComponents();
        initListeners();
        init();
    }

    private void initComponents() {
        panelTable = new Table();
        button_view = new JButton("Visualizar Venta");
        button_create = new JButton("Realizar Venta"){
            @Override
            public boolean isDefaultButton() {
                return true;
            }
        };
    }
    private void initListeners() {
        button_create.addActionListener(e -> {
            showPanelNewBuy();
        });
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
            JPanel panel = new JPanel(new MigLayout("fill", "fill"));
            panel.putClientProperty(FlatClientProperties.STYLE, ""
                    + "background:null");
            button_create.putClientProperty(FlatClientProperties.STYLE, ""
                    + "foreground:#FFFFFF");
            panel.add(button_view);
            panel.add(button_create);
            return panel;
        }



    private void showPanelNewBuy() {
        ModalDialog.showModal(this, new CustomModal(new PanelRequestVenta(), "Realizar Venta", "resources/icon/ic_newNote.svg"),
                ConfigModal.getModelShowModalPush(), PanelRequestVenta.ID);
    }




    public static class Table extends JPanel {

        private JTable table;
        private JScrollPane scrollPane;
        private DefaultTableModel model;

        private String[] columnNames = {"ID Venta", "Nombre Cliente", "Venta Total $", "Fecha Venta"};

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
                        false, false, false, false
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
            table.getColumnModel().getColumn(3).setPreferredWidth(50);


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
         * @param proveedor una lista de objetos Proveedor
         */
        public void setData(List<Proveedor> proveedor) {
            cleanData();
            for (Proveedor proveedors : proveedor) {
                model.addRow(proveedors.getUserArray());
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
