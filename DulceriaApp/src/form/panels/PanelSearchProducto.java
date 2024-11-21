package form.panels;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.extras.components.FlatFormattedTextField;
import components.MyJTextField;
import components.MyScrollPane;
import components.MyTxtAreaDescrip;
import components.Notify;
import form.FormProducts;
import form.request.RequestProducto;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import model.Producto;
import net.miginfocom.swing.MigLayout;
import raven.modal.ModalDialog;
import raven.modal.Toast;
import raven.modal.component.ModalBorderAction;
import raven.modal.component.SimpleModalBorder;

public class PanelSearchProducto extends JPanel {

    private JButton buttonSearch;
    private JButton buttonClose;

    private JFormattedTextField inputIdProducto;
    private MyTxtAreaDescrip description;

    public PanelSearchProducto() {
        initComponents();
        initListeners();
        init();
    }

    private void initComponents() {
//        NumberFormatter numberFormatter = new NumberFormatter(new DecimalFormat("#"));
//        numberFormatter.setValueClass(Integer.class);
//        numberFormatter.setMaximum(Integer.MAX_VALUE);
//        numberFormatter.setAllowsInvalid(false);
//        numberFormatter.setCommitsOnValidEdit(true);
//        numberFormatter.setOverwriteMode(false);
//        numberFormatter.setMinimum(null);
//
//
//        inputIdProducto = new FlatFormattedTextField();
//        inputIdProducto.setFormatterFactory(new DefaultFormatterFactory(numberFormatter));

        NumberFormatter numberFormatter = new NumberFormatter(NumberFormat.getIntegerInstance());
        numberFormatter.setValueClass(Integer.class);
        numberFormatter.setMaximum(Integer.MAX_VALUE);
        numberFormatter.setAllowsInvalid(true); // Permitir valores no válidos temporalmente

        inputIdProducto = new JFormattedTextField(numberFormatter);
        inputIdProducto.setFocusLostBehavior(JFormattedTextField.PERSIST); // Evitar que se restablezca automáticamente

        inputIdProducto.setValue(null);
        

        description = new MyTxtAreaDescrip("Busca tu producto");
        buttonClose = new JButton("Cerrar");
        buttonSearch = new JButton("Buscar Producto") {
            @Override
            public boolean isDefaultButton() {
                return true;
            }
        };
    }

    private void initListeners() {
        buttonSearch.addActionListener((e) -> ModalBorderAction.getModalBorderAction(buttonSearch).doAction(SimpleModalBorder.OK_OPTION));
        buttonClose.addActionListener((e) -> ModalBorderAction.getModalBorderAction(buttonClose).doAction(SimpleModalBorder.CANCEL_OPTION));
    }

    private void init() {
        setLayout(new MigLayout("fillx,wrap,insets 0 n 0 n", "fill,100:400", "fill"));

        buttonSearch.putClientProperty(FlatClientProperties.STYLE, ""
                + "foreground:#FFFFFF");

        description.putClientProperty(FlatClientProperties.STYLE, ""
                + "[light]foreground:lighten(@foreground,30%);"
                + "[dark]foreground:darken(@foreground,30%);"
                + "background:null");

        inputIdProducto.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Buscar ...");
        inputIdProducto.putClientProperty(FlatClientProperties.TEXT_FIELD_LEADING_ICON, new FlatSVGIcon("resources/icon/search.svg", 0.35f));
        inputIdProducto.putClientProperty(FlatClientProperties.STYLE, ""
                + "iconTextGap:10;"
                + "showClearButton:true");

        add(description);
        add(inputIdProducto, "grow 1,gapy 10");

        add(buttonClose, "split 2,grow 0,gapy 10,al trail");
        add(buttonSearch, "grow 0,gapy 10,al trail");
        updateUI();
        revalidate();
    }

    public void searchProducto() {
        try {
            final String id = "input";
            int idProducto = Integer.parseInt(inputIdProducto.getText().isEmpty() ? "0" : inputIdProducto.getText());
            System.out.println(idProducto);
            Producto producto = RequestProducto.getOneProducto(idProducto);
            if (producto != null) {
                PanelInfoProducto panel = new PanelInfoProducto(producto, null);
                ModalDialog.pushModal(new SimpleModalBorder(panel,
                        "Información del Producto", SimpleModalBorder.DEFAULT_OPTION, (controller, action) -> {
                            if (action == SimpleModalBorder.CANCEL_OPTION) {
                                controller.close();
                            }
                        }), id);
            } else {
                Notify.getInstance().showToast(Toast.Type.ERROR, "No existe el ID");
            }
        } catch (Exception ex) {
            Notify.getInstance().showToast(Toast.Type.ERROR, "Dato No valido");
        }
    }

}
