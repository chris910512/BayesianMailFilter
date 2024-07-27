package com.sussex.bayesianspamfilter.domain.spamchecker;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class SpamCheckerRestController {

    private final SpamCheckerService spamCheckerService;

    @PostMapping("/check-spam")
    public ResponseEntity<EmailSpamCheckResponse> checkSpam(@RequestBody EmailContentRequest emailContentRequest) {
        SpamProbabilityResult spamProbabilityResult = spamCheckerService.calculateSpamProbability(emailContentRequest);
        boolean isSpam = spamProbabilityResult.getSpamProbability() > 0.2;

        return ResponseEntity.ok(EmailSpamCheckResponse.builder()
                .message(isSpam ? "This email is likely spam." : "This email is likely not spam.")
                .isSpam(isSpam)
                .spamProbability(spamProbabilityResult.getSpamProbability())
                .spamProbabilityResult(spamProbabilityResult)
                .build()
        );
    }

    @PostMapping("/train-spam")
    public ResponseEntity<Void> trainSpam(@RequestBody EmailTrainRequest emailContentRequest) {
        spamCheckerService.train(emailContentRequest);
        return ResponseEntity.ok().build();
    }
}
