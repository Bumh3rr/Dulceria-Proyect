package main;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import com.formdev.flatlaf.themes.FlatMacLightLaf;
import com.formdev.flatlaf.util.SystemInfo;
import components.Notify;
import dao.pool.PoolConexion;
import drawer.DrawerBuildDulceria;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.UIManager;
import raven.modal.Drawer;
import raven.modal.ModalDialog;
import raven.modal.option.BorderOption;
import system.FormManager;

/**
 * DulceriaApp es la clase principal de la aplicación Dulceria, que extiende JFrame.
 * Inicializa la ventana de la aplicación y configura varios componentes y ajustes de la interfaz de usuario.
 */
public class DulceriaApp extends JFrame {
    /**
     * Constructor de DulceriaApp.
     * Inicializa la ventana de la aplicación y configura varios componentes y ajustes de la interfaz de usuario.
     */
    public DulceriaApp() {
        init();
    }

    /**
     * Método de inicialización.
     * Configura la ventana de la aplicación y varios componentes de la interfaz de usuario.
     */
    private void init() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Notify.install(this);
        Drawer.installDrawer(this, new DrawerBuildDulceria());
        FormManager.getInstance().install(this);
        setSize(new Dimension(1440, 823));
        setLocationRelativeTo(null);

        ModalDialog.getDefaultOption()
                .setOpacity(0.4f)
                .getBorderOption()
                .setShadow(BorderOption.Shadow.DOUBLE_EXTRA_LARGE);

        if (SystemInfo.isMacOS) {
            System.setProperty("apple.awt.application.appearance", "system");
            System.setProperty("apple.laf.useScreenMenuBar", "true");
            System.setProperty("apple.awt.application.name", "Flif Flop");
            System.setProperty("apple.awt.application.appearance", "system");
            getRootPane().putClientProperty("apple.awt.windowTitleVisible", false);
            getRootPane().putClientProperty("apple.awt.fullscreenable", true);
        } else if (SystemInfo.isWindows) {
            System.setProperty("Flatlaf.useWindowDecorations", "true");
            System.setProperty("JRootPane.useWindowDecorations", "true");
            System.setProperty("TitlePane.useWindowDecorations", "true");
        }
        if (SystemInfo.isMacFullWindowContentSupported) {
            getRootPane().putClientProperty("apple.awt.transparentTitleBar", true);
        }
        new Thread(() -> {
            try {
                PoolConexion.getInstance();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    /**
     * Método principal.
     * Configura y lanza la aplicación Dulceria.
     *
     * @param args Argumentos de la línea de comandos.
     */
    public static void main(String[] args) {
        FlatLaf.registerCustomDefaultsSource("themes");
        FlatMacLightLaf.setup();
        FlatRobotoFont.install();
        UIManager.put("defaultFont", new Font(FlatRobotoFont.FAMILY, Font.PLAIN, 13));
        EventQueue.invokeLater(() -> new DulceriaApp().setVisible(true));
    }
}