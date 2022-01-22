package client;

import client.framework.ClientInterface;
import server.framework.Message;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

    private Socket _socket;
    private ObjectOutputStream _out;
    private ObjectInputStream _in;
    private Object _received;
//    private BufferedReader _in;
//    private PrintWriter _out;

    private ClientInterface _clientListener;
    private boolean _open;

    public Client(String ip, int port, ClientInterface listener) {
        _open = true;

        _clientListener = listener;

        try {
            _socket = new Socket(ip, port);
            _out = new ObjectOutputStream(_socket.getOutputStream());
            _in = new ObjectInputStream(_socket.getInputStream());
//            _in = new BufferedReader(new InputStreamReader(_socket.getInputStream()));
//            _out = new PrintWriter(_socket.getOutputStream(), true);

            Thread clientThread = new Thread(() -> {
                while (_open) {
                    try {
//                        String readLine = _in.readLine();
                        Object readLine = _in.readObject();

                        if (readLine == null) {
                            _open = false;
                            _clientListener.disconnected();

                            try {
                                if (_socket != null) {
                                    _socket.close();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            try {
                                if (_in != null) {
                                    _in.close();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            try {
                                if (_out != null) {
                                    _out.close();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            return;
                        }

                        _received = _clientListener.receivedInput(readLine);
                    } catch (IOException e) {
                        _open = false;
                        _clientListener.serverClosed();

                        try {
                            _socket.close();
                        } catch (Exception ex) {
                            e.printStackTrace();
                        }

                        try {
                            _in.close();
                        } catch (Exception ex) {
                            e.printStackTrace();
                        }

                        try {
                            _out.close();
                        } catch (Exception ex) {
                            e.printStackTrace();
                        }

                        return;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            clientThread.setName("Client Connection");
            clientThread.setDaemon(true);
            clientThread.start();

            listener.connectedToServer();
        } catch (UnknownHostException e) {
            _open = false;
            listener.unknownHost();
        } catch (IOException e) {
            _open = false;
            listener.couldNotConnect();
        } catch (Exception e) {
            _open = false;
            e.printStackTrace();
        }
    }

    public void dispose() {
        try {
            if (_open) {
                _open = false;
                _socket.close();
                _in.close();
                _out.close();
                _clientListener.disconnected();
            }

            _socket = null;
            _in = null;
            _out = null;
            _clientListener = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void send(Message<?> message) {
        if (_open) {
//            _out.println(message);
            try {
                _out.writeObject(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Object received() {
        return _received;
    }

    public boolean isConnected() {
        return _open;
    }

}
