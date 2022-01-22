package server.framework;

import client.ClientInstance;

import java.io.ObjectOutputStream;

public interface ServerInterface {
    void clientConnected(ClientInstance client, ObjectOutputStream out);

    void clientDisconnected(ClientInstance client);

    Message<?> received(ClientInstance client, Message<?> data);

    Object respond(Message<?> message);

    void serverClosed();

}
