package system;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import javax.swing.SwingUtilities;

public class AllForms {

    private static AllForms instance;
    private Map<Class<? extends Form>, Form> formsMap;

    public static AllForms getInstance() {
        if (instance == null) {
            instance = new AllForms();
        }
        return instance;
    }

    public AllForms() {
        formsMap = new HashMap<>();
    }

    public Form getForm(Class<? extends Form> classForm) {
        if (instance.formsMap.containsKey(classForm)) {
            Form form = instance.formsMap.get(classForm);
            formOpen(form);
            return form;
        }

        try {
            Form form = classForm.getDeclaredConstructor().newInstance();
            instance.formsMap.put(classForm, form);
            formInit(form);
            return form;
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException
                | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static void formInit(Form form) {
        SwingUtilities.invokeLater(() -> form.formInit());
    }

    public static void formOpen(Form form) {
        SwingUtilities.invokeLater(() -> form.formOpen());
    }

}
