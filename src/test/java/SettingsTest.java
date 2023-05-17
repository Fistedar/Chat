import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;


public class SettingsTest {
    FileWriter writer;
    static File file;

    @Before
    public void setUp() throws Exception {
        file = new File("settings.txt");
        writer = new FileWriter(file);
    }

    @Test
    public void instSettings() throws IOException {
        String testOutMessage = "host=testLocalhost";
        String testInMessage = "testLocalhost";
        writer.write(testOutMessage + "\n");
        writer.flush();
        boolean test = false;
        if (Objects.equals(Settings.instSettings("host"), testInMessage)) {
            test = true;
        }
        Assertions.assertTrue(test);
    }

    @After
    public void after() throws IOException {
        new FileWriter(file, false).close();
    }
}