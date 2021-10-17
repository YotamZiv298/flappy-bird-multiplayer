package framework;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Mouse implements MouseListener {

    private static Mouse _instance;

    private final boolean[] _buttons;

    public Mouse() {
        _buttons = new boolean[3];
    }

    public static Mouse getInstance() {
        if (_instance == null) {
            _instance = new Mouse();
        }

        return _instance;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        _buttons[e.getButton() - 1] = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        _buttons[e.getButton() - 1] = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    public boolean isPressed(int button) {
        if (button >= 0 && button < _buttons.length) {
            return _buttons[button];
        }

        return false;
    }

}
