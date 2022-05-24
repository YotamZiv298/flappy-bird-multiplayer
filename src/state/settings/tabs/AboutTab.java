package state.settings.tabs;

import main.FlappyBirdMultiplayer;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

public class AboutTab extends JPanel {

    protected Image _gameImage;

    protected JLabel _copyrightLabel;

    public AboutTab() {
        setLayout(new BorderLayout());

        try {
            _gameImage = ImageIO.read(new File(FlappyBirdMultiplayer.GAME_LOGO));
        } catch (IOException e) {
            e.printStackTrace();
        }

        _copyrightLabel = new JLabel();
        String text = "" +
                "<p style=\"text-align: center;\">" +
                "Copyright © 2021-2022 Yotam Ziv" +
                "</p>";
        String rulesString = String.format("" +
                "<html>" +
                "<div style=\"width:%d;\">" +
                "%s" +
                "</div>" +
                "</html>", 250, text);
        _copyrightLabel.setText(rulesString);
        _copyrightLabel.setBorder(BorderFactory.createEtchedBorder());

        add(_copyrightLabel, BorderLayout.SOUTH);

        setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "About"));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int gameImageWidth = _gameImage.getWidth(this);
        int gameImageHeight = _gameImage.getHeight(this);
        int gameImageX = getWidth() / 2 - gameImageWidth / 2;
        int gameImageY = gameImageHeight / 2;

        g.drawImage(_gameImage, gameImageX, gameImageY, gameImageWidth, gameImageHeight, this);
    }

}
