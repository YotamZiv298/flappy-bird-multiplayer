package client;

import client.framework.ClientInterface;
import server.framework.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

    private Socket _socket;
    private ObjectOutputStream _out;
    private ObjectInputStream _in;
    private Message<?> _received;

    private ClientInterface _clientListener;
    private boolean _open;

    public Client(String ip, int port, ClientInterface listener) {
        _open = true;

        _clientListener = listener;

        try {
            _socket = new Socket(ip, port);
            _out = new ObjectOutputStream(_socket.getOutputStream());
            _in = new ObjectInputStream(_socket.getInputStream());

            Thread clientThread = new Thread(() -> {
                while (_open) {
                    try {
//                        Object readLine = _in.readObject();
                        Message<?> receivedMessage = (Message<?>) _in.readObject();

                        if (receivedMessage == null) {
                            System.out.println("Client: received null message");
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

                        _received = _clientListener.receivedInput(receivedMessage);
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

    public synchronized void send(Message<?> message) {
        if (_open) {
            try {
                _out.writeObject(message);
//                _out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized Message<?> getData(Message.RequestCode expectedCode) {
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        long startTime = System.currentTimeMillis();

        Message<?> data = _received;

        while (data == null || data.getRequestCode() != expectedCode) {
            if (System.currentTimeMillis() > startTime + 5000) {
                return null;
            }

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            data = _received;
        }

        _received = null;

        return data;
    }

    public boolean isConnected() {
        return _open;
    }

}
