package state.game;

import framework.Img;
import framework.Render;
import main.Main;
import main.resources.OSDetector;

import java.awt.Image;

public class Pipe extends Thread {

    private int _x;
    private int _y;

    private int _width;
    private int _height;

    private boolean _alive;

    private int _speed;

    private String _orientation;

    private final String _PIPE_IMAGE_PATH =
            OSDetector.isWindows() ? "src\\images\\pipe-%s.png" : "src/images/pipe-%s.png";
    private Image _image;

    private final int _REFRESH_RATE = 25;

    public Pipe(String orientation) {
        _x = Main.FRAME_WIDTH + 2;

        _width = 66;
        _height = 400;

        _alive = true;

        _speed = 3;

        _orientation = orientation;

        if (orientation.equals("south")) {
            _y = -(int) (Math.random() * 120) - _height / 2;
        }
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

    public int getSpeed() {
        return _speed;
    }

    public void setSpeed(int speed) {
        _speed = speed;
    }

    public String getOrientation() {
        return _orientation;
    }

    public void setOrientation(String orientation) {
        _orientation = orientation;
    }

    public String getPipeImagePath() {
        return _PIPE_IMAGE_PATH;
    }

    public Image getImage() {
        return _image;
    }

    public void setImage(Image image) {
        _image = image;
    }

    public boolean collides(int x, int y, int width, int height) {
        int margin = 2;

        if (x + width - margin > _x && x + margin < _x + _width) {
            if (_orientation.equals("south") && y < _y + _height) {
                return true;
            } else {
                return _orientation.equals("north") && y + height > _y;
            }
        }

        return false;
    }

    public Render getRender() {
        Render r = new Render();

        r.setX(_x);
        r.setY(_y);

        if (_image == null) {
            _image = Img.loadImage(String.format(_PIPE_IMAGE_PATH, _orientation));
        }

        r.setImage(_image);

        return r;
    }

    @Override
    public void run() {
        super.run();

        while (_alive) {
            _x -= _speed;

            try {
                sleep(_REFRESH_RATE);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
