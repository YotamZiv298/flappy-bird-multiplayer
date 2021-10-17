package state.settings.tabs;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class AboutTab extends JPanel {

    protected final String _GAME_IMAGE_PATH = "src\\images\\bird.png";
    protected Image _gameImage;

    protected JLabel _copyrightLabel;

    public AboutTab() {
        setLayout(new BorderLayout());

        try {
            _gameImage = ImageIO.read(new File(_GAME_IMAGE_PATH));
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
