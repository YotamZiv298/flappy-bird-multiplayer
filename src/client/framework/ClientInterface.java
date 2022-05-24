package client.framework;

import server.framework.Message;

public interface ClientInterface {
    void unknownHost();

    void couldNotConnect();

    Message<?> receivedInput(Message<?> data);

    void serverClosed();

    void disconnected();

    void connectedToServer();

}
