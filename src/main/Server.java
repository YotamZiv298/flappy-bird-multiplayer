package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class Server {

    public static final int PORT = 4000;

    public static void main(String[] args) throws IOException {

        try (ServerSocket listener = new ServerSocket(PORT)) {
            String line;
            while (true) {
                Socket socket = listener.accept();
                BufferedReader readerChannel = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                BufferedWriter writerChannel = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

                try {
                    writerChannel.write(new Date() + "\n\r");
                    writerChannel.flush();

                    while ((line = readerChannel.readLine()) != null) {
                        System.out.println(line);
                    }
                } finally {
                    socket.close();
                }
            }
        }
    }

}
