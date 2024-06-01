package com.sussex.bayesianspamfilter.domain.spamchecker;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EmailTrainRequest {
    private final String content;
    private final boolean isSpam;
}
