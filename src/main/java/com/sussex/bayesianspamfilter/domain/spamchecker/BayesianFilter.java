package com.sussex.bayesianspamfilter.domain.spamchecker;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

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
                if (spamWord.getWord().equals(word)) {
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
}
