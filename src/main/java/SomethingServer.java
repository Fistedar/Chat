import java.io.*;
import java.net.Socket;

public class SomethingServer extends Thread {
    public Socket socket;
    private final BufferedReader in;
    private final BufferedWriter out;

    public SomethingServer(Socket socket) throws IOException {
        this.socket = socket;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        Server.story.printStory(out);
        start();
    }

    public void run() {
        String word;
        try {
            word = in.readLine();
            try {
                out.write(word + "\n");
                out.flush();
            } catch (IOException e) {
                System.err.println("Ошибка при вводе имени");
            }
            try {

                while (true) {
                    word = in.readLine();
                    if (word.equals("/exit")) {
                        this.downService();
                        break;
                    }
                    System.out.println("Echoing: " + word);
                    Server.story.addStory(word);
                    for (SomethingServer vr : Server.servers) {
                        vr.send(word);
                    }
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }


        } catch (IOException e) {
            this.downService();
        }
    }

    private void downService() {
        try {
            if (!socket.isClosed()) {
                socket.close();
                in.close();
                out.close();
                for (SomethingServer vr : Server.servers) {
                    if (vr.equals(this)) vr.interrupt();
                    Server.servers.remove(this);
                }
            }
        } catch (IOException ignored) {
        }
    }

    public void send(String word) {
        try {
            out.write(word + "\n");
            out.flush();
        } catch (IOException ignored) {
        }
    }


}
