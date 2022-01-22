package state.settings.tabs;

import main.resources.Globals;
import main.resources.OSDetector;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class GeneralTab extends JPanel {

    protected JSlider _opacitySlider;

    protected final String _IMAGE_PATH =
            OSDetector.isWindows() ? "src\\images\\bird.png" : "src/images/bird.png";
    protected Image _image;
    protected float _imageOpacity;

    public GeneralTab() {
        try {
            _image = ImageIO.read(new File(_IMAGE_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }

        _opacitySlider = new JSlider(JSlider.HORIZONTAL, 0, 2, 1);

        Hashtable<Integer, JLabel> labels = new Hashtable<>();
        labels.put(0, new JLabel("Low"));
        labels.put(1, new JLabel("Medium"));
        labels.put(2, new JLabel("High"));

        float opacity = Float.parseFloat(Globals.getProperty(Globals.PLAYER_OPACITY));
        if (opacity == 0.25f) {
            _opacitySlider.setValue(0);
        } else if (opacity == 0.5f) {
            _opacitySlider.setValue(1);
        } else if (opacity == 0.75f) {
            _opacitySlider.setValue(2);
        }

        _opacitySlider.setLabelTable(labels);
        _opacitySlider.setMajorTickSpacing(1);
        _opacitySlider.setPaintTicks(true);
        _opacitySlider.setPaintLabels(true);
        _opacitySlider.setFocusable(false);

        _opacitySlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider source = (JSlider) e.getSource();

                switch (source.getValue()) {
                    case 0:
                        _imageOpacity = 0.25f;
                        break;
                    case 1:
                        _imageOpacity = 0.5f;
                        break;
                    case 2:
                        _imageOpacity = 0.75f;
                        break;
                }

                Globals.setProperty(Globals.PLAYER_OPACITY, String.valueOf(_imageOpacity));

                repaint();
            }
        });

        add(_opacitySlider);
        setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Image"));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g.create();
        float alpha = Float.parseFloat(Globals.getProperty(Globals.PLAYER_OPACITY)) != 0f ? Float.parseFloat(Globals.getProperty(Globals.PLAYER_OPACITY)) : 0.75f;

        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));

        int imageWidth = _image.getWidth(this);
        int imageHeight = _image.getHeight(this);
        int imageX = getWidth() / 2 - imageWidth / 2;
        int imageY = _opacitySlider.getHeight() + (int) (1.5 * imageHeight);

        g2d.drawImage(_image, imageX, imageY, imageWidth, imageHeight, this);

        g2d.dispose();
    }

}
