package components;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.components.FlatTextField;

/**
 * Clase FieldSearch que extiende FlatTextField para crear un campo de búsqueda con un botón de limpiar.
 */
public class FieldSearch extends FlatTextField {
    /**
     * Constructor de FieldSearch.
     */
    public FieldSearch() {
        super();
        init();
    }

    /**
     * Método de inicialización que configura el campo de búsqueda.
     */
    private void init() {
        setPlaceholderText("Buscar...");
        putClientProperty(FlatClientProperties.STYLE, "showClearButton:true;");
    }
}