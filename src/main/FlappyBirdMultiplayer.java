package main;

import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import framework.Keyboard;
import state.MainMenu;

public class FlappyBirdMultiplayer extends JFrame {

    protected MainMenu _mainMenu;

    protected ArrayList<JPanel> _panels;

    public FlappyBirdMultiplayer() {
        super("Flappy Bird Multiplayer");

        _mainMenu = new MainMenu();

        _panels = new ArrayList<>();
        _panels.add(_mainMenu);

        for (JPanel panel : _panels) {
            add(panel);
        }

        addKeyListener(Keyboard.getInstance());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(Main.FRAME_WIDTH, Main.FRAME_HEIGHT));
        pack();
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }

}
