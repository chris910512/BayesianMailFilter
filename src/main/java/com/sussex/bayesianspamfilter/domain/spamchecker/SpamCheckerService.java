package com.sussex.bayesianspamfilter.domain.spamchecker;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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
    public SpamProbabilityResult calculateSpamProbability(EmailContentRequest emailContentRequest) {
        List<String> words = preprocessor.preprocess(emailContentRequest.getContent());
        List<SpamWordEntity> spamWords = spamWordRepository.findAll();

        double totalSpamScore = 0.0;
        Map<String, Double> wordImpactMap = new HashMap<>();

        for (String word : words) {
            for (SpamWordEntity spamWord : spamWords) {
                if (BayesianFilterUtils.jaccardSimilarity(spamWord.getWord(), word) > 0.8) {
                    double impact = spamWord.getImpactFactor();
                    totalSpamScore += impact;
                    wordImpactMap.put(word, wordImpactMap.getOrDefault(word, 0.0) + impact);
                    for (SpamWordEntity relatedWord : spamWord.getRelatedWords()) {
                        if (words.contains(relatedWord.getWord())) {
                            impact = relatedWord.getImpactFactor();
                            totalSpamScore += impact;
                            wordImpactMap.put(relatedWord.getWord(), wordImpactMap.getOrDefault(relatedWord.getWord(), 0.0) + impact);
                        }
                    }
                }
            }
        }

        List<Map.Entry<String, Double>> sortedWords = new ArrayList<>(wordImpactMap.entrySet());
        sortedWords.sort((e1, e2) -> Double.compare(e2.getValue(), e1.getValue()));
        List<String> topWords = new ArrayList<>();
        Map<String, Double> topWordImpactPercentages = new HashMap<>();
        for (int i = 0; i < Math.min(5, sortedWords.size()); i++) {
            String word = sortedWords.get(i).getKey();
            topWords.add(word);
            topWordImpactPercentages.put(word, (sortedWords.get(i).getValue() / totalSpamScore) * 100);
        }

        double spamProbability = totalSpamScore / words.size();
        return new SpamProbabilityResult(spamProbability, topWords, topWordImpactPercentages);
    }

    @Transactional
    public void train(EmailTrainRequest emailTrainRequest) {
        List<String> words = preprocessor.preprocess(emailTrainRequest.getContent());
        List<SpamWordEntity> spamWords = spamWordRepository.findAll();

        Map<String, Integer> wordCounts = new HashMap<>();
        for (SpamWordEntity spamWord : spamWords) {
            wordCounts.put(spamWord.getWord(), wordCounts.getOrDefault(spamWord.getWord(), 0) + 1);
        }

        for (String word : words) {
            double maxSimilarity = 0.8;

            List<SpamWordEntity> newSpamWords = new ArrayList<>();

            for (SpamWordEntity spamWord : spamWords) {
                double similarity = BayesianFilterUtils.jaccardSimilarity(spamWord.getWord(), word);
                if (similarity > maxSimilarity) {
                    newSpamWords.add(spamWord);
                }
            }
            Optional<SpamWordEntity> optionalSpamWordEntity = spamWordRepository.findByWord(word);
            int count = wordCounts.getOrDefault(word, 0);
            double impactFactorVector = count * 0.01;
            if(optionalSpamWordEntity.isPresent()){
                double impactFactor = optionalSpamWordEntity.get().getImpactFactor();
                if (impactFactor > 0.8) {
                    impactFactorVector = 0.8;
                } else if (impactFactor > 0.7) {
                    impactFactorVector = count * 0.003;
                } else if (impactFactor > 0.6) {
                    impactFactorVector = count * 0.004;
                } else if (impactFactor > 0.5) {
                    impactFactorVector = count * 0.005;
                }
                optionalSpamWordEntity.get().setImpactFactor(optionalSpamWordEntity.get().getImpactFactor() + impactFactorVector);
            } else {
                double impactFactor = 0.5 + impactFactorVector;
                spamWordRepository.save(SpamWordEntity.builder()
                        .word(word)
                        .impactFactor(impactFactor)
                        .relatedWords(newSpamWords)
                        .build());
            }

        }
    }

}
