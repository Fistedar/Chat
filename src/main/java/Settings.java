import java.io.*;
import java.util.Properties;

public class Settings {
    private static final String DEFAULTHOST = "host=localhost";
    private static final String DEFAULTPORT = "port=8080";
    static File file = new File("settings.txt");

    public static String instSettings(String key) {
        if (file.length() == 0) {
            try (FileWriter fileWriter = new FileWriter(file)) {
                fileWriter.write(DEFAULTPORT + "\n");
                fileWriter.write(DEFAULTHOST + "\n");
                fileWriter.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Properties properties = new Properties();

        try {
            properties.load(new FileReader(file));
            String port = properties.getProperty("port");
            String host = properties.getProperty("host");
            if (key.equals("port")) {
                return port;
            } else if (key.equals("host")) {
                return host;
            } else {
                throw new IOException("Ошибка файла настройки");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
