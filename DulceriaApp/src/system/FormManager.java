package system;

import com.formdev.flatlaf.extras.FlatAnimatedLafChange;
import form.FormBuys;
import form.FormProveedoresAndEmpleados;
import java.awt.EventQueue;
import javax.swing.JFrame;
import raven.modal.Drawer;
import raven.modal.Toast;
import utils.UndoRedo;

/**
 * Clase que gestiona los formularios de la aplicación.
 */
public class FormManager {

    // Instancia estática de UndoRedo para gestionar formularios.
    protected static final UndoRedo<Form> FORMS = new UndoRedo<>();
    private static FormManager instance;
    private static MainForm mainForm;
    private JFrame frame;

    /**
     * Obtiene la instancia única de FormManager.
     *
     * @return la instancia de FormManager
     */
    public static FormManager getInstance() {
        if (instance == null) {
            instance = new FormManager();
        }
        return instance;
    }

    /**
     * Instala el gestor de formularios en el marco especificado.
     *
     * @param frame el marco JFrame donde se instalará el gestor de formularios
     */
    public void install(JFrame frame) {
        this.frame = frame;
        initForm(FormBuys.class);
    }

    /**
     * Obtiene la instancia única de MainForm.
     *
     * @return la instancia de MainForm
     */
    private static MainForm getMainForm() {
        if (mainForm == null) {
            mainForm = new MainForm();
        }
        return mainForm;
    }

    /**
     * Inicializa el formulario especificado.
     *
     * @param cls la clase del formulario a inicializar
     */
    private void initForm(Class<? extends Form> cls) {
        EventQueue.invokeLater(() -> {
            FlatAnimatedLafChange.showSnapshot();
            Drawer.setVisible(true);
            Drawer.toggleMenuOpenMode();
            frame.getContentPane().removeAll();
            frame.getContentPane().add(getMainForm());
            Drawer.setSelectedItemClass(cls);
            frame.repaint();
            frame.revalidate();
            FlatAnimatedLafChange.hideSnapshotWithAnimation();
        });
    }

    /**
     * Muestra el formulario principal.
     *
     * @param form el formulario a mostrar
     */
    public static void showFormMain(Form form) {
        if (form != FORMS.getCurrent()) {
            FORMS.add(form);
            mainForm.setForm(form);
            mainForm.refresh();
            Drawer.setSelectedItemClass(form.getClass());
        }
    }

    /**
     * Deshace la última acción realizada en el formulario.
     */
    public static void undo() {
        if (FORMS.isUndoAble()) {
            Form form = FORMS.undo();
            form.formOpen();
            Toast.closeAll();
            mainForm.setForm(form);
            Drawer.setSelectedItemClass(form.getClass());
        }
    }

    /**
     * Rehace la última acción deshecha en el formulario.
     */
    public static void redo() {
        if (FORMS.isRedoAble()) {
            Form form = FORMS.redo();
            form.formOpen();
            Toast.closeAll();
            mainForm.setForm(form);
            Drawer.setSelectedItemClass(form.getClass());
        }
    }

    /**
     * Refresca el formulario actual.
     */
    public static void refresh() {
        if (FORMS.getCurrent() != null) {
            FORMS.getCurrent().formRefresh();
            mainForm.refresh();
        }
    }

}