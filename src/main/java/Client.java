public class Client {
    private static final String IPAD = "localhost";
    private static final int PORT = 8080;

    public static void main(String[] args) {
        new SomethingClient(IPAD, PORT);
    }
}
