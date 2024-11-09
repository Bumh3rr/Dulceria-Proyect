package system;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import components.RefreshLine;
import java.awt.BorderLayout;
import java.awt.Component;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import net.miginfocom.swing.MigLayout;
import raven.modal.Drawer;

public class MainForm extends JPanel {

    private JPanel mainPanel;
    private RefreshLine refreshLine;

    private JButton buttonUndo;
    private JButton buttonRedo;
    private JButton buttonRefresh;

    public MainForm() {
        init();
    }

    private void init() {
        setLayout(new MigLayout("fillx,wrap,insets 0,gap 0", "[fill]", "[][][fill,grow]"));
        add(createHeader());
        add(createRefreshLine(), "height 3!");
        add(createMain());
//        add(createScrollMain());
    }

    private JPanel createHeader() {
        JPanel panel = new JPanel(new MigLayout("insets 3", "[]push[]push", "[fill]"));
        JToolBar toolBar = new JToolBar();
        buttonUndo = new JButton(new FlatSVGIcon("resources/icon/undo.svg", 0.5f));
        buttonRedo = new JButton(new FlatSVGIcon("resources/icon/redo.svg", 0.5f));
        buttonRefresh = new JButton(new FlatSVGIcon("resources/icon/refresh.svg", 0.5f));

        buttonUndo.addActionListener(e -> FormManager.undo());
        buttonRedo.addActionListener(e -> FormManager.redo());
        buttonRefresh.addActionListener(e -> FormManager.refresh());

        toolBar.add(buttonUndo);
        toolBar.add(buttonRedo);
        toolBar.add(buttonRefresh);
        panel.add(toolBar);
        return panel;
    }

    private JPanel createRefreshLine() {
        refreshLine = new RefreshLine();
        return refreshLine;
    }

    private Component createMain() {
        mainPanel = new JPanel(new BorderLayout());
        return mainPanel;
    }

    public void setForm(Form form) {
        mainPanel.removeAll();
        mainPanel.add(form);
        mainPanel.repaint();
        mainPanel.revalidate();

        // check button
        buttonUndo.setEnabled(FormManager.FORMS.isUndoAble());
        buttonRedo.setEnabled(FormManager.FORMS.isRedoAble());
    }

    public void refresh() {
        refreshLine.refresh();
    }

}
