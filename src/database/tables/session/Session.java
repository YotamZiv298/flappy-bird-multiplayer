package database.tables.session;

import utils.Pair;

import java.util.ArrayList;

public class Session {

    private int _id;
    private String _code;
    private ArrayList<String> _players;
    private ArrayList<Pair<String, String>> _leaderboard;

    public int getId() {
        return _id;
    }

    public void setId(int id) {
        _id = id;
    }

    public String getCode() {
        return _code;
    }

    public void setCode(String code) {
        _code = code;
    }

    public ArrayList<String> getPlayers() {
        return _players;
    }

    public void setPlayers(ArrayList<String> players) {
        _players = players;
    }

    public void addPlayer(String player) {
        _players.add(player);
    }

    public ArrayList<Pair<String, String>> getLeaderboard() {
        return _leaderboard;
    }

    public void setLeaderboard(ArrayList<Pair<String, String>> leaderboard) {
        _leaderboard = leaderboard;
    }

}
