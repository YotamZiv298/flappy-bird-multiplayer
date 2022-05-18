package state.leaderboard;

import database.tables.leaderboard.PlayerData;
import main.FlappyBirdMultiplayer;
import main.Main;
import server.framework.Message;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableColumnModel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.stream.IntStream;

public class LeaderboardPanel extends JDialog {

    public LeaderboardPanel() {
        setTitle("Leaderboard");
        setModal(true);
        setLayout(new BorderLayout());

        String[] columnNames = {"Place", "Name", "Score"};
        String[][] data = getLeaderboardData();

        JTable leaderboardTable = new JTable(data, columnNames);
        TableColumnModel columnModel = leaderboardTable.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(10);
        columnModel.getColumn(2).setPreferredWidth(10);
        leaderboardTable.setEnabled(false);

        JScrollPane scrollPane = new JScrollPane(leaderboardTable);

        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dispose());
        closeButton.setFocusable(false);

        add(scrollPane, BorderLayout.CENTER);
        add(closeButton, BorderLayout.SOUTH);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(Main.FRAME_WIDTH / 2 + 50, 3 * Main.FRAME_HEIGHT / 4));
        pack();
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public String[][] getLeaderboardData() {
        if (!FlappyBirdMultiplayer.client.isConnected()) {
            return new String[][]{{"-", "No high scores yet", "-"}};
        }

        FlappyBirdMultiplayer.client.send(new Message<>(Message.RequestCode.RECEIVE_LEADERBOARD, null));

        Message<?> data = FlappyBirdMultiplayer.client.getData(Message.RequestCode.RECEIVE_LEADERBOARD);

        if (data == null || ((ArrayList<PlayerData>) data.getData()).isEmpty()) {
            return new String[][]{{"-", "No high scores yet", "-"}};
        }

        ArrayList<PlayerData> leaderboard = (ArrayList<PlayerData>) data.getData();
        leaderboard.sort((o1, o2) -> o2.getScore() - o1.getScore());

        String[][] leaderboardData = new String[leaderboard.size()][3];

        IntStream.range(0, leaderboard.size()).forEach(i -> {
            String name = leaderboard.get(i).getName();
            int score = leaderboard.get(i).getScore();

            leaderboardData[i][0] = String.valueOf(i + 1);
            leaderboardData[i][1] = name;
            leaderboardData[i][2] = String.valueOf(score);
        });

        return leaderboardData;

    }

}