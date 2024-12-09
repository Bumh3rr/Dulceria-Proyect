package components;

import com.formdev.flatlaf.extras.components.FlatTextField;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;

/**
 * Clase MyJTextField que extiende FlatTextField para crear un campo de texto con un menú contextual para acciones de copiar, cortar y pegar.
 */
public class MyJTextField extends FlatTextField {

    private final JPopupMenu popupMenu = new JPopupMenu();

    private final JMenuItem copyItem = new JMenuItem("Copiar");
    private final JMenuItem cutItem = new JMenuItem("Cortar");
    private final JMenuItem pasteItem = new JMenuItem("Pegar");

    /**
     * Constructor de MyJTextField.
     * Inicializa el menú contextual y añade los listeners de ratón para mostrar el menú.
     */
    public MyJTextField() {
        popupMenu.add(copyItem);
        popupMenu.add(cutItem);
        popupMenu.add(pasteItem);

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

        pasteItem.addActionListener((e) -> {

            // Obtener el portapapeles del sistema
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

            // Intentar obtener el contenido del portapapeles
            try {
                // Verificar si el contenido del portapapeles es de tipo texto
                if (clipboard.isDataFlavorAvailable(DataFlavor.stringFlavor)) {
                    // Obtener el texto del portapapeles
                    String textoPegado = (String) clipboard.getData(DataFlavor.stringFlavor);

                    setText(getText().concat(textoPegado));

                }
            } catch (IOException | UnsupportedFlavorException ex) {
                setText(getText().concat(""));
            }

        });

        cutItem.addActionListener((e) -> {
            // Obtener el texto seleccionado en el JTextArea
            String textoParaCortar = getSelectedText();

            if (textoParaCortar != null) {
                // Crear un objeto StringSelection con el texto seleccionado
                StringSelection stringSelection = new StringSelection(textoParaCortar);

                // Obtener el portapapeles del sistema
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

                // Poner el texto en el portapapeles
                clipboard.setContents(stringSelection, null);

                // Eliminar el texto cortado del JTextArea
                replaceSelection("");

            }

        });

    }

}
