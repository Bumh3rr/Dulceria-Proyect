package system;

import components.MyLabelTitle;
import components.MyTxtAreaDescrip;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;

public class Form extends JPanel {

    public void formOpen() {

    }

    public void formRefresh() {

    }

    public void formInit() {

    }

    public JComponent createHeader(String title, String description, int size) {
        JPanel panel = new JPanel(new MigLayout("fillx,wrap,insets 5 10 5 10", "[fill]"));
        panel.add(new MyLabelTitle(title, JLabel.LEFT, (4 - size)));
        panel.add(new MyTxtAreaDescrip(description));
        return panel;
    }
}
