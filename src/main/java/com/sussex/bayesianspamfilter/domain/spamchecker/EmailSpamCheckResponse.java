package com.sussex.bayesianspamfilter.domain.spamchecker;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmailSpamCheckResponse {
    private String message;
    private Boolean isSpam;
    private Double spamProbability;
}
