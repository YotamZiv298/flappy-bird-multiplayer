package database.tables.leaderboard;

import java.io.Serializable;

public class PlayerData implements Serializable {

    private int _id;
    private String _name;
    private int _score;

    public int getId() {
        return _id;
    }

    public void setId(int id) {
        _id = id;
    }

    public String getName() {
        return _name;
    }

    public void setName(String name) {
        _name = name;
    }

    public int getScore() {
        return _score;
    }

    public void setScore(int score) {
        _score = score;
    }

}
