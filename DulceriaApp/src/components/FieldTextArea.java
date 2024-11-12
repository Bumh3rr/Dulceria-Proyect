package components;

import com.formdev.flatlaf.FlatClientProperties;
import java.awt.Cursor;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTextArea;

public class FieldTextArea extends JTextArea {

    private final JPopupMenu popupMenu = new JPopupMenu();
    private final JMenuItem copyItem = new JMenuItem("Copiar");

    public FieldTextArea(String title) {
        if (title == null || title.isEmpty()) {
            setText("el campo esta vació");
            putClientProperty(FlatClientProperties.STYLE, ""
                    + "background:null;"
                    + "[light]foreground:lighten(@foreground,30%);"
                    + "[dark]foreground:darken(@foreground,30%);"
                    + "font:bold +3");
        } else {
            setText(title);
            putClientProperty(FlatClientProperties.STYLE, ""
                    + "background:null;"
                    + "font:bold +3");
        }
        setEditable(false);
        setBorder(BorderFactory.createEmptyBorder());
        setCursor(new Cursor(Cursor.TEXT_CURSOR));

        addPopMenu();
    }

    public FieldTextArea(String title, int size) {
        if (title == null || title.isEmpty()) {
            setText("el campo esta vació");
            putClientProperty(FlatClientProperties.STYLE, ""
                    + "background:null;"
                    + "[light]foreground:lighten(@foreground,30%);"
                    + "[dark]foreground:darken(@foreground,30%);"
                    + "font:bold +" + size);
        } else {
            setText(title);
            putClientProperty(FlatClientProperties.STYLE, ""
                    + "background:null;"
                    + "font:bold +" + size);
        }
        setEditable(false);
        setBorder(BorderFactory.createEmptyBorder());
        setCursor(new Cursor(Cursor.TEXT_CURSOR));

        addPopMenu();
    }

    public FieldTextArea() {
        setText("");
        setEditable(false);
        setBorder(BorderFactory.createEmptyBorder());
        setCursor(new Cursor(Cursor.TEXT_CURSOR));
        addPopMenu();
    }

    @Override
    public void setText(String t) {
        if (t == null || t.isEmpty()) {
            setText("el campo esta vació");
            putClientProperty(FlatClientProperties.STYLE, ""
                    + "background:null;"
                    + "[light]foreground:lighten(@foreground,30%);"
                    + "[dark]foreground:darken(@foreground,30%);"
                    + "font:bold +3");
            super.setText(t);
        } else {
            putClientProperty(FlatClientProperties.STYLE, ""
                    + "background:null;"
                    + "font:bold +3");
            super.setText(t);
        }
    }

    private void addPopMenu() {
        popupMenu.add(copyItem);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                showPopup(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                showPopup(e);
            }

            private void showPopup(MouseEvent e) {
                if (e.isPopupTrigger()) {  // Detecta si es click derecho
                    popupMenu.show(e.getComponent(), e.getX(), e.getY()); // Muestra el menú en la posición del click
                }
            }
        });

        copyItem.addActionListener(x -> {
            String textoParaCopiar = getSelectedText();

            // Crear un objeto StringSelection con el texto
            StringSelection stringSelection = new StringSelection(textoParaCopiar);

            // Obtener el portapapeles del sistema
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

            // Poner el texto en el portapapeles
            clipboard.setContents(stringSelection, null);
        });
    }

    public void setTextField(String t) {
        if (t == null || t.isEmpty()) {
            setText("el campo esta vació");
            putClientProperty(FlatClientProperties.STYLE, ""
                    + "background:null;"
                    + "[light]foreground:lighten(@foreground,30%);"
                    + "[dark]foreground:darken(@foreground,30%);"
                    + "font:bold +3");
        } else {
            setText(t);
            putClientProperty(FlatClientProperties.STYLE, ""
                    + "background:null;"
                    + "font:bold +3");
        }
    }

}
