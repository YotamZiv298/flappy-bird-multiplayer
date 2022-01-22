package client.framework;

public class ClientListener implements ClientInterface {

    @Override
    public void unknownHost() {
        System.out.println("Unknown host");
    }

    @Override
    public void couldNotConnect() {
        System.out.println("Couldn't connect");
    }

    @Override
    public Object receivedInput(Object data) {
        return data;
    }

    @Override
    public void serverClosed() {
        System.out.println("Server closed");
    }

    @Override
    public void disconnected() {
        System.out.println("Disconnected");
    }

    @Override
    public void connectedToServer() {
        System.out.println("Connected to server");
    }

}
