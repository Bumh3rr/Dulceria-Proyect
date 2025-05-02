package controller;

import components.Notify;
import dao.request.RequestEmpleado;
import form.FormEmpleado;
import form.panels.PanelInfoEmpleado;
import form.panels.PanelRequestEmpleado;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.function.BiConsumer;
import model.Empleado;
import raven.modal.ModalDialog;
import raven.modal.Toast;

public class EmpleadoController extends Controller {
    private FormEmpleado view;

    public EmpleadoController(FormEmpleado view) {
        this.view = view;
        this.view.installEventShowPanelAddEmployee(this::showPanelAddEmployee);
    }

    private void showPanelAddEmployee() {
        PanelRequestEmpleado panelRequestEmpleado = new PanelRequestEmpleado();
        panelRequestEmpleado.installEventButton(() -> {
            Optional<Empleado> value = panelRequestEmpleado.getValue();
            if (value.isPresent()) {
                try {
                    if (RequestEmpleado.addEmpleado(value.get())) {
                        view.eventAddEmployee.accept(value.get());
                        eventSuccess.accept("Success :)", "Empleado agregado correctamente");
                        Notify.getInstance().showToast(Toast.Type.SUCCESS, "Empleado agregado correctamente");
                    }
                } catch (Exception e) {
                    Notify.getInstance().showToast(Toast.Type.ERROR, "Error al agregar el empleado\n" + e.getMessage());
                }
            }
        });
        showPanel(panelRequestEmpleado,
                "Agregar Empleado",
                "ic_add-user.svg",
                ID,
                null,
                false);
    }

    public BiConsumer<Empleado, Runnable> eventShowPanelInfoEmployee = (empleado, consumer) -> {
        PanelInfoEmpleado panelInfoEmployee = new PanelInfoEmpleado(empleado);
        panelInfoEmployee.installEventButtonUpdate(() -> updateInfoEmployee(empleado, panelInfoEmployee));
        panelInfoEmployee.installEventButtonActiveOrLow(() -> updateStatusEmployee(empleado, panelInfoEmployee));
        showPanel(panelInfoEmployee, "Información del Empleado", "ic_tecnico.svg", ID, null, false);
    };

    private void updateInfoEmployee(Empleado empleado, PanelInfoEmpleado panelInfoEmployee) {
        PanelRequestEmpleado panelRequestEmpleado = new PanelRequestEmpleado();
        panelRequestEmpleado.setEmpleado(empleado);
        panelRequestEmpleado.installEventButton(() -> {
            Optional<Empleado> value = panelRequestEmpleado.getValue();
            if (value.isPresent()) {
                try {
                    if (RequestEmpleado.updateEmpleado(value.get(), empleado.getIdEmpleado())) {
                        empleado.setNombre(value.get().getNombre());
                        empleado.setApellido(value.get().getApellido());
                        empleado.setTelefono(value.get().getTelefono());
                        empleado.setDireccion(value.get().getDireccion());
                        empleado.setRfc(value.get().getRfc());
                        empleado.setPuesto(value.get().getPuesto());
                        empleado.setSueldo(value.get().getSueldo());
                        panelInfoEmployee.refreshFields();
                        ModalDialog.popModel(ID);
                        Notify.getInstance().showToast(Toast.Type.SUCCESS, "Empleado actualizado correctamente");
                    } else {
                        Notify.getInstance().showToast(Toast.Type.ERROR, "Error al actualizar el empleado");
                    }
                } catch (Exception e) {
                    Notify.getInstance().showToast(Toast.Type.ERROR, "Error al agregar el empleado\n" + e.getMessage());
                }
            }
        });
        showPanel(panelRequestEmpleado,
                "Actualizar Empleado",
                "ic_update.svg",
                ID,
                () -> ModalDialog.popModel(ID),
                true);
    }


    private void updateStatusEmployee(Empleado empleado, PanelInfoEmpleado panelInfoEmployee) {
        try {
            LocalDateTime localDateTime = RequestEmpleado.updateStatusEmpleado(empleado.getFecha_baja() != null, empleado.getIdEmpleado());
            empleado.setFecha_baja(localDateTime);
            empleado.setEstado(localDateTime == null ? Empleado.Status.Activo : Empleado.Status.Inactivo);
            panelInfoEmployee.refreshFields();
            Notify.getInstance().showToast(Toast.Type.SUCCESS, "Empleado actualizado correctamente");
        } catch (Exception e) {
            Notify.getInstance().showToast(Toast.Type.ERROR, "Error al actualizado al empleado\n" + e.getMessage());
        }
    }
}
