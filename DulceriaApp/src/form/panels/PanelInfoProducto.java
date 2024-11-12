package form.panels;

import com.formdev.flatlaf.FlatClientProperties;
import components.FieldTextArea;
import components.MyLabelTitle;
import components.MyTxtAreaDescrip;
import form.FormProducts;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import model.Producto;
import net.miginfocom.swing.MigLayout;
import raven.extras.AvatarIcon;
import raven.modal.component.ModalBorderAction;
import raven.modal.component.SimpleModalBorder;

public class PanelInfoProducto extends JPanel {

    private FormProducts form;
    private Producto producto;
    private final String KEY = getClass().getName();

    private FieldTextArea fieldProductNombre;
    private FieldTextArea fieldMarca;
    private FieldTextArea fieldDescription;
    private FieldTextArea fieldStock;
    private FieldTextArea fieldPrecio_Venta;
    private FieldTextArea fieldPrecio_Compra;
    private FieldTextArea fieldIdProveedor;
    private FieldTextArea fieldNombreProveedor;
    private FieldTextArea fieldCategoria;

    private JButton buttonUpdate;
    private JButton buttonDelete;
    private JButton buttonClose;

    public PanelInfoProducto(Producto producto, FormProducts form) {
        this.producto = producto;
        this.form = form;
        intComponents();
        initListeners();
        init();
    }

    private void intComponents() {

        fieldProductNombre = new FieldTextArea(producto.getNombre());
        fieldMarca = new FieldTextArea(producto.getMarca());
        fieldDescription = new FieldTextArea(producto.getDescripcion());
        fieldStock = new FieldTextArea(String.valueOf(producto.getStock()));
        fieldPrecio_Venta = new FieldTextArea(String.valueOf(producto.getPrecio_Venta()));
        fieldPrecio_Compra = new FieldTextArea(String.valueOf(producto.getPrecio_Compra()));
        fieldIdProveedor = new FieldTextArea(String.valueOf(producto.getProveedor().getId()));
        fieldNombreProveedor = new FieldTextArea(producto.getProveedor().getFirst_name());
        fieldCategoria = new FieldTextArea(producto.getCategoria().getTipo());

        buttonUpdate = new JButton("Actualizar");
        buttonDelete = new JButton("Eliminar");
        buttonClose = new JButton("Cerrar") {
            @Override
            public boolean isDefaultButton() {
                return true;
            }
        };

    }

    private void initListeners() {
        buttonUpdate.addActionListener((e) -> {
            ModalBorderAction.getModalBorderAction(buttonUpdate).doAction(SimpleModalBorder.OK_OPTION);
        });

        buttonDelete.addActionListener((e) -> {
            ModalBorderAction.getModalBorderAction(buttonDelete).doAction(SimpleModalBorder.CANCEL_OPTION);
        });
        buttonClose.addActionListener((e) -> {
            ModalBorderAction.getModalBorderAction(buttonClose).doAction(SimpleModalBorder.CLOSE_OPTION);
        });
    }

//    public void refreshFields() {
//        try {
//            this.tecnico = ThreadsPool.getInstance().getExecutorService().submit(() -> {
//                try {
//                    return DataOperations.getTecnico(tecnico.getId());
//                } catch (Exception e) {
//                    throw new Exception(e);
//                }
//            }).get();
//            fieldFirtsName.setTextField(tecnico.getFirsName());
//            fieldLastName.setTextField(tecnico.getLastName());
//            fieldPhone.setTextField("+52 ".concat(tecnico.getPhone()));
//            fieldState.setTextField(tecnico.getState());
//            fieldMunicipality.setTextField(tecnico.getMunicipality());
//            fieldCologne.setTextField(tecnico.getCologne());
//            fieldDateRegister.setTextField(tecnico.getDateTimeFull(tecnico.getDate_register()));
//            fieldDateLow.setTextField(tecnico.getDate_low() != null ? tecnico.getDateTimeFull(tecnico.getDate_low()) : "");
//            fieldZip.setTextField(tecnico.getZip());
//
//            SwingUtilities.invokeLater(() -> {
//                changeStatusLabel(this.tecnico);
//                buttonActiveOrLow.setText(tecnico.getDate_low() != null ? "Remover Baja" : "Baja");
//                buttonActiveOrLow.putClientProperty(FlatClientProperties.STYLE, ""
//                        + ((tecnico.getDate_low() != null) ? "background:#66a73b;" : "background:#FD961A;")
//                        + "foreground:#FFFFFF;"
//                        + "font:bold +0");
//            });
//
//        } catch (Exception e) {
//            System.out.println(e.fillInStackTrace());
//        }
//    }
    private void init() {
        setLayout(new MigLayout("fill,wrap,insets 0 20 20 20 20", "fill,450!"));
        add(new JLabel(new AvatarIcon(getClass().getResource("/resources/dulce.png"), 100, 100, 16)), "split 2,grow 0");
        add(createHeader("Producto", "ID: " + producto.getId(), 1));
        add(body());
    }

    public JComponent createHeader(String title, String description, int size) {
        JPanel panel = new JPanel(new MigLayout("fill,wrap,insets 5 10 5 10", "[fill]"));
        panel.add(new MyLabelTitle(title, JLabel.LEFT, (4 - size)));
        panel.add(new MyTxtAreaDescrip(description), "grow 0");
        return panel;
    }

