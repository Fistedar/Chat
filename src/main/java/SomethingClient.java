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
        } catch (IOException e) {
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
        System.out.print("Введите имя:  ");
        try {
            nickname = inputUser.readLine();
            out.write("Здраствуй " + nickname + "\n");
            out.flush();
        } catch (IOException e) {
            System.err.println("pressNickname failed");
        }
    }

    private void downService() {
        try {
            if (!socket.isClosed()) {
                socket.close();
                in.close();
                out.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class ReadMsg extends Thread {
        @Override
        public void run() {
            String str;
            try {
                while (true) {
                    str = in.readLine(); // ждем сообщения с сервера
                    if (str.equals("stop")) {
                        SomethingClient.this.downService(); // харакири
                        break; // выходим из цикла если пришло "stop"
                    }

                    System.out.println(str); // пишем сообщение с сервера на консоль
                }
            } catch (IOException e) {
                SomethingClient.this.downService();
            }
        }
    }

    private class WriteMsg extends Thread {

        @Override
        public void run() {
            File file = new File("log.txt");
            while (true) {
                String userWord;
                try {
                    BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
                    time = new Date(); // текущая дата
                    SimpleDateFormat dt1 = new SimpleDateFormat("HH:mm:ss"); // берем только время до секунд
                    String dtime = dt1.format(time); // время
                    userWord = inputUser.readLine(); // сообщения с консоли
                    if (userWord.equals("stop")) {
                        out.write("stop" + "\n");
                        SomethingClient.this.downService(); // харакири
                        break; // выходим из цикла если пришло "stop"
                    } else {
                        writer.write("(" + dtime + ") " + nickname + ": " + userWord + "\n");
                        out.write("(" + dtime + ") " + nickname + ": " + userWord + "\n"); // отправляем на сервер
                    }
                    writer.flush();
                    out.flush(); // чистим
                } catch (IOException e) {
                    SomethingClient.this.downService(); // в случае исключения тоже харакири

                }

            }
        }
    }
}
