
package components;

import javax.swing.BorderFactory;
import javax.swing.JTextArea;

public class MyTxtAreaDescrip extends JTextArea {
    public MyTxtAreaDescrip(String text) {
        super(text);
        setEditable(false);
        setBorder(BorderFactory.createEmptyBorder());
    }
}
