package framework;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;

public class Render {

    protected int _x;
    protected int _y;
    protected Image _image;
    protected AffineTransform _transform;

    public Render() {
    }

    public Render(int x, int y, String imagePath) {
        Toolkit.getDefaultToolkit().sync();
        _x = x;
        _y = y;
        _image = Img.loadImage(imagePath);
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

    public Image getImage() {
        return _image;
    }

    public void setImage(Image image) {
        _image = image;
    }

    public AffineTransform getTransform() {
        return _transform;
    }

    public void setTransform(AffineTransform transform) {
        _transform = transform;
    }

}
