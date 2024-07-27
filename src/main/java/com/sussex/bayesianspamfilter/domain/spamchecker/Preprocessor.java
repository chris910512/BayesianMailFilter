package com.sussex.bayesianspamfilter.domain.spamchecker;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class Preprocessor {
    public static final List<String> STOP_WORDS = Arrays.asList(
            "a", "the", "is", "it", "this",
            "and", "in", "to", "of", "your",
            "best", "regards", "Dear"
    );

    public List<String> preprocess(String content) {
        List<String> words = Arrays.asList(content.split("\\s+"));
        List<String> ngrams = new ArrayList<>();

        for (int n = 1; n <= 3; n++) {
            for (int i = 0; i < words.size() - n + 1; i++) {
                String ngram = String.join(" ", words.subList(i, i + n));
                if (!STOP_WORDS.contains(ngram)) {
                    ngrams.add(ngram);
                }
            }
        }

        return ngrams;
    }
}
