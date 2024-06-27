package com.sussex.bayesianspamfilter.domain.spamchecker;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class Preprocessor {
    private static final List<String> STOP_WORDS = Arrays.asList("a", "the", "is", "it", "this", "and", "in", "to", "of");

    public List<String> preprocess(String content) {
        content = content.toLowerCase().replaceAll("[^a-z ]", "");
        List<String> words = Arrays.asList(content.split("\\s+"));
        return words.stream()
                .filter(word -> !STOP_WORDS.contains(word))
                .collect(Collectors.toList());
    }
}
