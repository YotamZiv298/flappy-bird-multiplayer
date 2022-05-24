package server.framework;

import java.io.Serializable;

public class Message<T> implements Serializable {

    public enum RequestCode {
        NEW_GAME,
        JOIN_GAME,
        LEAVE_GAME,
        DELETE_GAME,
        START_GAME,
        RECEIVE_PLAYERS_IN_SESSION,
        IS_SESSION_ACTIVE,
        RECEIVE_LEADERBOARD,
        SET_PLAYER_SCORE
    }

    private RequestCode _requestCode;
    private T _data;

    public Message(RequestCode requestCode, T data) {
        _requestCode = requestCode;
        _data = data;
    }

    public RequestCode getRequestCode() {
        return _requestCode;
    }

    public void setRequestCode(RequestCode requestCode) {
        _requestCode = requestCode;
    }

    public T getData() {
        return _data;
    }

    public void setData(T data) {
        _data = data;
    }

}
