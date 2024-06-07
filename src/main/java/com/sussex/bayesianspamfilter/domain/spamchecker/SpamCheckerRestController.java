package com.sussex.bayesianspamfilter.domain.spamchecker;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SpamCheckerRestController {

    private final SpamCheckerService spamCheckerService;

    @PostMapping("/check-spam")
    public ResponseEntity<EmailSpamCheckResponse> checkSpam(@RequestBody EmailContentRequest emailContentRequest) {
        double spamProbability = spamCheckerService.calculateSpamProbability(emailContentRequest);
        boolean isSpam = spamProbability > 0.2;

        return ResponseEntity.ok(EmailSpamCheckResponse.builder()
                .message(isSpam ? "This email is likely spam." : "This email is likely not spam.")
                .isSpam(isSpam)
                .spamProbability(spamProbability)
                .build()
        );
    }

    @PostMapping("/train-spam")
    public ResponseEntity<Void> trainSpam(@RequestBody EmailTrainRequest emailContentRequest) {
        spamCheckerService.train(emailContentRequest);
        return ResponseEntity.ok().build();
    }
}
