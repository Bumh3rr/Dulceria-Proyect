
package components;

import com.formdev.flatlaf.FlatClientProperties;
import static java.awt.Component.LEFT_ALIGNMENT;
import javax.swing.JLabel;

public class MyLabelTitle extends JLabel {
    public MyLabelTitle(String text, int horizontalAlignment,int fontSize) {
        setText(text);
        setHorizontalAlignment(horizontalAlignment);
        updateUI();
        setAlignmentX(LEFT_ALIGNMENT);
        putClientProperty(FlatClientProperties.STYLE, ""
                + "font:bold +" + fontSize);
    }
}