import java.util.Objects;

public class Client {
    private static final String HOST = Settings.instSettings("host");
    private static final int PORT = Integer.parseInt(Objects.requireNonNull(Settings.instSettings("port")));

    public static void main(String[] args) {
        new SomethingClient(HOST, PORT);
    }
}
