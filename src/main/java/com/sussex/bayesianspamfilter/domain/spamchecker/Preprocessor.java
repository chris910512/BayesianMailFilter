package com.sussex.bayesianspamfilter.domain.spamchecker;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class Preprocessor {
    public List<String> preprocess(String content) {
        content = content.toLowerCase().replaceAll("[^a-z ]", "");
        return Arrays.asList(content.split("\\s+"));
    }
}
