package state.settings.tabs;

import main.Main;
import main.resources.Globals;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.util.ArrayList;

public class NetworkTab extends JPanel {

    protected final JTextField _addressField;
    protected final JTextField _portField;

    protected final ArrayList<JTextField> _fields;

    public NetworkTab() {
        _addressField = new JTextField(Globals.getProperty(Globals.IP_ADDRESS));
        _addressField.setBounds(Main.FRAME_WIDTH / 2 - Main.FRAME_WIDTH / 6, Main.FRAME_HEIGHT / 2 + 4 * 40, 150, 40);
        _addressField.setColumns(11);
        _addressField.setEnabled(false);

        _portField = new JTextField(Globals.getProperty(Globals.PORT));
        _portField.setBounds(Main.FRAME_WIDTH / 2 - Main.FRAME_WIDTH / 6, Main.FRAME_HEIGHT / 2 + 6 * 40, 150, 40);
        _portField.setColumns(4);
        _portField.setEnabled(false);

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
