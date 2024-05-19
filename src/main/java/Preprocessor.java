import java.util.Arrays;
import java.util.List;

public class Preprocessor {
    public List<String> preprocess(String content) {
        content = content.toLowerCase().replaceAll("[^a-z ]", "");
        return Arrays.asList(content.split("\\s+"));
    }
}
