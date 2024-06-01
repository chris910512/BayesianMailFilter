package com.sussex.bayesianspamfilter.domain.spamchecker;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SpamCheckerController {

    private final BayesianFilter bayesianFilter;

    @GetMapping("/check-spam")
    public String checkSpam() {
        return "Spam Checker";
    }

    @PostMapping("/check-spam")
    public ResponseEntity<String> checkSpam(@RequestBody EmailContentRequest emailContentRequest) {
        double spamProbability = bayesianFilter.calculateSpamProbability(emailContentRequest);
        String message = spamProbability > 0.5 ? "This email is likely spam." : "This email is likely not spam.";
        return ResponseEntity.ok(message);
    }

    @PostMapping("/train-spam")
    public ResponseEntity<String> trainSpam(@RequestBody EmailTrainRequest emailContentRequest) {
        bayesianFilter.train(emailContentRequest);
        return ResponseEntity.ok().build();
    }
}
