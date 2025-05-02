package form;

import components.button.ButtonDefault;
import components.card.ContainerCards;
import components.card.cards.CardEmpleado;
import components.Notify;
import controller.EmpleadoController;
import dao.pool.PoolThreads;
import dao.request.RequestEmpleado;
import java.awt.Dimension;
import java.util.List;
import java.util.function.Consumer;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import model.Empleado;
import net.miginfocom.swing.MigLayout;
import raven.modal.Toast;
import system.Form;
import utils.Promiseld;
import utils.ResponsiveLayout;

public class FormEmpleado extends Form {
    private final String KEY = getClass().getName();
    private ContainerCards<Empleado> containerCards;
    private ButtonDefault button_add_employee;

    public FormEmpleado() {
        initComponents();
        init();
        refreshEmpleados();
    }

    @Override
    public void formInit() {
        refreshEmpleados();
        EmpleadoController empleadoController = new EmpleadoController(this);
        containerCards.installDependent(empleadoController.eventShowPanelInfoEmployee);
    }

    @Override
    public void formOpen() {
        refreshEmpleados();
    }

    @Override
    public void formRefresh() {
        refreshEmpleados();
    }

    public void installEventShowPanelAddEmployee(Runnable event) {
        button_add_employee.addActionListener((e) -> event.run());
    }

    public void refreshEmpleados() {
        if (Promiseld.checkPromiseId(KEY)) {
            return;
        }
        Promiseld.commit(KEY);
        PoolThreads.getInstance().getExecutorService().submit(() -> {
            try {
                List<Empleado> allEmpleados = RequestEmpleado.getAllEmpleados();
                if (!allEmpleados.isEmpty()) {
                    eventAddEmployeeCard.accept(allEmpleados);
                } else {
                    Notify.getInstance().showToast(Toast.Type.INFO, "No hay empleados registrados");
                }
            } catch (Exception ex) {
                Notify.getInstance().showToast(Toast.Type.ERROR, ex.getMessage());
            } finally {
                Promiseld.terminate(KEY);
            }
        });
    }

    private void initComponents() {
        button_add_employee = new ButtonDefault("Agregar Empleado");
        containerCards = new ContainerCards<>(CardEmpleado.class, new ResponsiveLayout(ResponsiveLayout.JustifyContent.FIT_CONTENT, new Dimension(500, -1), 10, 10));
    }

    private void init() {
        setLayout(new MigLayout("wrap,fill,insets 0", "[fill]", "[fill]"));
        add(body());
    }

    private JComponent body() {
        JPanel panel = new JPanel(new MigLayout("wrap,fillx,insets 0", "[fill]", "[][fill]"));
        panel.add(button_add_employee, "grow 0,al trail");
        panel.add(containerCards, "gapx 0 2,grow,push");
        panel.add(containerCards.getPanelPaginacion(), "grow 0,al center");
        return panel;
    }

    public Consumer<Empleado> eventAddEmployee = (employee) ->
            SwingUtilities.invokeLater(() -> containerCards.addItemOne(employee));

    public Consumer<List<Empleado>> eventAddEmployeeCard = (list) ->
            containerCards.addItemsAll(list);

}