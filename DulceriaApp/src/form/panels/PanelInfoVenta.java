package form.panels;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.components.FlatScrollPane;
import com.formdev.flatlaf.extras.components.FlatTable;
import model.DetalleVenta;
import model.Empleado;
import model.Venta;
import net.miginfocom.swing.MigLayout;
import raven.modal.ModalDialog;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;

import static model.Proveedor.WEEKS;

public class PanelInfoVenta extends JPanel {
    private JLabel label_idVenta, label_cant, label_fecha, label_total, label_empleado, label_metodoPay;
    private JLabel label_title1, label_title2;

    private Table table_detalles;
    private JButton button_close;

    public PanelInfoVenta() {
        initComponents();
        init();
    }

    public void setVenta(Venta venta, LinkedList<DetalleVenta.DetalleVentaSub> detalles) {
        label_idVenta.setText(String.valueOf(venta.getId_venta()));
        label_empleado.setText(venta.getEmpleado().toString());
        label_fecha.setText(venta.getFecha_venta().format(DateTimeFormatter.ofPattern("yyyy-MM-dd / hh:mm:ss a")).concat(" / " + WEEKS[venta.getFecha_venta().getDayOfWeek().getValue() - 1]));
        label_metodoPay.setText(venta.getMethodPayment());
        label_cant.setText(String.valueOf(venta.getCantidad_total_productos()).concat(" Productos"));
        label_total.setText(new StringBuilder().append("$").append(venta.getTotal_venta()).toString());
        table_detalles.setData(detalles);
        updateUI();
        revalidate();
    }

    private void initComponents() {
        label_title1 = new JLabel("Detalles") {
            @Override
            public void updateUI() {
                putClientProperty(FlatClientProperties.STYLE, "" + "font:bold +1");
                super.updateUI();
            }
        };
        label_title2 = new JLabel("Detalles Productos") {
            @Override
            public void updateUI() {
                putClientProperty(FlatClientProperties.STYLE, "" + "font:bold +1");
                super.updateUI();
            }
        };

        label_idVenta = new JLabel();
        label_cant = new JLabel();
        label_fecha = new JLabel();
        label_total = new JLabel(){
            @Override
            public void updateUI() {
                putClientProperty(FlatClientProperties.STYLE, "" +
                        "font:bold +2");
                super.updateUI();
            }
        };
        label_empleado = new JLabel();
        label_metodoPay = new JLabel();
        table_detalles = new Table();
        button_close = new JButton("Cerrar") {
            @Override
            public boolean isDefaultButton() {
                return true;
            }

            @Override
            public void updateUI() {
                putClientProperty(FlatClientProperties.STYLE, "" + "foreground:#FFFFFF;");
                super.updateUI();
            }
        };
        button_close.addActionListener(e -> ModalDialog.closeModal(PanelRequestVenta.ID));
    }


    private void init() {
        setLayout(new MigLayout("wrap 2,fillx,insets 0 25 n 25,width 400:550", "[grow,trail]6[grow]"));
        putClientProperty(FlatClientProperties.STYLE, "" + "background:null;");

        add(label_title1, "wrap,al lead,gapy 10");
        add(cretaeLabel("ID Venta: "));
        add(label_idVenta);
        add(cretaeLabel("Vendedor: "));
        add(label_empleado);
        add(cretaeLabel("Fecha: "));
        add(label_fecha);
        add(cretaeLabel("Método de Pago: "));
        add(label_metodoPay);
        add(cretaeLabel("Cantidad Total de Productos: "));
        add(label_cant);
        add(label_title2, "wrap,al lead,gapy 10");
        add(table_detalles, "span 2,grow,push");
        add(cretaeLabel("Total: "), "split 2,grow 0,al lead");
        add(label_total, "al trail");
        add(button_close, "span,grow 0,al trail");
        updateUI();
        revalidate();
    }

    private JComponent cretaeLabel(String text) {
        JLabel label = new JLabel(text);
        label.putClientProperty(FlatClientProperties.STYLE, "" +
                "font:bold 0");
        return label;
    }


    private class Table extends JPanel {

        private JTable table;
        private JScrollPane scrollPane;
        private DefaultTableModel model;

        private String[] columnNames = {"Num.", "Producto", "Cantidad", "Precio", "Total"};


        public Table() {
            initComponentsTable();
            initTable();
        }

        private void initComponentsTable() {
            table = new FlatTable();
            scrollPane = new FlatScrollPane();
            model = new DefaultTableModel(columnNames, 0) {
                boolean[] canEdit = new boolean[]{false, false, false, false, false};

                public boolean isCellEditable(int rowIndex, int columnIndex) {
                    return canEdit[columnIndex];
                }
            };
        }

        private void initTable() {
            setLayout(new MigLayout("wrap,fillx,insets n", "fill", "[]-1[fill,grow]"));
            putClientProperty(FlatClientProperties.STYLE, "" + "arc:16;" + "background:$Table.background");

            scrollPane.setBorder(BorderFactory.createEmptyBorder());
            table.setModel(model);

            //Size fields
            table.getColumnModel().getColumn(0).setMaxWidth(40);
            table.getColumnModel().getColumn(1).setPreferredWidth(110);
            table.getColumnModel().getColumn(2).setPreferredWidth(30);
            table.getColumnModel().getColumn(3).setPreferredWidth(30);
            table.getColumnModel().getColumn(4).setPreferredWidth(60);

            //Center Data
            DefaultTableCellRenderer defaultTableCellRenderer = new DefaultTableCellRenderer();
            defaultTableCellRenderer.setHorizontalAlignment(SwingConstants.CENTER);
            for (int i = 0; i < table.getColumnCount(); i++) {
                table.getColumnModel().getColumn(i).setCellRenderer(defaultTableCellRenderer);
            }

            //Styles
            table.getTableHeader().putClientProperty(FlatClientProperties.STYLE, "" + "height:30;" + "hoverBackground:null;" + "pressedBackground:fade($Component.accentColor,5%);" + "separatorColor:$TableHeader.background;");
            table.putClientProperty(FlatClientProperties.STYLE, "" + "rowHeight:40;" + "showHorizontalLines:true;" + "intercellSpacing:0,1;" + "selectionArc:20;" + "cellFocusColor:$TableHeader.hoverBackground;" + "selectionBackground:fade($Component.accentColor,10%);" + "selectionInactiveBackground:$TableHeader.hoverBackground;" + "selectionForeground:$Table.foreground;");

            scrollPane.setViewportView(table);

            add(scrollPane);
            updateUI();
            revalidate();
        }

        public void setData(LinkedList<DetalleVenta.DetalleVentaSub> detalles) {
            cleanData();
            for (DetalleVenta.DetalleVentaSub detalleVenta : detalles) {
                model.addRow(detalleVenta.getDetalleVentaSubArray(model.getRowCount() + 1));
            }
        }

        private void cleanData() {
            while (model.getRowCount() > 0) {
                model.removeRow(0);
            }
        }
    }
}
