package main;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Img {

    protected String _imagePath;
    protected BufferedImage _image;

    protected int _x;
    protected int _y;

    protected int _width;
    protected int _height;

    protected ImageObserver _observer;

    public Img(String imagePath, int x, int y, int width, int height, ImageObserver observer) {
        _imagePath = imagePath;

        try {
            _image = ImageIO.read(new File(_imagePath));
        } catch (IOException e) {
            e.printStackTrace();
        }

        _x = x;
        _y = y;

        _width = width;
        _height = height;

        _observer = observer;
    }

    public void draw(Graphics g) {
        g.drawImage(_image, _x, _y, _width, _height, _observer);
    }

}
