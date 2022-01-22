package state.settings.tabs;

import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextField;

import main.Main;
import main.resources.Globals;

public class NetworkTab extends JPanel {

    protected JTextField _addressField;
    protected JTextField _portField;

    protected ArrayList<JTextField> _fields;

    public NetworkTab() {
        _addressField = new JTextField(Globals.getProperty(Globals.IP_ADDRESS));
        _addressField.setBounds(Main.FRAME_WIDTH / 2 - Main.FRAME_WIDTH / 6, Main.FRAME_HEIGHT / 2 + 2 * 40, 150, 40);
        _addressField.setColumns(11);

        _portField = new JTextField(Globals.getProperty(Globals.PORT));
        _portField.setBounds(Main.FRAME_WIDTH / 2 - Main.FRAME_WIDTH / 6, Main.FRAME_HEIGHT / 2 + 4 * 40, 150, 40);
        _portField.setColumns(4);

        _fields = new ArrayList<>();
        _fields.add(_addressField);
        _fields.add(_portField);

        for (JTextField field : _fields) {
            field.setFocusable(true);
            add(field);
        }

        setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Network"));
    }

}
