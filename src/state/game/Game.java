package state.game;

import framework.Keyboard;
import framework.Render;
import main.Main;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Game {

    private final String _BACKGROUND_PATH = "src\\images\\background.png";
    private final String _FOREGROUND_PATH = "src\\images\\foreground.png";

    private Bird _bird;
    private ArrayList<Bird> _birds;

    private Keyboard _keyboard;

    private int _score;
    private Boolean _gameOver;
    private Boolean _started;

    public Game() {
        _keyboard = Keyboard.getInstance();

        restart();
    }

    public Bird getBird() {
        return _bird;
    }

    public void setBird(Bird bird) {
        _bird = bird;
    }

    public ArrayList<Bird> getBirds() {
        return _birds;
    }

    public void setBirds(ArrayList<Bird> birds) {
        _birds = birds;
    }

    public Keyboard getKeyboard() {
        return _keyboard;
    }

    public void setKeyboard(Keyboard keyboard) {
        _keyboard = keyboard;
    }

    public int getScore() {
        return _score;
    }

    public void setScore(int score) {
        _score = score;
    }

    public Boolean isGameOver() {
        return _gameOver;
    }

    public void setGameOver(Boolean gameOver) {
        _gameOver = gameOver;
    }

    public Boolean hasStarted() {
        return _started;
    }

    public void setStarted(Boolean started) {
        _started = started;
    }

    public void restart() {
        _started = false;
        _gameOver = false;

        _score = 0;

        _bird = new Bird();
    }

    public void update() {
        startEvent();

        if (!_started)
            return;

        resetEvent();

        if (_gameOver)
            return;

        checkCollisions();
    }

    public void checkCollisions() {
        if (_bird.getY() + _bird.getHeight() > Main.FRAME_HEIGHT - 80 || _bird.getY() <= 0) {
            _gameOver = true;
            _bird.setAlive(false);
            _bird.setY(_bird.getY());
        }
    }

    public ArrayList<Render> getRenders() {
        ArrayList<Render> renders = new ArrayList<>();

        renders.add(new Render(0, 0, _BACKGROUND_PATH));
        renders.add(new Render(0, 0, _FOREGROUND_PATH));
        renders.add(_bird.getRender());

        return renders;
    }

    public void startEvent() {
        if (!_started && _keyboard.isDown(KeyEvent.VK_SPACE)) {
            _started = true;
            _bird.start();
        }
    }

    public void resetEvent() {
        if (_keyboard.isDown(KeyEvent.VK_R)) {
            restart();
        }
    }

//    @Override
//    protected void paintComponent(Graphics g) {
//        super.paintComponent(g);
//
//        Graphics2D g2D = (Graphics2D) g;
//
//        Render r = _bird.getRender();
//
//        if (r.getTransform() != null)
//            g2D.drawImage(r.getImage(), r.getTransform(), null);
//        else
//            g.drawImage(r.getImage(), r.getX(), r.getY(), null);
//
//
//        repaint();
//    }

}
