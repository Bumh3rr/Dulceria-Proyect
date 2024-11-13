package form;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.components.FlatButton;
import components.CardEmpleado;
import components.Notify;
import dao.pool.PoolThreads;
import form.panels.PanelInfoEmpleado;
import form.panels.PanelRequestEmpleado;
import form.panels.PanelRequestProducto;
import form.request.RequestEmpleado;
import java.awt.Dimension;
import java.util.LinkedList;
import java.util.List;
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
import utils.ConfigModal;
import utils.Promiseld;
import utils.Request;
import utils.ResponsiveLayout;

public class FormEmpleado extends Form {

    private final String KEY = getClass().getName();
    private LinkedList<Empleado> listEmpleado;
    private ResponsiveLayout responsiveLayout;
    private JPanel panelEmpleados;

    public FormEmpleado() {
        initComponents();
        initListeners();
        init();
        refreshEmpleados();
    }

    @Override
    public void formInit() {
        refreshEmpleados();
    }

    @Override
    public void formOpen() {
        refreshEmpleados();
    }

    @Override
    public void formRefresh() {
        refreshEmpleados();
    }

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

    private void initComponents() {
        listEmpleado = new LinkedList<>();
    }

    private void initListeners() {
    }

    private void init() {
        setLayout(new MigLayout("wrap,fill,insets 0", "[fill]", "[grow 0][fill]"));
        add(body());
    }

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

        //Agregar Producto
        buton.addActionListener((e) -> {
            //Instance Panel
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

    private void refreshPanelEmpleados(LinkedList<Empleado> list) throws Exception {
        panelEmpleados.removeAll();
        for (Empleado empleado : list) {
            panelEmpleados.add(new CardEmpleado(empleado, createEventCard()));
        }
        panelEmpleados.repaint();
        panelEmpleados.revalidate();
    }

    private Consumer<Empleado> createEventCard() {
        return e -> {
//            // View Info
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
