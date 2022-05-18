package server;

import server.framework.ServerListener;

public class FlappyBirdMultiplayerServer extends Thread {

    private final Server _server;
    private volatile boolean _alive;
    private static final int _port = 6066;
    private static final ServerListener _serverListener = new ServerListener();

    public FlappyBirdMultiplayerServer() {
        _server = new Server(_port, _serverListener);
        System.out.println(_server.getIp());
        _alive = true;

        start();
    }

    public void turnOff() {
        _server.dispose();
        _alive = false;
    }

    @Override
    public void run() {
        super.run();

        while (_alive) {
            if (Thread.interrupted()) {
                turnOff();
            }
        }
    }

}
