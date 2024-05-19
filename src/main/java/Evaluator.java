import java.util.List;

public class Evaluator {
    public double evaluate(BayesianClassifier classifier, List<Email> testSet, Preprocessor preprocessor, FeatureExtractor featureExtractor) {
        int correct = 0;
        for (Email email : testSet) {
            boolean isSpam = classifier.isSpam(email, preprocessor, featureExtractor);
            if (isSpam == email.isSpam()) {
                correct++;
            }
        }
        return (double) correct / testSet.size();
    }
}
