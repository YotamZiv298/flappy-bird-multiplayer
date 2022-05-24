package main;

import client.Client;
import client.framework.ClientListener;
import framework.ConnectionDisplay;
import framework.Keyboard;
import main.resources.Globals;
import main.resources.OSDetector;
import state.MainMenu;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.util.Random;

public class FlappyBirdMultiplayer extends JFrame {

    private static final Random random = new Random();
    public static CardLayout appCardLayout;
    public static JPanel appPanelContainer;

    public static Client client;
    public static ConnectionDisplay connectionDisplay;

    public static String playerName = "Player" + String.format("%06d", random.nextInt(999999));

    public static final String GAME_LOGO = OSDetector.isWindows() ? "src\\images\\bird.png" : "src/images/bird.png";

    public FlappyBirdMultiplayer() {
        super("Flappy Bird Multiplayer");

        appCardLayout = new CardLayout();
        appPanelContainer = new JPanel(appCardLayout);

        ClientListener clientListener = new ClientListener();
        client = new Client(Globals.getProperty(Globals.IP_ADDRESS), Integer.parseInt(Globals.getProperty(Globals.PORT)), clientListener);

        connectionDisplay = new ConnectionDisplay();
        connectionDisplay.setPreferredSize(new Dimension(0, 10));

        appPanelContainer.add(new MainMenu(), MainMenu.class.getSimpleName());

        appCardLayout.show(appPanelContainer, MainMenu.class.getSimpleName());

        add(connectionDisplay, BorderLayout.PAGE_START);
        add(appPanelContainer, BorderLayout.CENTER);

        setIconImage(new ImageIcon(GAME_LOGO).getImage());
        setFocusable(true);
        addKeyListener(Keyboard.getInstance());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(Main.FRAME_WIDTH, Main.FRAME_HEIGHT));
        pack();
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }

}
