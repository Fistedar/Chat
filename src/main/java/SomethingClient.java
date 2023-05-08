import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
public class SomethingClient {
    Socket socket;
    int port;
    String nickname;
    String adr;
    Date time;
    BufferedWriter out;
    BufferedReader in;
    private BufferedReader inputUser;

    public SomethingClient(String adr, int port) {
    this.adr = adr;
    this.port = port;
    try {
        this.socket = new Socket(adr, port);
    }catch (IOException e){
        System.err.println("Socket failed");
    }
    try {
        inputUser = new BufferedReader(new InputStreamReader(System.in));
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        this.pressNickname();
        new ReadMsg().start();
        new WriteMsg().start();

    } catch (IOException e) {
        downService();
    }
    }

    private void pressNickname() {
        System.out.print("Введите имя");
        try {
            nickname = inputUser.readLine();
            out.write("Здраствуй" + nickname + "\n");
            out.flush();
        } catch (IOException ignored) {
        }
    }

    private void downService() {
        try {
            if (!socket.isClosed()) {
                socket.close();
                in.close();
                out.close();
            }
        } catch (IOException ignored) {
        }
    }

    private class ReadMsg extends Thread {
        @Override
        public void run() {
            String str;
            try {
                while (true) {
                    str = in.readLine();
                    if (str.equals("/exit")) {
                        downService();
                        break;
                    } else {
                        System.out.println(str);
                    }
                }
            } catch (IOException e) {
                downService();
            }
        }
    }

    private class WriteMsg extends Thread {
        @Override
        public void run() {
            String str;
            try {
                while (true){
                    str = inputUser.readLine();
                    if (str.equals("/exit")){
                        downService();
                        break;
                    }else {
                     time = new Date();
                        SimpleDateFormat dt1 = new SimpleDateFormat("HH:mm:ss");
                        String dtime = dt1.format(time);
                     out.write(dtime + " " + nickname + " " + str + "\n");
                     out.flush();
                    }
                }
            } catch (IOException e) {
                downService();
            }
        }
    }

}
