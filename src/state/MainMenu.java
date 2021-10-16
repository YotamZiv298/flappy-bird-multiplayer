package state;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import main.GamePanel;
import main.Main;
import state.settings.Settings;

public class MainMenu extends JPanel {

    protected JButton _startButton;
    protected JButton _settingsButton;
    protected JButton _exitButton;

    protected ArrayList<JButton> _buttons;

    public MainMenu() {
        setLayout(null);

        _startButton = new JButton("Start");
        _startButton.setBounds(Main.FRAME_WIDTH / 2 - Main.FRAME_WIDTH / 6, Main.FRAME_HEIGHT / 2, 150, 40);

        _settingsButton = new JButton("Settings");
        _settingsButton.setBounds(Main.FRAME_WIDTH / 2 - Main.FRAME_WIDTH / 6, Main.FRAME_HEIGHT / 2 + 2 * 40, 150, 40);

        _exitButton = new JButton("Exit");
        _exitButton.setBounds(Main.FRAME_WIDTH / 2 - Main.FRAME_WIDTH / 6, Main.FRAME_HEIGHT / 2 + 4 * 40, 150, 40);

        _buttons = new ArrayList<>();
        _buttons.add(_startButton);
        _buttons.add(_settingsButton);
        _buttons.add(_exitButton);

        for (JButton button : _buttons) {
//            button.setBackground(Color.gray);
//            button.setForeground(Color.black);
//            button.setFont(new Font("Tahoma", Font.BOLD, 36));
//            button.setBorderPainted(false);
//            button.setFocusPainted(false);
//            button.setContentAreaFilled(false);
            button.setFocusable(false);
            add(button);
        }

        _startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame jframe = (JFrame) SwingUtilities.getWindowAncestor(MainMenu.this);
                GamePanel gamePanel = new GamePanel();

                setVisible(false);
                jframe.add(gamePanel);
                jframe.invalidate();
                jframe.validate();
            }
        });
        _settingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Settings();
            }
        });
        _exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        add(_startButton);
        add(_settingsButton);
        add(_exitButton);
    }

}
