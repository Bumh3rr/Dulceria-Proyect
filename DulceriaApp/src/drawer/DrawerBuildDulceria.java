package drawer;

import com.formdev.flatlaf.FlatClientProperties;
import form.FormProducts;
import form.FormSupplier;
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

public class DrawerBuildDulceria extends SimpleDrawerBuilder {

    private final int SHADOW_SIZE = 12;

    public DrawerBuildDulceria() {
        super(createSimpleMenuOption());
        footer.setVisible(false);
    }

    @Override
    public SimpleFooterData getSimpleFooterData() {
        return new SimpleFooterData()
                .setTitle("")
                .setDescription("V");
    }

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

    public static MenuOption createSimpleMenuOption() {
        MenuOption simpleMenuOption = new MenuOption();
        MenuItem items[] = new MenuItem[]{
            new Item.Label("PRINCIPAL"),
            new Item("Ventas", "chart.svg", FormProducts.class),
            new Item("Casa", "dashboard.svg", FormProducts.class),
            new Item("Notas", "forms.svg", FormProducts.class),
            new Item("Proveedor", "user2.svg", FormSupplier.class),
            new Item("Acerca De", "about.svg"),
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
                if (i == 5) {
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

    @Override
    public int getDrawerWidth() {
        return 270 + SHADOW_SIZE;
    }

    @Override
    public int getDrawerCompactWidth() {
        return 80 + SHADOW_SIZE;
    }

    @Override
    public int getOpenDrawerAt() {
        return 1000;
    }

    @Override
    public Option getOption() {
        Option option = super.getOption();
        option.setOpacity(0.3f);
        option.getBorderOption()
                .setShadowSize(new Insets(0, 0, 0, SHADOW_SIZE));
        return option;
    }

    @Override
    public boolean openDrawerAtScale() {
        return false;
    }

    @Override
    public void build(DrawerPanel drawerPanel) {
        drawerPanel.putClientProperty(FlatClientProperties.STYLE, getDrawerBackgroundStyle());
    }

    private static String getDrawerBackgroundStyle() {
        return ""
                + "[light]background:tint($Panel.background,100%);"
                + "[dark]background:tint($Panel.background,5%);";
    }

}
