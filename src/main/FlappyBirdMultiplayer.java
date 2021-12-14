package main;

import java.awt.CardLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

import framework.Keyboard;
import state.MainMenu;

public class FlappyBirdMultiplayer extends JFrame {

    public static CardLayout appCardLayout;
    public static JPanel appPanelContainer;

    private MainMenu _mainMenu;
//    private GamePanel _gamePanel;

//    protected ArrayList<JPanel> _panels;

    public FlappyBirdMultiplayer() {
        super("Flappy Bird Multiplayer");

        appCardLayout = new CardLayout();
        appPanelContainer = new JPanel(appCardLayout);

//        _mainMenu = new MainMenu();
//        _gamePanel = new GamePanel();

        appPanelContainer.add(new MainMenu(), MainMenu.class.getSimpleName());
//        appPanelContainer.add(_gamePanel, GamePanel.class.getSimpleName());

        appCardLayout.show(appPanelContainer, MainMenu.class.getSimpleName());

//        _panels = new ArrayList<>();
//        _panels.add(_mainMenu);
//        _panels.add(_gamePanel);


//        for (JPanel panel : _panels) {
//            add(panel);
//        }

        add(appPanelContainer);

        addKeyListener(Keyboard.getInstance());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(Main.FRAME_WIDTH, Main.FRAME_HEIGHT));
        pack();
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }

}
