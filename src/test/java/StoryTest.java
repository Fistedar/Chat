import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class StoryTest {

    Story objStory;

    @Before
    public void setUp() {
        objStory = new Story();
    }

    @Test
    public void addStoryTest() {
        String testStory = "Привет";
        objStory.addStory(testStory);

        assert Story.story.peekFirst() != null;
        boolean testResult = Story.story.peekFirst().equals(testStory);
        Assertions.assertTrue(testResult);
    }
}