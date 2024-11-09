package components;

import com.formdev.flatlaf.FlatClientProperties;
import java.awt.Component;
import javax.swing.JScrollPane;

public class MyScrollPane extends JScrollPane {
    public MyScrollPane(Component view) {
        super(view);
        putClientProperty(FlatClientProperties.STYLE, ""
                + "border:0,0,0,0");
        getVerticalScrollBar().putClientProperty(FlatClientProperties.STYLE, ""
                + "trackArc:999;"
                + "width:10");
        getVerticalScrollBar().setUnitIncrement(10);
    }
}
