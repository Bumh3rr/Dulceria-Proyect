package system;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatLaf;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LookAndFeel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Panel extends JPanel {
    private LookAndFeel oldTheme = UIManager.getLookAndFeel();

    public void panelInit() {
    }

    public void panelRefresh() {
    }

    public void panelOpen() {
    }

    public void installController() {
    }

    public void panelCheckUI() {
        SwingUtilities.invokeLater(() -> {
            if (oldTheme != UIManager.getLookAndFeel()) {
                try {
                    oldTheme = UIManager.getLookAndFeel();
                    SwingUtilities.updateComponentTreeUI(this);
                    UIManager.setLookAndFeel(oldTheme);
                    FlatLaf.updateUI();
                } catch (UnsupportedLookAndFeelException ex) {
                    Logger.getLogger(Panel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }


    public static final JComponent createdGramaticalP(String title) {
        JLabel label = new JLabel(title);
        label.putClientProperty(FlatClientProperties.STYLE, ""
                + "[light]foreground:lighten(@foreground,30%);"
                + "[dark]foreground:darken(@foreground,30%);"
                + "font:12;"
        );
        return label;
    }

}
