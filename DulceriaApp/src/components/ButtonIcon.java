package components;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;

/**
 * Clase ButtonIcon que extiende JButton para crear un botón con un icono SVG y un título.
 */
public class ButtonIcon extends JButton {
    private JLabel title;

    /**
     * Constructor de ButtonIcon.
     *
     * @param text   El texto del título del botón.
     * @param url    La URL del icono SVG.
     * @param scale  La escala del icono SVG.
     * @param border El tamaño del borde del icono.
     */
    public ButtonIcon(String text, String url, Float scale, int border) {
        setLayout(new MigLayout("al center center,insets 0"));
        title = new JLabel(text);
        title.putClientProperty(FlatClientProperties.STYLE, ""
                + "font:bold 0;");
        add(title);
        add(createIcon(url, scale, border));
    }

    /**
     * Crea un componente JComponent que contiene el icono SVG.
     *
     * @param url    La URL del icono SVG.
     * @param scale  La escala del icono SVG.
     * @param border El tamaño del borde del icono.
     * @return Un componente JComponent que contiene el icono SVG.
     */
    private JComponent createIcon(String url, Float scale, int border) {
        FlatSVGIcon svgIcon = new FlatSVGIcon(url, scale).setColorFilter(new FlatSVGIcon.ColorFilter(color -> UIManager.getColor("Component.accentColor")));

        JLabel label = new JLabel(svgIcon);
        label.putClientProperty(FlatClientProperties.STYLE, "" +
                "border:" + border + "," + border + "," + border + "," + border + ",fade($Component.accentColor,50%),,999;" +
                "background:fade($Component.accentColor,10%);");

        return label;
    }

    /**
     * Establece el texto del título del botón.
     *
     * @param text El nuevo texto del título.
     */
    @Override
    public void setText(String text) {
        title.setText(text);
    }
}