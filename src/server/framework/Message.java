package server.framework;

import java.io.Serializable;

public class Message<T> implements Serializable {

    private int _requestCode;
    private T _data;

    public Message(int requestCode, T data) {
        _requestCode = requestCode;
        _data = data;
    }

    public int getRequestCode() {
        return _requestCode;
    }

    public void setRequestCode(int code) {
        _requestCode = code;
    }

    public T getData() {
        return _data;
    }

    public void setData(T data) {
        _data = data;
    }

}
