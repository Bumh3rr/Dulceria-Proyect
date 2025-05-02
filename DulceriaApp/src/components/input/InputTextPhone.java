package components.input;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.extras.components.FlatFormattedTextField;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.text.ParseException;
import javax.swing.JLabel;
import javax.swing.UIManager;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;

public class InputTextPhone extends FlatFormattedTextField {
    private JLabel baseIcon;
    private FlatSVGIcon icon;
    private final String iconURL = "resources/icon/ic_phone.svg";

    public InputTextPhone() {
        icon = new FlatSVGIcon(iconURL, 0.35f);
        baseIcon = new JLabel("+52");
        baseIcon.setIcon(icon);
        baseIcon.putClientProperty(FlatClientProperties.STYLE,
                "border:0,8,0,0; [light]foreground:lighten(@foreground,30%); [dark]foreground:darken(@foreground,30%);");

        addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                baseIcon.setIcon(new FlatSVGIcon(iconURL, 0.35f).setColorFilter(new FlatSVGIcon.ColorFilter((x) -> UIManager.getColor("Component.accentColor"))));
            }

            @Override
            public void focusLost(FocusEvent e) {
                baseIcon.setIcon(icon);
            }
        });

        putClientProperty(FlatClientProperties.TEXT_FIELD_LEADING_COMPONENT, baseIcon);
        putClientProperty(FlatClientProperties.STYLE, ""
                + "iconTextGap:10;"
                + "showClearButton:true");
        try {
            setFormatterFactory(new DefaultFormatterFactory(new MaskFormatter("###-###-####")));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        putClientProperty("JTextField.clearCallback", (Runnable) () -> {
            // Custom logic when the clear button is pressed
            setText("");
            setValue(null);
        });
    }
}
