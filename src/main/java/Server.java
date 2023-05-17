import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class Server {

    protected static List<SomethingServer> servers = new LinkedList<>();
    private static final int PORT = Integer.parseInt(Objects.requireNonNull(Settings.instSettings("port")));
    protected static Story story;

    public static void main(String[] args) throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
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
