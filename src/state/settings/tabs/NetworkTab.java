package state.settings.tabs;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import main.Main;

public class NetworkTab extends JPanel {

    protected JTextField _addressField;
    protected JTextField _portField;

    protected ArrayList<JTextField> _fields;


    protected JButton _saveButton;

    protected ArrayList<JButton> _buttons;

    public NetworkTab() {
        String address = null;
        try {
            address = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        _addressField = new JTextField(address);
        _addressField.setBounds(Main.FRAME_WIDTH / 2 - Main.FRAME_WIDTH / 6, Main.FRAME_HEIGHT / 2 + 2 * 40, 150, 40);
        _addressField.setColumns(11);

        _portField = new JTextField("0");
        _portField.setBounds(Main.FRAME_WIDTH / 2 - Main.FRAME_WIDTH / 6, Main.FRAME_HEIGHT / 2 + 4 * 40, 150, 40);
        _portField.setColumns(4);

        _fields = new ArrayList<>();
        _fields.add(_addressField);
        _fields.add(_portField);

        for (JTextField field : _fields) {
            field.setFocusable(true);
            add(field);
        }

        _saveButton = new JButton("Save");

        _buttons = new ArrayList<>();
        _buttons.add(_saveButton);

        for (JButton button : _buttons) {
            button.setFocusable(false);
            add(button);
        }

        setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Network"));
    }

}
