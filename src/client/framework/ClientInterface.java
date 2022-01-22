package client.framework;

public interface ClientInterface {
    void unknownHost();

    void couldNotConnect();

    Object receivedInput(Object data);

    void serverClosed();

    void disconnected();

    void connectedToServer();

}
