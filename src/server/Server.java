package server;

import client.ClientInstance;
import server.framework.Message;
import server.framework.ServerInterface;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class Server {

    private int _port;
    private boolean _open;
    private ServerSocket _serverSocket;
    private ServerInterface _serverListener;
    private ArrayList<Socket> _clients;

    public Server(int port, ServerInterface listener) {
        _open = true;
        _clients = new ArrayList<>();

        _serverListener = listener;

        try {
            _serverSocket = new ServerSocket(port);

            if (_port == 0) {
                _port = _serverSocket.getLocalPort();
            } else {
                _port = port;
            }

            Thread serverThread = new Thread(() -> {
                while (_open) {
                    try {
                        final Socket s = _serverSocket.accept();

                        Thread clientThread = new Thread(() -> {
                            try {
                                _clients.add(s);

                                ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
                                ObjectInputStream in = new ObjectInputStream(s.getInputStream());

                                ClientInstance client = new ClientInstance(s.getInetAddress(), s.getPort());

                                _serverListener.clientConnected(client, out);

                                while (_open) {
                                    try {
                                        Message<?> receivedMessage = _serverListener.received(client, (Message<?>) in.readObject());

                                        Message<?> response = _serverListener.respond(client, receivedMessage);

                                        out.writeObject(response);
                                        out.flush();
                                    } catch (IOException e) {
                                        _serverListener.clientDisconnected(client);

                                        try {
                                            if (!s.isClosed()) {
                                                s.shutdownOutput();
                                                s.close();
                                            }
                                        } catch (IOException ex) {
                                            ex.printStackTrace();
                                        }

                                        _clients.remove(s);

                                        return;
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            try {
                                s.close();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            _clients.remove(s);
                        });
                        clientThread.setDaemon(true);
                        clientThread.setName("Client " + s.getInetAddress().toString());
                        clientThread.start();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            serverThread.setDaemon(true);
            serverThread.setName("Server");
            serverThread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void dispose() {
        _open = false;

        try {
            _serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (Socket socket : _clients) {
            try {
                socket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        _clients.clear();
        _clients = null;
        _serverSocket = null;
        _serverListener.serverClosed();
        _serverListener = null;
    }

    public String getIp() {
        try {
            _serverSocket.getInetAddress();

            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void kickClient(ClientInstance client) {
        Socket s;

        for (Socket socket : _clients) {
            s = socket;

            if (client._ip == s.getInetAddress() && s.getPort() == client._port) {
                try {
                    s.shutdownOutput();
                    s.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return;
            }
        }
    }

}
