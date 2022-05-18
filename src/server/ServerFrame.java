package server;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.DefaultCaret;
import java.awt.BorderLayout;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ServerFrame extends JFrame {

    private final FlappyBirdMultiplayerServer _flappyBirdMultiplayerServer;

    private static JTextArea _logTextArea;
    private static SimpleDateFormat _dateFormat;
    private final JButton _switchButton;

    public ServerFrame() {
        super("Flappy Bird Multiplayer Server");

        setLayout(new BorderLayout());

        _flappyBirdMultiplayerServer = new FlappyBirdMultiplayerServer();

        _logTextArea = new JTextArea(15, 40);
        _logTextArea.setLineWrap(true);
        _logTextArea.setWrapStyleWord(true);
        _logTextArea.setEditable(false);
        ((DefaultCaret) _logTextArea.getCaret()).setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        _dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        _switchButton = new JButton("Turn Off Server");
        _switchButton.addActionListener(e -> {
            _flappyBirdMultiplayerServer.turnOff();
            _switchButton.setEnabled(false);
        });

        add(_logTextArea, BorderLayout.CENTER);
        add(_switchButton, BorderLayout.SOUTH);

        add(new JScrollPane(_logTextArea));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void addLog(String text) {
        String log = String.format("[ %s ] : %s\n", _dateFormat.format(new Date()), text);

        _logTextArea.append(log);
    }

}
