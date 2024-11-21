package components;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;

public class ButtonIcon extends JButton {
    private JLabel title;
    public ButtonIcon(String text,String url,Float scale,int border) {
        setLayout(new MigLayout("al center center,insets 0"));
        title = new JLabel(text);
        title.putClientProperty(FlatClientProperties.STYLE, ""
                + "font:bold 0;");
        add(title);
        add(createIcon(url, scale, border));
    }

    private JComponent createIcon(String url,Float scale,int border){
        FlatSVGIcon svgIcon = new FlatSVGIcon(url, scale).setColorFilter(new FlatSVGIcon.ColorFilter(color -> UIManager.getColor("Component.accentColor")));

        JLabel label = new JLabel(svgIcon);
        label.putClientProperty(FlatClientProperties.STYLE, "" +
                "border:"+ border + ","+ border +","+ border +","+ border +",fade($Component.accentColor,50%),,999;" +
                "background:fade($Component.accentColor,10%);");

        return label;
    }

    @Override
    public void setText(String text) {
        title.setText(text);
    }
}

