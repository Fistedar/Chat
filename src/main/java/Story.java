import java.io.BufferedWriter;
import java.io.IOException;
import java.util.LinkedList;

public class Story {
    static LinkedList<String> story = new LinkedList<>();

    public void addStory(String word){
        if (story.size()>= 10){
            story.removeFirst();
            story.add(word);
        }else {
            story.add(word);
        }
    }

    public void printStory(BufferedWriter writer) throws IOException {
        if(story.size()>0){
            writer.write("История сообщений " + "\n");
            for (String word: story){
                writer.write(word + "\n");
            }
                writer.write("/...." + "\n");
                writer.flush();
        }
    }
}
