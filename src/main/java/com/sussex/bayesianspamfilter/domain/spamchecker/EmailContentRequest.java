package com.sussex.bayesianspamfilter.domain.spamchecker;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EmailContentRequest {
    private String content;
}
