package state.game;

import framework.Keyboard;
import framework.Img;
import framework.Render;

import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;

public class Bird extends Thread {

    protected int _x;
    protected int _y;

    protected int _width;
    protected int _height;

    protected boolean _alive;

    protected double _velY;
    protected double _gravity;

    protected int _jumpDelay;
    protected double _rotation;

    protected final String _BIRD_IMAGE_PATH = "src\\images\\bird.png";
    protected Image _image;

    protected Keyboard _keyboard;

    public Bird() {
        _x = 100;
        _y = 150;

        _width = 45;
        _height = 32;

        _alive = true;

        _velY = 0;
        _gravity = 0.5;

        _jumpDelay = 0;
        _rotation = 0.0;

        _keyboard = Keyboard.getInstance();
    }

    public int getX() {
        return _x;
    }

    public void setX(int x) {
        _x = x;
    }

    public int getY() {
        return _y;
    }

    public void setY(int y) {
        _y = y;
    }

    public int getWidth() {
        return _width;
    }

    public void setWidth(int width) {
        _width = width;
    }

    public int getHeight() {
        return _height;
    }

    public void setHeight(int height) {
        _height = height;
    }

    public boolean getAlive() {
        return _alive;
    }

    public void setAlive(boolean alive) {
        _alive = alive;
    }

    public Render getRender() {
        Render r = new Render();

        r.setX(r.getX());
        r.setY(r.getY());

        if (_image == null) {
            _image = Img.loadImage(_BIRD_IMAGE_PATH);
        }
        r.setImage(_image);

        _rotation = (90 * (_velY + 20) / 20) - 90;
        _rotation = _rotation * Math.PI / 180;

        if (_rotation > Math.PI / 2)
            _rotation = Math.PI / 2;

        r.setTransform(new AffineTransform());
        r.getTransform().translate(_x + _width / 2.0, _y + _height / 2.0);
        r.getTransform().rotate(_rotation);
        r.getTransform().translate(-_width / 2.0, -_height / 2.0);

        return r;
    }

    @Override
    public void run() {
        super.run();

        while (_alive) {
            System.out.format("|%d|%d| %b", _x, _y, _alive);
            System.out.println();

            _velY += _gravity;

            if (_jumpDelay > 0)
                _jumpDelay--;

            if (_alive && _keyboard.isDown(KeyEvent.VK_SPACE) && _jumpDelay <= 0) {
                _velY = -10;
                _jumpDelay = 10;
            }

            _y += (int) _velY;

            try {
                sleep(25);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
