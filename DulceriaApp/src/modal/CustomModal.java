package modal;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import form.panels.PanelRequestVenta;
import net.miginfocom.swing.MigLayout;
import raven.modal.ModalDialog;
import raven.modal.component.Modal;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

public class CustomModal extends Modal {
    private final Component component;
    private final String title;
    private final String icon;
    private final String ID;
    private final Consumer consumer;

    public CustomModal(Component component, String title, String icon, String ID, Consumer<Boolean> consumer) {
        this.component = component;
        this.title = title;
        this.icon = icon;
        this.ID = ID;
        this.consumer = consumer;
    }

    public CustomModal(Component component, String title, String icon) {
        this.component = component;
        this.title = title;
        this.icon = icon;
        this.ID = null;
        this.consumer = null;
    }

    @Override
    public void installComponent() {
        setLayout(new MigLayout("wrap,fillx", "[fill]"));
        add(createHeader());
        add(component);
        updateUI();
    }

    protected Component createHeader() {
        JPanel panel = new JPanel(new MigLayout("insets 10 20 0 20,gapx 10"));
        panel.putClientProperty(FlatClientProperties.STYLE, "" +
                "background:null;");
        if (ID != null) {
            panel.add(createBackButton(), "al left");
        }
        panel.add(createIcon());
        panel.add(createTitle());
        return panel;
    }

    protected JComponent createBackButton() {
        JButton buttonClose = new JButton(new FlatSVGIcon("raven/modal/icon/back.svg", 0.4F));
        buttonClose.setFocusable(false);
        buttonClose.addActionListener((e) -> {
            consumer.accept(true);
            ModalDialog.popModel(ID);
        });
        buttonClose.putClientProperty("FlatLaf.style", "arc:999;margin:5,5,5,5;borderWidth:0;focusWidth:0;innerFocusWidth:0;background:null;");
        return buttonClose;
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
