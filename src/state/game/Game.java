package state.game;

import framework.Keyboard;
import framework.Render;
import main.Main;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Game {

    public static final int PIPE_DELAY = 100;

    private final String _BACKGROUND_PATH = "src\\images\\background.png";
    private final String _FOREGROUND_PATH = "src\\images\\foreground.png";

    private int _pipeDelay;

    private Bird _bird;
    private ArrayList<Bird> _birds;

    private ArrayList<Pipe> _pipes;

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

    public synchronized void restart() {
        _started = false;
        _gameOver = false;

        _score = 0;
        _pipeDelay = 0;

        if (_bird != null) {
            _bird.setAlive(false);

            try {
                _bird.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        _bird = new Bird();

        if (_pipes != null) {
            for (Pipe pipe : _pipes) {
                pipe.setAlive(false);

                try {
                    pipe.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        _pipes = new ArrayList<>();
    }

    public void update() {
        startEvent();

        if (!_started)
            return;

        resetEvent();

        if ((_bird.getState() == Thread.State.NEW) && !_bird.isAlive()) {
            _bird.start();
        }

        if (_gameOver) {
            for (Pipe pipe : _pipes) {
                pipe.setAlive(false);
            }
            return;
        }

        movePipes();
        checkCollisions();
    }

    public void movePipes() {
        _pipeDelay--;

        if (_pipeDelay < 0) {
            _pipeDelay = PIPE_DELAY;

            Pipe northPipe = null;
            Pipe southPipe = null;

            // Look for pipes off the screen
            for (Pipe pipe : _pipes) {
                if (pipe.getX() - pipe.getWidth() < 0) {
                    if (northPipe == null) {
                        northPipe = pipe;
                    } else {
                        southPipe = pipe;
                        break;
                    }
                }
            }

            if (northPipe != null) {
                northPipe.setAlive(false);

                try {
                    northPipe.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            northPipe = new Pipe("north");
            _pipes.add(northPipe);

            if (southPipe != null) {
                southPipe.setAlive(false);

                try {
                    southPipe.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            southPipe = new Pipe("south");
            _pipes.add(southPipe);

            northPipe.setY(southPipe.getY() + southPipe.getHeight() + 175);
        }

        for (Pipe pipe : _pipes) {
            if (pipe.getState() == Thread.State.TERMINATED)
                continue;
            if (!pipe.isAlive()) {
                pipe.start();
            }
        }
    }

    public void checkCollisions() {
        if (_bird.getY() + _bird.getHeight() > Main.FRAME_HEIGHT - 80 || _bird.getY() <= 0) {
            _gameOver = true;
            _bird.setY(_bird.getY());
            _bird.setAlive(false);
        }

        for (Pipe pipe : _pipes) {
            if (pipe.collides(_bird.getX(), _bird.getY(), _bird.getWidth(), _bird.getHeight())) {
                _gameOver = true;
                _bird.setAlive(false);

                return;
            } else if (pipe.getX() == _bird.getX() && pipe.getOrientation().equalsIgnoreCase("south")) {
                _score++;
            }
        }
    }

    public ArrayList<Render> getRenders() {
        ArrayList<Render> renders = new ArrayList<>();

        renders.add(new Render(0, 0, _BACKGROUND_PATH));
        for (Pipe pipe : _pipes)
            renders.add(pipe.getRender());
        renders.add(new Render(0, 0, _FOREGROUND_PATH));
        renders.add(_bird.getRender());

        return renders;
    }

    public void startEvent() {
        if (!_started && _keyboard.isDown(KeyEvent.VK_SPACE)) {
            _started = true;
        }
    }

    public void resetEvent() {
        if (_keyboard.isDown(KeyEvent.VK_R)) {
            restart();
        }
    }

}
