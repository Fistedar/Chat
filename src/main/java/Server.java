import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {

    protected static List<SomethingServer> servers = new ArrayList<>();

    protected static Story story;

    public static void main(String[] args) throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(8080)) {
            story = new Story();
            System.out.println("server started");
            while (true) {
                Socket socket = serverSocket.accept();
                try {
                    servers.add(new SomethingServer(socket));
                } catch (IOException e) {
                    socket.close();
                }
            }
        }
    }
}
