package components;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.components.FlatTextField;

public class FieldSearch extends FlatTextField {
    public FieldSearch() {
        super();
        init();
    }

    private void init() {
        setPlaceholderText("Buscar...");
        putClientProperty(FlatClientProperties.STYLE,"showClearButton:true;");
    }
}
