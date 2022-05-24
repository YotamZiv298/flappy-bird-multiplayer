package client;

import java.net.InetAddress;

public class ClientInstance {
    public final InetAddress _ip;
    public final int _port;

    public ClientInstance(InetAddress ip, int port) {
        _ip = ip;
        _port = port;
    }

    @Override
    public String toString() {
        return _ip.toString() + ":" + _port;
    }

}

