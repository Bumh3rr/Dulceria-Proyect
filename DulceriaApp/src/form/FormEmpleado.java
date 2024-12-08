package form;

import com.formdev.flatlaf.FlatClientProperties;
import modal.cards.CardEmpleado;
import components.Notify;
import dao.pool.PoolThreads;
import form.panels.PanelInfoEmpleado;
import form.panels.PanelRequestEmpleado;
import dao.request.RequestEmpleado;
import java.awt.Dimension;
import java.util.LinkedList;
import java.util.function.Consumer;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import model.Empleado;
import net.miginfocom.swing.MigLayout;
import raven.modal.Drawer;
import raven.modal.ModalDialog;
import raven.modal.Toast;
import raven.modal.component.SimpleModalBorder;
import system.Form;
import modal.ConfigModal;
import utils.Promiseld;
import utils.Request;
import utils.ResponsiveLayout;

public class FormEmpleado extends Form {

    private final String KEY = getClass().getName();
    private LinkedList<Empleado> listEmpleado;
    private ResponsiveLayout responsiveLayout;
    private JPanel panelEmpleados;

    /**
     * Constructor de FormEmpleado.
     * Inicializa los componentes, listeners y refresca la lista de empleados.
     */
    public FormEmpleado() {
        initComponents();
        initListeners();
        init();
        refreshEmpleados();
    }

    /**
     * Inicializa el formulario.
     */
    @Override
    public void formInit() {
        refreshEmpleados();
    }

    /**
     * Abre el formulario.
     */
    @Override
    public void formOpen() {
        refreshEmpleados();
    }

    /**
     * Refresca el formulario.
     */
    @Override
    public void formRefresh() {
        refreshEmpleados();
    }

    /**
     * Refresca la lista de empleados.
     * Obtiene los empleados desde la base de datos y actualiza el panel de empleados.
     */
    public void refreshEmpleados() {
        if (Promiseld.checkPromiseId(KEY)) {
            return;
        }
        Promiseld.commit(KEY);
        PoolThreads.getInstance().getExecutorService().submit(() -> {
            try {
                listEmpleado = RequestEmpleado.getAllEmpleados();
                refreshPanelEmpleados(listEmpleado);
            } catch (Exception ex) {
                Notify.getInstance().showToast(Toast.Type.ERROR, ex.getMessage());
                System.out.println(ex.getLocalizedMessage());
            } finally {
                Promiseld.terminate(KEY);
            }
        });
    }

    /**
     * Inicializa los componentes del formulario.
     */
    private void initComponents() {
        listEmpleado = new LinkedList<>();
    }

    /**
     * Inicializa los listeners del formulario.
     */
    private void initListeners() {
    }

    /**
     * Inicializa el formulario.
     * Configura el layout y agrega el cuerpo del formulario.
     */
    private void init() {
        setLayout(new MigLayout("wrap,fill,insets 0", "[fill]", "[grow 0][fill]"));
        add(body());
    }

    /**
     * Crea el cuerpo del formulario.
     *
     * @return El componente JComponent que representa el cuerpo del formulario.
     */
    private JComponent body() {
        JPanel panel = new JPanel(new MigLayout("wrap,fillx,insets 0", "[fill]", "[][fill]"));
        JButton buton = new JButton("Agregar Empleado") {
            @Override
            public boolean isDefaultButton() {
                return true;
            }
        };
        buton.putClientProperty(FlatClientProperties.STYLE, ""
                + "foreground:#FFFFFF");

        // Agregar Producto
        buton.addActionListener((e) -> {
            // Instancia Panel
            PanelRequestEmpleado panelAdd = new PanelRequestEmpleado(this, Request.INSERTS);
            ModalDialog.showModal(SwingUtilities.windowForComponent(this),
                    new SimpleModalBorder(panelAdd, "Agregar Empleado", SimpleModalBorder.DEFAULT_OPTION, (controller, action) -> {
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

        panel.add(buton, "grow 0,al trail");
        panel.add(createEmpleadosContainers(), "gapx 0 2");

        return panel;
    }

    /**
     * Crea los contenedores de empleados.
     *
     * @return El componente JComponent que contiene los contenedores de empleados.
     */
    private JComponent createEmpleadosContainers() {
        responsiveLayout = new ResponsiveLayout(ResponsiveLayout.JustifyContent.FIT_CONTENT, new Dimension(-1, -1), 10, 10);
        panelEmpleados = new JPanel(responsiveLayout);
        panelEmpleados.putClientProperty(FlatClientProperties.STYLE, ""
                + "[light]background:darken(@background,3%);"
                + "[dark]background:lighten(@background,3%)");
        panelEmpleados.putClientProperty(FlatClientProperties.STYLE, ""
                + "border:10,10,10,10;");
        JScrollPane scrollPane = new JScrollPane(panelEmpleados);
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

    /**
     * Refresca el panel de empleados.
     *
     * @param list La lista de empleados a mostrar en el panel.
     * @throws Exception Si ocurre un error durante la actualización del panel.
     */
    private void refreshPanelEmpleados(LinkedList<Empleado> list) throws Exception {
        panelEmpleados.removeAll();
        for (Empleado empleado : list) {
            panelEmpleados.add(new CardEmpleado(empleado, createEventCard()));
        }
        panelEmpleados.repaint();
        panelEmpleados.revalidate();
    }

    /**
     * Crea un evento para las tarjetas de empleados.
     *
     * @return Un consumidor que maneja el evento de las tarjetas de empleados.
     */
    private Consumer<Empleado> createEventCard() {
        return e -> {
            // Ver información
            PanelInfoEmpleado panel = new PanelInfoEmpleado(e, this);
            ModalDialog.showModal(SwingUtilities.windowForComponent(this),
                    new SimpleModalBorder(panel, "Información del Empleado", SimpleModalBorder.DEFAULT_OPTION, (controller, action) -> {
                        if (action == SimpleModalBorder.CLOSE_OPTION) {
                            controller.close();
                        }
                    }), ConfigModal.getModelShowDefault());
        };
    }

}