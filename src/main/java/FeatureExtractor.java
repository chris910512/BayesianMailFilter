import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FeatureExtractor {
    public Map<String, Integer> extractFeatures(List<String> words) {
        Map<String, Integer> features = new HashMap<>();
        for (String word : words) {
            features.put(word, features.getOrDefault(word, 0) + 1);
        }
        return features;
    }
}