    private JComponent body() {
        JPanel panel = new JPanel(new MigLayout("wrap 2,fillx,insets n", "fill", "fill"));

        buttonDelete.putClientProperty(FlatClientProperties.STYLE, ""
                + "background:#FF5733;"
                + "foreground:#FFFFFF;"
                + "font:bold +0");
        buttonUpdate.putClientProperty(FlatClientProperties.STYLE, ""
                + "background:#FDC211;"
                + "foreground:#FFFFFF;"
                + "font:bold +0");

        buttonClose.putClientProperty(FlatClientProperties.STYLE, ""
                + "foreground:#FFFFFF");

        panel.add(new JSeparator(), "span 2, grow 1");
        panel.add(new FieldTextArea("Datos del Producto", 4), "span 2,al center");
        panel.add(getLabelSubTitle("Nombre:"));
        panel.add(getLabelSubTitle("Marca:"));
        panel.add(fieldProductNombre);
        panel.add(fieldMarca);
        panel.add(getLabelSubTitle("Descripcion:"));
        panel.add(getLabelSubTitle("Unidades Disponibles:"));
        panel.add(fieldDescription);
        panel.add(fieldStock);

        panel.add(getLabelSubTitle("Categoria:"), "wrap");
        panel.add(fieldCategoria, "wrap");

        panel.add(new JSeparator(), "span 2, grow 1");
        panel.add(new JLabel("Datos del Proveedor"), "span 2,grow 0,al center");
        panel.add(getLabelSubTitle("ID:"));
        panel.add(getLabelSubTitle("Nombre:"));
        panel.add(fieldIdProveedor);
        panel.add(fieldNombreProveedor);
        panel.add(new JSeparator(), "span 2, grow 1");

        panel.add(createAccions(), "span 2,grow 0,al center");

        return panel;
    }

    private JComponent createAccions() {
        JPanel panel = new JPanel(new MigLayout("fill,insets n", "fill"));
        panel.add(buttonUpdate);
        panel.add(buttonDelete);
        panel.add(buttonClose);
        return panel;
    }

    private JLabel getLabelSubTitle(String title) {
        JLabel label = new JLabel(title);
        label.putClientProperty(FlatClientProperties.STYLE, ""
                + "[light]foreground:lighten(@foreground,30%);"
                + "[dark]foreground:darken(@foreground,30%);"
                + "font:13");

        return label;
    }

//    public void commitLow(int id, StatusTecnhnican request) {
//        if (Toast.checkPromiseId(KEY)) {
//            return;
//        }
//
//        Toast.showPromise(FormsManager.getFrame(), (request.equals(ACTIVO)) ? "Remover Baja" : "Baja", Notify.getInstance().getSelectedOption(),
//                new ToastPromise(KEY) {
//            @Override
//            public void execute(ToastPromise.PromiseCallback toas) {
//                try {
//                    toas.update("Verificando");
//                    if (CommitsTechnician.lowTecnico(id, (request.equals(ACTIVO)) ? null : Timestamp.valueOf(LocalDateTime.now()))) {
//                        new Thread(() -> form.formOpen()).start();
//                        refreshFields();
//                        toas.done(Toast.Type.SUCCESS, "Operación Exitosamente");
//                    } else {
//                        toas.done(Toast.Type.ERROR, "Operación fallida");
//                    }
//                } catch (Exception e) {
//                    toas.done(Toast.Type.ERROR, e.getLocalizedMessage());
//                }
//            }
//        });
//    }
//    public void showDeleteTecnico(ModalController modalController) {
//        MessageAlerts.getInstance().showMessage("ELiminar Tecnico", "Sugero que quieres eliminar al Tecnico", MessageAlerts.MessageType.WARNING, MessageAlerts.YES_NO_OPTION, new PopupCallbackAction() {
//            @Override
//            public void action(PopupController pc, int i) {
//                if (i == MessageAlerts.YES_OPTION) {
//                    commitDelete(modalController, pc, tecnico.getId());
//                }
//            }
//        });
//    }
//    public void commitDelete(ModalController modalController, PopupController pc, int id) {
//        if (Toast.checkPromiseId(KEY)) {
//            pc.consume();
//            return;
//        }
//
//        Toast.showPromise(FormsManager.getFrame(), "Eliminando...", Notify.getInstance().getSelectedOption(),
//                new ToastPromise(KEY) {
//            @Override
//            public void execute(ToastPromise.PromiseCallback toas) {
//                try {
//                    toas.update("Verificando");
//                    if (CommitsTechnician.deleteTecnico(id)) {
//                        new Thread(() -> form.formRefresh()).start();
//                        pc.closePopup();
//                        modalController.close();
//                        toas.done(Toast.Type.SUCCESS, "Tecnico Eliminado Exitosamente");
//                    } else {
//                        toas.done(Toast.Type.ERROR, "Operación fallida");
//                    }
//                } catch (Exception e) {
//                    toas.done(Toast.Type.ERROR, e.getLocalizedMessage());
//                }
//            }
//        });
//    }
}
