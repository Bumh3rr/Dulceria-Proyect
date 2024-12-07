package form;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.components.FlatScrollPane;
import com.formdev.flatlaf.extras.components.FlatTable;
import components.Notify;
import dao.pool.PoolThreads;
import form.panels.PanelRequestSupplier;
import dao.request.RequestProveedor;

import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import model.Proveedor;
import net.miginfocom.swing.MigLayout;
import raven.modal.ModalDialog;
import raven.modal.Toast;
import raven.modal.component.SimpleModalBorder;
import system.Form;
import modal.ConfigModal;
import utils.Promiseld;
import utils.Request;
/**
 * FormSupplier es una clase que extiende Form y gestiona la interfaz de usuario para la gestión de proveedores.
 */
public class FormProveedor extends Form {

    private final String KEY = getClass().getName();
    private List<Proveedor> listProveedors;

    private Table panelTable;
    private JButton button_create;

    public void refreshTabla() {
        if (Promiseld.checkPromiseId(KEY)) {
            return;
        }
        Promiseld.commit(KEY);
        PoolThreads.getInstance().getExecutorService().submit(() -> {
            try {
                listProveedors = RequestProveedor.getAllProveedors();
                panelTable.setData(listProveedors);
            } catch (Exception ex) {
                Notify.getInstance().showToast(Toast.Type.ERROR, ex.getMessage());
                System.out.println(ex.getLocalizedMessage());
            } finally {
                Promiseld.terminate(KEY);
            }
        });
    }
    /**
     * Constructor de FormProveedor.
     */
    public FormProveedor() {
        initComponents();
        initListeners();
        init();
    }
    /**
     * Inicializa los componentes del formulario.
     */
    private void initComponents() {
        panelTable = new Table();
        button_create = new JButton("Agregar") {
            @Override
            public boolean isDefaultButton() {
                return true;
            }

            @Override
            public void updateUI() {
                putClientProperty(FlatClientProperties.STYLE,"" +
                       "foreground:#FFF");
                super.updateUI();
            }
        };

    }
    /**
     * Inicializa los listeners de los componentes.
     */
    private void initListeners() {
        button_create.addActionListener((e) -> showPanelAddSupplier());
    }
    /**
     * Inicializa el formulario.
     */
    private void init() {
        setLayout(new MigLayout("wrap,fill,insets 0", "[fill]", "[grow 0][fill]"));
        add(body(), "gapx 7 7");
        updateUI();
        revalidate();
    }
    /**
     * Crea el cuerpo del formulario.
     *
     * @return un componente JComponent que representa el cuerpo del formulario
     */
    private JComponent body() {
        JPanel panel = new JPanel(new MigLayout("wrap,fillx,insets 0"));
        panel.putClientProperty(FlatClientProperties.STYLE, ""
                + "background:null");
        panel.add(button_create, "al trail");
        panel.add(panelTable, "grow, push");
        return panel;
    }
    /**
     * Muestra el panel para agregar un nuevo proveedor.
     */
    private void showPanelAddSupplier() {
        PanelRequestSupplier panelRequest = new PanelRequestSupplier(this, Request.INSERTS);
        ModalDialog.showModal(SwingUtilities.windowForComponent(this),
                new SimpleModalBorder(panelRequest, "Agregar Proveedor", SimpleModalBorder.DEFAULT_OPTION, (controller, action) -> {
                    if (action == SimpleModalBorder.OK_OPTION) {
                        panelRequest.commitInserts(controller);
                    } else if (action == SimpleModalBorder.CANCEL_OPTION) {
                        controller.close();
                    }
                }), ConfigModal.getModelShowDefault());
    }
    /**
     * Table es una clase interna que extiende JPanel y gestiona la tabla de proveedores.
     */
    public static class Table extends JPanel {

        private JTable table;
        private JScrollPane scrollPane;
        private DefaultTableModel model;

        private String[] columnNames = {"ID", "Nombre", "Apellido", "Teléfono", "Correo", "Dirección","Fecha Registro"};
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
                    false, false, false, false, false, false, false
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
            table.getColumnModel().getColumn(0).setMaxWidth(40);
            table.getColumnModel().getColumn(1).setPreferredWidth(90);
            table.getColumnModel().getColumn(2).setPreferredWidth(95);
            table.getColumnModel().getColumn(3).setPreferredWidth(70);
            table.getColumnModel().getColumn(4).setPreferredWidth(210);
            table.getColumnModel().getColumn(5).setPreferredWidth(210);
            table.getColumnModel().getColumn(6).setPreferredWidth(209);

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
