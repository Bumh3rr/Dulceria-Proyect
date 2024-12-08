package components;

import com.formdev.flatlaf.FlatClientProperties;
import static java.awt.Component.LEFT_ALIGNMENT;
import javax.swing.JLabel;

/**
 * Clase MyLabelTitle que extiende JLabel para crear un título de etiqueta con alineación y tamaño de fuente personalizados.
 */
public class MyLabelTitle extends JLabel {
    /**
     * Constructor de MyLabelTitle.
     *
     * @param text                El texto del título de la etiqueta.
     * @param horizontalAlignment La alineación horizontal del texto.
     * @param fontSize            El tamaño de la fuente del texto.
     */
    public MyLabelTitle(String text, int horizontalAlignment, int fontSize) {
        setText(text);
        setHorizontalAlignment(horizontalAlignment);
        updateUI();
        setAlignmentX(LEFT_ALIGNMENT);
        putClientProperty(FlatClientProperties.STYLE, ""
                + "font:bold +" + fontSize);
    }
}