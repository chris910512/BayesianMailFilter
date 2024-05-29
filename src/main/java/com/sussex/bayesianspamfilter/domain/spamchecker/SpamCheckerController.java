package com.sussex.bayesianspamfilter.domain.spamchecker;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SpamCheckerController {

    @GetMapping("/check-spam")
    public String checkSpam() {
        return "Spam Checker";
    }
}
