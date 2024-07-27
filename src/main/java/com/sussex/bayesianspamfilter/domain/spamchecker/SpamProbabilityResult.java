package com.sussex.bayesianspamfilter.domain.spamchecker;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.List;
import java.util.Map;

@Data
@ToString
@AllArgsConstructor
public class SpamProbabilityResult {
    private double spamProbability;
    private List<String> topWords;
    private Map<String, Double> wordImpactPercentages;

}
