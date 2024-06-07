package com.sussex.bayesianspamfilter.domain.spamchecker;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class BayesianFilterUtils {

    public static double jaccardSimilarity(String word1, String word2) {
        Set<String> set1 = new HashSet<>(Arrays.asList(word1.split("")));
        Set<String> set2 = new HashSet<>(Arrays.asList(word2.split("")));

        Set<String> intersection = new HashSet<>(set1);
        intersection.retainAll(set2);

        Set<String> union = new HashSet<>(set1);
        union.addAll(set2);

        return (double) intersection.size() / union.size();
    }
}
