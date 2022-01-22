package state.settings;

import java.awt.Dimension;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import main.Main;
import state.settings.tabs.AboutTab;
import state.settings.tabs.GeneralTab;
import state.settings.tabs.NetworkTab;

public class Settings extends JDialog {

    protected GeneralTab _generalPanel;
    protected NetworkTab _networkPanel;
    protected AboutTab _aboutPanel;

    public Settings() {
        setTitle("Settings");
        setModal(true);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFocusable(false);

        Map<String, JPanel> panels = initPanels();

        for (Map.Entry<String, JPanel> entry : panels.entrySet()) {
            tabbedPane.add(entry.getKey(), entry.getValue());
        }

        add(tabbedPane);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(Main.FRAME_WIDTH / 2 + 50, 3 * Main.FRAME_HEIGHT / 4));
        pack();
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public Map<String, JPanel> initPanels() {
        Map<String, JPanel> components = new LinkedHashMap<>();

        _generalPanel = new GeneralTab();
        _networkPanel = new NetworkTab();
        _aboutPanel = new AboutTab();

        components.put("General", _generalPanel);
        components.put("Network", _networkPanel);
        components.put("About", _aboutPanel);

        return components;
    }

}
