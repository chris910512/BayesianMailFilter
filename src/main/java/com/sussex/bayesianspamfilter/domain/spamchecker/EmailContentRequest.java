package com.sussex.bayesianspamfilter.domain.spamchecker;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EmailContentRequest {
    private final String content;
}
