package com.sussex.bayesianspamfilter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpamMailFilterApplication {
    public static void main(String[] args) {

        SpringApplication.run(SpamMailFilterApplication.class, args);
    }
}

/*
* 일반적인 머신러닝: train -> test -> evaluate
* 이메일 스팸 필터링: train 생략
*
* */
