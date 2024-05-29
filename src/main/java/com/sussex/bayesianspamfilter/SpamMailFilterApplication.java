package com.sussex.bayesianspamfilter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpamMailFilterApplication {
    public static void main(String[] args) {

        SpringApplication.run(SpamMailFilterApplication.class, args);

//        // Create some mock data
//        List<Email> emails = Arrays.asList(
//                new Email("Dear user, you have won a lottery of $1,000,000. Please send us your bank details.", true),
//                new Email("Dear customer, your order has been shipped. Thank you for shopping with us.", false),
//                new Email("Congratulations! You have been selected for a cash prize. Click here to claim.", true),
//                new Email("Your appointment is confirmed for tomorrow at 10am. See you then.", false),
//                new Email("You have inherited a large sum of money from a distant relative. Contact us for more details.", true),
//                new Email("Your subscription to our service has been renewed. Thank you for your continued support.", false)
//        );
//
//        // Split the data into a training set and a test set
//        List<Email> trainingSet = emails.subList(0, 4);
//        List<Email> testSet = emails.subList(4, 6);
//
//        // Create the necessary objects
//        Preprocessor preprocessor = new Preprocessor();
//        FeatureExtractor featureExtractor = new FeatureExtractor();
//        BayesianClassifier classifier = new BayesianClassifier();
//
//        // Train the classifier
//        classifier.train(trainingSet, preprocessor, featureExtractor);
//
//        // Evaluate the classifier
//        Evaluator evaluator = new Evaluator();
//        double accuracy = evaluator.evaluate(classifier, testSet, preprocessor, featureExtractor);
//
//        System.out.println("Classifier accuracy: " + accuracy);
    }
}

/*
* 일반적인 머신러닝: train -> test -> evaluate
* 이메일 스팸 필터링: train 생략
*
* */
