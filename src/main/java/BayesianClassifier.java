import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BayesianClassifier {
    private Map<String, Double> spamProbabilities = new HashMap<>();
    private Map<String, Double> hamProbabilities = new HashMap<>();

    public void train(List<Email> trainingSet, Preprocessor preprocessor, FeatureExtractor featureExtractor) {
        Map<String, Integer> spamCounts = new HashMap<>();
        Map<String, Integer> hamCounts = new HashMap<>();
        int spamTotal = 0;
        int hamTotal = 0;

        for (Email email : trainingSet) {
            Map<String, Integer> features = featureExtractor.extractFeatures(preprocessor.preprocess(email.getContent()));
            for (Map.Entry<String, Integer> entry : features.entrySet()) {
                if (email.isSpam()) {
                    spamCounts.put(entry.getKey(), spamCounts.getOrDefault(entry.getKey(), 0) + entry.getValue());
                    spamTotal += entry.getValue();
                } else {
                    hamCounts.put(entry.getKey(), hamCounts.getOrDefault(entry.getKey(), 0) + entry.getValue());
                    hamTotal += entry.getValue();
                }
            }
        }

        for (String word : spamCounts.keySet()) {
            spamProbabilities.put(word, (double) spamCounts.get(word) / spamTotal);
        }

        for (String word : hamCounts.keySet()) {
            hamProbabilities.put(word, (double) hamCounts.get(word) / hamTotal);
        }
    }

    public boolean isSpam(Email email, Preprocessor preprocessor, FeatureExtractor featureExtractor) {
        double spamScore = 0.0;
        double hamScore = 0.0;

        Map<String, Integer> features = featureExtractor.extractFeatures(preprocessor.preprocess(email.getContent()));
        for (String word : features.keySet()) {
            if (spamProbabilities.containsKey(word)) {
                spamScore += Math.log(spamProbabilities.get(word));
            }
            if (hamProbabilities.containsKey(word)) {
                hamScore += Math.log(hamProbabilities.get(word));
            }
        }

        return spamScore > hamScore;
    }
}
