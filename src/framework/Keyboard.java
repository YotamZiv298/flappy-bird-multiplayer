package framework;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener {

    private static Keyboard _instance;

    private final boolean[] _keys;

    private Keyboard() {
        _keys = new boolean[256];
    }

    public static Keyboard getInstance() {
        if (_instance == null) {
            _instance = new Keyboard();
        }

        return _instance;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() >= 0 && e.getKeyCode() < _keys.length) {
            _keys[e.getKeyCode()] = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() >= 0 && e.getKeyCode() < _keys.length) {
            _keys[e.getKeyCode()] = false;
        }
    }

    public boolean isDown(int key) {
        if (key >= 0 && key < _keys.length) {
            return _keys[key];
        }

        return false;
    }

}
