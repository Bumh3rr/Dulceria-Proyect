package form;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.components.FlatScrollPane;
import com.formdev.flatlaf.extras.components.FlatTable;
import components.Notify;
import dao.pool.PoolThreads;
import dao.ProveedorDao;
import form.panels.PanelRequestSupplier;
import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
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
import utils.ConfigModal;
import utils.Promiseld;
import utils.Request;
/**
 * FormSupplier es una clase que extiende Form y gestiona la interfaz de usuario para la gestión de proveedores.
 */
public class FormProveedor extends Form {

    private final String KEY = getClass().getName();
    private List<Proveedor> listProveedors;

    private Table panelTable;
    private JButton button_view;
    private JButton button_create;
    /**
     * Método llamado al abrir el formulario.
     */
    @Override
    public void formOpen() {
        refreshTabla();
    }
    /**
     * Método llamado para refrescar el formulario.
     */
    @Override
    public void formRefresh() {
        refreshTabla();
    }
    /**
     * Método llamado para inicializar el formulario.
     */
    @Override
    public void formInit() {
        refreshTabla();
    }
    /**
     * Refresca la tabla de proveedores.
     */
    private void refreshTabla() {
        if (Promiseld.checkPromiseId(KEY)) {
            return;
        }
        Promiseld.commit(KEY);
        PoolThreads.getInstance().getExecutorService().submit(() -> {
            try {
                listProveedors = ProveedorRequest.getAllProveedors();
                panelTable.setData(listProveedors);
            } catch (Exception ex) {
                Notify.getInstance().showToast(Toast.Type.ERROR, ex.getMessage());
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
        button_view = new JButton("Visualizar");
        button_create = new JButton("Agregar") {
            @Override
            public boolean isDefaultButton() {
                return true;
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
        setLayout(new MigLayout("wrap,fill,insets n", "[fill]", "[grow 0][fill]"));
        add(createHeader("Proveedores", "description", 1));
        add(body(), "gapx 7 7");
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
        panel.add(headerButtons(), "al trail");
        panel.add(panelTable, "grow, push");
        return panel;
    }
    /**
     * Crea los botones del encabezado.
     *
     * @return un componente JComponent que representa los botones del encabezado
     */
    private JComponent headerButtons() {
        JPanel panel = new JPanel(new MigLayout("fill", "fill"));
        panel.putClientProperty(FlatClientProperties.STYLE, ""
                + "background:null");
        button_create.putClientProperty(FlatClientProperties.STYLE, ""
                + "foreground:#FFFFFF");
        panel.add(button_view);
        panel.add(button_create);
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

        private String[] columnNames = {"ID", "Nombre", "Apellido", "Telefono", "Correo", "Fecha Registro"};
        /**
         * Constructor de Table.
         */
        public Table() {
            initComponentsTable();
            initListenersTable();
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
                    false, false, false, false, false, false
                };

                public boolean isCellEditable(int rowIndex, int columnIndex) {
                    return canEdit[columnIndex];
                }
            };
        }
        /**
         * Inicializa los listeners de la tabla.
         */
        private void initListenersTable() {
            table.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    int colum = table.columnAtPoint(e.getPoint());
                    int row = table.rowAtPoint(e.getPoint());

                    if (row >= 0 && colum >= 0) {
                        EventQueue.invokeLater(() -> {
                            JOptionPane.showMessageDialog(SwingUtilities.windowForComponent(table),
                                    "Hola : row" + row + " colum: " + colum);
                        });
                    }
                }
            });
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
            table.getColumnModel().getColumn(0).setMaxWidth(70);
            table.getColumnModel().getColumn(1).setPreferredWidth(90);
            table.getColumnModel().getColumn(2).setPreferredWidth(95);
            table.getColumnModel().getColumn(3).setPreferredWidth(112);
            table.getColumnModel().getColumn(4).setPreferredWidth(210);
            table.getColumnModel().getColumn(5).setPreferredWidth(209);

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
    /**
     * ProveedorRequest es una clase interna que gestiona las solicitudes de proveedores.
     */
    public static class ProveedorRequest {
        /**
         * Agrega un nuevo proveedor.
         *
         * @param proveedor el objeto Proveedor a agregar
         * @return el ID generado del nuevo proveedor
         * @throws Exception si hay un error durante la operación
         */
        public static int addProveedor(Proveedor proveedor) throws Exception {
            return PoolThreads.getInstance().getExecutorService().submit(() -> {
                try {
                    return ProveedorDao.addProveedorBD(proveedor);
                } catch (Exception e) {
                    throw new Exception(e);
                }
            }).get();
        }
        /**
         * Recupera todos los proveedores.
         *
         * @return una LinkedList de objetos Proveedor
         * @throws Exception si hay un error durante la operación
         */
        public static LinkedList<Proveedor> getAllProveedors() throws Exception {
            return PoolThreads.getInstance().getExecutorService().submit(() -> {
                try {
                    return ProveedorDao.getAllProveedorsBD();
                } catch (Exception e) {
                    throw new Exception(e);
                }
            }).get();
        }
    }

}
