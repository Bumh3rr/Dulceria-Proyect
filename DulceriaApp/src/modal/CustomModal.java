package modal;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import net.miginfocom.swing.MigLayout;
import raven.modal.component.Modal;

import javax.swing.*;
import java.awt.*;

public class CustomModal extends Modal {
    private final Component component;
    private final String title;
    private final String icon;

    public CustomModal(Component component, String title, String icon) {
        this.component = component;
        this.title = title;
        this.icon = icon;
    }

    @Override
    public void installComponent() {
        setLayout(new MigLayout("wrap,fillx", "[fill]"));
        add(createHeader());
        add(component);
    }

    protected Component createHeader() {
        JPanel panel = new JPanel(new MigLayout("insets 10 20 0 20,gapx 10"));
        panel.putClientProperty(FlatClientProperties.STYLE, "" +
                "background:null;");
        panel.add(createIcon());
        panel.add(createTitle());
        return panel;
    }

    protected Component createTitle() {
        JLabel label = new JLabel(title);
        label.putClientProperty(FlatClientProperties.STYLE, "" +
                "font:bold +5;");
        return label;
    }

    protected JLabel createIcon() {
        FlatSVGIcon svgIcon = new FlatSVGIcon(icon,0.4f).setColorFilter(new FlatSVGIcon.ColorFilter(color -> UIManager.getColor("Component.accentColor")));

        JLabel label = new JLabel(svgIcon);
        label.putClientProperty(FlatClientProperties.STYLE,"" +
                "border:10,10,10,10,fade($Component.accentColor,50%),,999;" +
                "background:fade($Component.accentColor,10%);");
        return label;
    }
}
