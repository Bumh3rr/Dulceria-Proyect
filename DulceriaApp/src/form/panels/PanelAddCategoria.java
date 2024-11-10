package form.panels;

import com.formdev.flatlaf.FlatClientProperties;
import components.MyJTextField;
import components.MyScrollPane;
import components.MyTxtAreaDescrip;
import components.Notify;
import form.FormProducts;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import model.Categoria;
import net.miginfocom.swing.MigLayout;
import raven.modal.Toast;
import raven.modal.component.ModalBorderAction;
import raven.modal.component.SimpleModalBorder;
import raven.modal.listener.ModalController;
import raven.modal.toast.ToastPromise;
import system.AllForms;

public class PanelAddCategoria extends JPanel {

    private String KEY = getClass().getName();
    private MyTxtAreaDescrip description;
    private MyJTextField inputNombreCategoria;
    private JButton button;

    public PanelAddCategoria() {
        initComponents();
        initListeners();
        init();
    }

    private void initComponents() {
        description = new MyTxtAreaDescrip("Agrega una nueva categoria");
        inputNombreCategoria = new MyJTextField();
        button = new JButton("Agregar Categoria") {
            @Override
            public boolean isDefaultButton() {
                return true;
            }
        };
    }

    private void initListeners() {
        button.addActionListener((e)
                -> ModalBorderAction.getModalBorderAction(button).doAction(SimpleModalBorder.OK_OPTION)
        );
    }

    private void init() {
        setLayout(new MigLayout("fillx,insets 0", "[center]", "[center]"));

        JPanel panel = new JPanel(new MigLayout("wrap,fillx,insets 0 45 0 45", "fill,400!"));
        button.putClientProperty(FlatClientProperties.STYLE, "" + "foreground:#FFFFFF");

        description.putClientProperty(FlatClientProperties.STYLE, ""
                + "[light]foreground:lighten(@foreground,30%);"
                + "[dark]foreground:darken(@foreground,30%);"
                + "background:null");

        inputNombreCategoria.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Nombre de la Categoria");
        inputNombreCategoria.putClientProperty(FlatClientProperties.STYLE, ""
                + "showClearButton:true");

        panel.add(description);
        panel.add(new JLabel("Categoria"));
        panel.add(inputNombreCategoria);
        panel.add(button, "grow 0,gapy 10,al trail");

        add(new MyScrollPane(panel));
        updateUI();
        revalidate();
    }

    public void commitInserts(ModalController controller) {
        if (Toast.checkPromiseId(KEY)) {
            controller.consume();
            return;
        }

        Toast.showPromise(SwingUtilities.windowForComponent(this), "Agregar", Notify.getInstance().getSelectedOptionTop(),
                new ToastPromise(KEY) {
            @Override
            public void execute(ToastPromise.PromiseCallback toas) {
                try {
                    toas.update("Verificando");
                    if (insert()) {
                        toas.done(Toast.Type.SUCCESS, "Categoria Agregado Exitoxamente");
                        controller.close();
                    } else {
                        toas.done(Toast.Type.ERROR, "Operación fallida");
                    }
                } catch (Exception e) {
                    if (e.getMessage().contains("Data too long")) {
                        toas.done(Toast.Type.WARNING, "Has Revasado el Limite de Caracteres\n"
                                + e.getLocalizedMessage());
                    } else {
                        toas.done(Toast.Type.ERROR, "Hubo un problema al Categoria   ala base de datos"
                                + "\nCausa: " + e.getLocalizedMessage());
                    }
                    controller.consume();
                }
            }
        });
    }

    private Boolean insert() throws Exception {
        Toast.closeAll();
        String categoriaName = inputNombreCategoria.getText().strip();

        if (categoriaName.isEmpty()) {
            Notify.getInstance().showToast(Toast.Type.WARNING, "El campo es Requerido");
            return false;
        }
        return FormProducts.ProductoRequest.addCategoria(new Categoria(categoriaName));
    }

}
