package system;

import com.formdev.flatlaf.extras.FlatAnimatedLafChange;
import form.FormProveedoresAndEmpleados;
import java.awt.EventQueue;
import javax.swing.JFrame;
import raven.modal.Drawer;
import raven.modal.Toast;
import utils.UndoRedo;

public class FormManager {

    protected static final UndoRedo<Form> FORMS = new UndoRedo<>();
    private static FormManager instance;
    private static MainForm mainForm;
    private JFrame frame;

    public static FormManager getInstance() {
        if (instance == null) {
            instance = new FormManager();
        }
        return instance;
    }

    public void install(JFrame frame) {
        this.frame = frame;
        initForm(FormProveedoresAndEmpleados.class);
    }

    private static MainForm getMainForm() {
        if (mainForm == null) {
            mainForm = new MainForm();
        }
        return mainForm;
    }

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
    
    
    public static void showFormMain(Form form) {
        if (form != FORMS.getCurrent()) {
            FORMS.add(form);
            mainForm.setForm(form);
            mainForm.refresh();
            Drawer.setSelectedItemClass(form.getClass());
        }
    }

    public static void undo() {
        if (FORMS.isUndoAble()) {
            Form form = FORMS.undo();
            form.formOpen();
            Toast.closeAll();
            mainForm.setForm(form);
            Drawer.setSelectedItemClass(form.getClass());
        }
    }

    public static void redo() {
        if (FORMS.isRedoAble()) {
            Form form = FORMS.redo();
            form.formOpen();
            Toast.closeAll();
            mainForm.setForm(form);
            Drawer.setSelectedItemClass(form.getClass());
        }
    }

    public static void refresh() {
        if (FORMS.getCurrent() != null) {
            FORMS.getCurrent().formRefresh();
            mainForm.refresh();
        }
    }

}
