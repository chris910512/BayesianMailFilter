package com.sussex.bayesianspamfilter.domain.spamchecker;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class SpamCheckerController {

    private final Preprocessor preprocessor;
    private final FeatureExtractor featureExtractor;
    private final BayesianClassifier bayesianClassifier;
    private final Evaluator evaluator;

    @GetMapping("/check-spam")
    public String checkSpam() {
        return "Spam Checker";
    }
}
