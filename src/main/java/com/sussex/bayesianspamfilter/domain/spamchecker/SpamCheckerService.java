package com.sussex.bayesianspamfilter.domain.spamchecker;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SpamCheckerService {

    //TODO: Cache the spam words in memory
    //TODO: Implement a mechanism to remove spam words that are not used anymore
    //TODO: GraphDB for storing the spam words and their relations
    //TODO: Re-generate the spam words and their relations every 1 hours

    private final SpamWordRepository spamWordRepository;
    private final Preprocessor preprocessor;

    @Transactional
    public double calculateSpamProbability(EmailContentRequest emailContentRequest) {
        List<String> words = preprocessor.preprocess(emailContentRequest.getContent());
        List<SpamWordEntity> spamWords = spamWordRepository.findAll();

        double totalSpamScore = 0.0;
        for (String word : words) {
            for (SpamWordEntity spamWord : spamWords) {
                if (BayesianFilterUtils.jaccardSimilarity(spamWord.getWord(), word) > 0.8) {
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

    @Transactional
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
                double similarity = BayesianFilterUtils.jaccardSimilarity(spamWord.getWord(), word);
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

}
