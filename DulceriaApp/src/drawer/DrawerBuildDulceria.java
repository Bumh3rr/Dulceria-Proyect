package drawer;

import com.formdev.flatlaf.FlatClientProperties;
import dao.pool.PoolConexion;
import form.FormBuys;
import form.FormProducts;
import form.FormProveedor;
import form.FormProveedoresAndEmpleados;
import java.awt.Insets;
import java.util.Arrays;
import javax.swing.JComponent;
import raven.extras.AvatarIcon;
import raven.modal.drawer.DrawerPanel;
import raven.modal.drawer.data.Item;
import raven.modal.drawer.data.MenuItem;
import raven.modal.drawer.menu.MenuAction;
import raven.modal.drawer.menu.MenuEvent;
import raven.modal.drawer.menu.MenuOption;
import raven.modal.drawer.menu.MenuStyle;
import raven.modal.drawer.renderer.DrawerStraightDotLineStyle;
import raven.modal.drawer.simple.SimpleDrawerBuilder;
import raven.modal.drawer.simple.footer.SimpleFooterData;
import raven.modal.drawer.simple.header.SimpleHeaderData;
import raven.modal.option.Option;
import system.AllForms;
import system.Form;
import system.FormManager;
/**
 * DrawerBuildDulceria es una clase que extiende SimpleDrawerBuilder para construir un menú de navegación personalizado para la aplicación Dulceria.
 */
public class DrawerBuildDulceria extends SimpleDrawerBuilder {

    private final int SHADOW_SIZE = 12;
    /**
     * Constructor de DrawerBuildDulceria que inicializa el menú simple y oculta el pie de página.
     */
    public DrawerBuildDulceria() {
        super(createSimpleMenuOption());
        footer.setVisible(false);
    }
    /**
     * Obtiene los datos del pie de página simple.
     *
     * @return un objeto SimpleFooterData con el título y la descripción del pie de página
     */
    @Override
    public SimpleFooterData getSimpleFooterData() {
        return new SimpleFooterData()
                .setTitle("")
                .setDescription("V");
    }

    /**
     * Obtiene los datos del encabezado simple.
     *
     * @return un objeto SimpleHeaderData con el icono, el título y la descripción del encabezado
     */
    @Override
    public SimpleHeaderData getSimpleHeaderData() {
        AvatarIcon icon = new AvatarIcon(DrawerBuildDulceria.class.getResource("/resources/logo_default.png"), 50, 50, 3.5f);
        icon.setType(AvatarIcon.Type.MASK_SQUIRCLE);
        icon.setBorder(2, 2);

        return new SimpleHeaderData()
                .setIcon(icon)
                .setTitle("Dulceria App")
                .setDescription("");

    }
    /**
     * Crea una opción de menú simple con elementos predefinidos.
     *
     * @return un objeto MenuOption con la configuración del menú
     */
    public static MenuOption createSimpleMenuOption() {
        MenuOption simpleMenuOption = new MenuOption();
        MenuItem items[] = new MenuItem[]{
            new Item.Label("PRINCIPAL"),
            new Item("Ventas", "ic_buys.svg", FormBuys.class),
            new Item("Productos", "ic_products.svg", FormProducts.class),
            new Item("Proveedor", "user2.svg", FormProveedoresAndEmpleados.class),
            new Item("Cerrar Sesión", "logout.svg")
        };

        simpleMenuOption.setMenuStyle(new MenuStyle() {

            @Override
            public void styleMenu(JComponent component) {
                component.putClientProperty(FlatClientProperties.STYLE, getDrawerBackgroundStyle());
            }
        });

        simpleMenuOption.getMenuStyle().setDrawerLineStyleRenderer(new DrawerStraightDotLineStyle());
        simpleMenuOption.setMenuItemAutoSelectionMode(MenuOption.MenuItemAutoSelectionMode.SELECT_SUB_MENU_LEVEL);
        simpleMenuOption.addMenuEvent(new MenuEvent() {
            @Override
            public void selected(MenuAction action, int[] index) {
                System.out.println("Drawer menu selected " + Arrays.toString(index));
                Class<?> itemClass = action.getItem().getItemClass();
                int i = index[0];
                if (i == 3) {
                    Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                        try {
                            PoolConexion.getInstance().closePool();
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }));
                    System.exit(0);
                }
                if (itemClass == null || !Form.class.isAssignableFrom(itemClass)) {
                    action.consume();
                    return;
                }
                Class<? extends Form> formClass = (Class<? extends Form>) itemClass;
                FormManager.showFormMain(AllForms.getInstance().getForm(formClass));
            }
        });

        simpleMenuOption.setMenus(items)
                .setBaseIconPath("resources/icon")
                .setIconScale(0.45f);

        return simpleMenuOption;
    }
    /**
     * Obtiene el ancho del drawer.
     *
     * @return el ancho del drawer en píxeles
     */
    @Override
    public int getDrawerWidth() {
        return 270 + SHADOW_SIZE;
    }
    /**
     * Obtiene el ancho compacto del drawer.
     *
     * @return el ancho compacto del drawer en píxeles
     */
    @Override
    public int getDrawerCompactWidth() {
        return 80 + SHADOW_SIZE;
    }
    /**
     * Obtiene el ancho compacto del drawer.
     *
     * @return el ancho compacto del drawer en píxeles
     */
    @Override
    public int getOpenDrawerAt() {
        return 1000;
    }
    /**
     * Obtiene la opción de configuración del drawer.
     *
     * @return un objeto Option con la configuración del drawer
     */
    @Override
    public Option getOption() {
        Option option = super.getOption();
        option.setOpacity(0.3f);
        option.getBorderOption()
                .setShadowSize(new Insets(0, 0, 0, SHADOW_SIZE));
        return option;
    }
    /**
     * Indica si el drawer se abre a escala.
     *
     * @return false, indicando que el drawer no se abre a escala
     */
    @Override
    public boolean openDrawerAtScale() {
        return false;
    }
    /**
     * Construye el panel del drawer.
     *
     * @param drawerPanel el panel del drawer a construir
     */
    @Override
    public void build(DrawerPanel drawerPanel) {
        drawerPanel.putClientProperty(FlatClientProperties.STYLE, getDrawerBackgroundStyle());
    }
    /**
     * Obtiene el estilo de fondo del drawer.
     *
     * @return una cadena con el estilo de fondo del drawer
     */
    private static String getDrawerBackgroundStyle() {
        return ""
                + "[light]background:tint($Panel.background,100%);"
                + "[dark]background:tint($Panel.background,5%);";
    }

}
