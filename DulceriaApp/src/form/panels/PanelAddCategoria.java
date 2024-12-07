package form.panels;

import com.formdev.flatlaf.FlatClientProperties;
import components.MyJTextField;
import components.MyScrollPane;
import components.MyTxtAreaDescrip;
import components.Notify;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;


import dao.request.RequestCategoria;
import model.Categoria;
import net.miginfocom.swing.MigLayout;
import raven.modal.Toast;
import raven.modal.component.ModalBorderAction;
import raven.modal.component.SimpleModalBorder;
import raven.modal.listener.ModalController;
import raven.modal.toast.ToastPromise;

/**
 * PanelAddCategoria es un JPanel personalizado que permite agregar una nueva categoría.
 */
public class PanelAddCategoria extends JPanel {

    private String KEY = getClass().getName();
    private MyTxtAreaDescrip description;
    private MyJTextField inputNombreCategoria;
    private JButton button;

    /**
     * Constructor de PanelAddCategoria.
     * Inicializa los componentes, listeners y configuración del panel.
     */
    public PanelAddCategoria() {
        initComponents();
        initListeners();
        init();
    }

    /**
     * Inicializa los componentes del panel.
     */
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

    /**
     * Inicializa los listeners de los componentes.
     */
    private void initListeners() {
        button.addActionListener((e)
                -> ModalBorderAction.getModalBorderAction(button).doAction(SimpleModalBorder.OK_OPTION)
        );
    }

    /**
     * Configura el diseño y propiedades del panel.
     */
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

    /**
     * Realiza la inserción de la nueva categoría.
     *
     * @param controller el controlador del modal.
     */
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

                            System.out.println(e.toString());
                            controller.consume();
                        }
                    }
                });
    }

    /**
     * Inserta la nueva categoría en la base de datos.
     *
     * @return true si la inserción fue exitosa, false en caso contrario.
     * @throws Exception si ocurre un error durante la inserción.
     */
    private Boolean insert() throws Exception {
        Toast.closeAll();
        String categoriaName = inputNombreCategoria.getText().strip();

        if (categoriaName.isEmpty()) {
            Notify.getInstance().showToast(Toast.Type.WARNING, "El campo es Requerido");
            return false;
        }
        return RequestCategoria.addCategoria(new Categoria(categoriaName));
    }

}