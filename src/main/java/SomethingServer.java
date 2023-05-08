import java.io.*;
import java.net.Socket;
import java.util.LinkedList;

public class SomethingServer extends Thread {
    public Socket socket;
    private BufferedReader in;
    private BufferedWriter out;

    public SomethingServer(Socket socket)  throws IOException {
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
            out.write(word + "\n");
            out.flush();
             while (true) {
                word = in.readLine();
                if (word.equals("/exit")) {
                    downService();
                    break;
                }
                System.out.println("Echoing: " + word);
                Server.story.addStory(word);
                for (SomethingServer sv : Server.servers) {
                    sv.send(word);
                }
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
            out.write(word + "n");
            out.flush();
        } catch (IOException ignored) {
        }
    }


}
