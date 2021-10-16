package state.game;

import framework.Keyboard;
import framework.Render;

import java.util.ArrayList;

public class Game {

    private final String _BACKGROUND_PATH = "src\\images\\background.png";
    private final String _FOREGROUND_PATH = "src\\images\\foreground.png";

    private Bird _bird;
    private ArrayList<Bird> _birds;

    private Keyboard _keyboard;

    public int _score;
    public Boolean _gameOver;
    public Boolean _started;

    public Game() {
        _keyboard = Keyboard.getInstance();

        initiate();
    }

    private void initiate() {
        _started = false;
        _gameOver = false;

        _score = 0;

        _bird = new Bird();
        _bird.start();
    }

    public ArrayList<Render> getRenders() {
        ArrayList<Render> renders = new ArrayList<>();

        renders.add(new Render(0, 0, _BACKGROUND_PATH));
        renders.add(new Render(0, 0, _FOREGROUND_PATH));
        renders.add(_bird.getRender());

        return renders;
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
//        if (_bird.getY() + _bird.getHeight() > Main.FRAME_HEIGHT - 80 || _bird.getY() <= 0) {
//            _bird.setAlive(false);
////            _bird.setY(Main.FRAME_HEIGHT - 80 - _bird.getHeight());
//        }
//
//        repaint();
//    }

}
