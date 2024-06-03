package com.sussex.bayesianspamfilter.domain.spamchecker;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class BayesianFilter {

    private final SpamWordRepository spamWordRepository;
    private final Preprocessor preprocessor;

    public double calculateSpamProbability(EmailContentRequest emailContentRequest) {
        List<String> words = preprocessor.preprocess(emailContentRequest.getContent());
        List<SpamWordEntity> spamWords = spamWordRepository.findAll();

        double totalSpamScore = 0.0;
        for (String word : words) {
            for (SpamWordEntity spamWord : spamWords) {
                if (jaccardSimilarity(spamWord.getWord(), word) > 0.8) {
                    totalSpamScore += spamWord.getImpactFactor();
                    for (SpamWordEntity relatedWord : spamWord.getRelatedWords()) {
                        if (words.contains(relatedWord.getWord())) {
                            totalSpamScore += relatedWord.getImpactFactor();
                        }
                    }
                }
            }
        }

        return totalSpamScore / words.size();
    }

    public void train(EmailTrainRequest emailTrainRequest) {
        List<String> words = preprocessor.preprocess(emailTrainRequest.getContent());
        List<SpamWordEntity> spamWords = spamWordRepository.findAll();

        List<SpamWordEntity> newSpamWords = new ArrayList<>();

        Map<String, Integer> wordCounts = new HashMap<>();
        for (SpamWordEntity spamWord : spamWords) {
            wordCounts.put(spamWord.getWord(), wordCounts.getOrDefault(spamWord.getWord(), 0) + 1);
        }

        for (String word : words) {
            double maxSimilarity = 0.8;

            for (SpamWordEntity spamWord : spamWords) {
                double similarity = jaccardSimilarity(spamWord.getWord(), word);
                if (similarity > maxSimilarity) {
                    newSpamWords.add(spamWord);
                }
            }

            int count = wordCounts.getOrDefault(word, 0);
            double impactFactor = 0.1 + count * 0.01;
            spamWordRepository.save(SpamWordEntity.builder()
                    .word(word)
                    .impactFactor(impactFactor)
                    .relatedWords(newSpamWords)
                    .build());
        }
    }

    public double jaccardSimilarity(String word1, String word2) {
        Set<String> set1 = new HashSet<>(Arrays.asList(word1.split("")));
        Set<String> set2 = new HashSet<>(Arrays.asList(word2.split("")));

        Set<String> intersection = new HashSet<>(set1);
        intersection.retainAll(set2);

        Set<String> union = new HashSet<>(set1);
        union.addAll(set2);

        return (double) intersection.size() / union.size();
    }
}
