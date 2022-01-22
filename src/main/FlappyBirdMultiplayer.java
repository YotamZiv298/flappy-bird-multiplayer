package main;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

import client.Client;
import client.framework.ClientListener;
import framework.ConnectionDisplay;
import framework.Keyboard;
import main.resources.Globals;
import state.MainMenu;

public class FlappyBirdMultiplayer extends JFrame {

    public static CardLayout appCardLayout;
    public static JPanel appPanelContainer;

    public static Client client;
    private ClientListener _clientListener = new ClientListener();
    public static ConnectionDisplay connectionDisplay;

    public FlappyBirdMultiplayer() {
        super("Flappy Bird Multiplayer");

        appCardLayout = new CardLayout();
        appPanelContainer = new JPanel(appCardLayout);

        client = new Client(Globals.getProperty(Globals.IP_ADDRESS), Integer.parseInt(Globals.getProperty(Globals.PORT)), _clientListener);

        connectionDisplay = new ConnectionDisplay();
        connectionDisplay.setPreferredSize(new Dimension(0, 10));

        appPanelContainer.add(new MainMenu(), MainMenu.class.getSimpleName());

        appCardLayout.show(appPanelContainer, MainMenu.class.getSimpleName());

        add(connectionDisplay, BorderLayout.PAGE_START);
        add(appPanelContainer, BorderLayout.CENTER);

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
