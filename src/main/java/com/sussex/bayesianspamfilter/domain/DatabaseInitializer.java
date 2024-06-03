package com.sussex.bayesianspamfilter.domain;


import com.sussex.bayesianspamfilter.domain.spamchecker.SpamWordEntity;
import com.sussex.bayesianspamfilter.domain.spamchecker.SpamWordRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class DatabaseInitializer implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseInitializer.class);
    private final SpamWordRepository spamWordRepository;

    String[] words = {
            "Act now!", "Apply now!", "Call now!",
            "Don’t hesitate!", "For only", "Get started now",
            "Limited time", "Great offer", "Instant",
            "Now only", "Offer expires", "Once in a lifetime",
            "Order now", "Order today", "Special promotion",
            "Urgent", "While supplies last", "Bonus",
            "All new", "Amazing", "Certified",
            "Congratulations", "Fantastic deal", "For free",
            "Guaranteed", "Outstanding value", "Risk free",
            "Satisfaction guaranteed", "Free", "Free!",
            "Free trial", "Free consultation", "Free gift",
            "Free membership", "Free offer", "Free preview",
            "Free sample", "Free quote", "Sign up free today",
            "Deal", "Giving away", "No obligation",
            "No strings attached", "Offer", "Prize",
            "Trial", "Unlimited", "What are you waiting for?",
            "Win", "Winner", "You’re a winner! Won",
            "You have been selected", "#1", "100% free",
            "100% satisfied", "50% off", "One hundred percent guaranteed",
            "Click below", "Click here", "Increase sales",
            "Increase your sales", "Opt in", "Open",
            "Sale", "Sales", "Subscribe",
            "Chance", "Sample", "Satisfaction",
            "Solution", "Success", "Cards accepted",
            "Full refund", "Affordable", "Bargain",
            "Best price", "Cash", "Cash bonus",
            "Cheap", "Credit", "Discount",
            "For just $", "Lowest price", "Save big money",
            "Why pay more?", "Buy", "As seen on",
            "Buy direct", "Clearance", "Order",
            "$$$", "Marketing solutions", "Join millions",
            "Name brand", "No questions asked", "Giving it away",
            "Best rates", "Compare", "Drastically reduced", "Delivery", "Fast cash",
            "Hidden", "Income", "Make money", "Million dollars",
            "Verification", "No catch", "No credit check", "No fees",
            "No hidden costs", "No hidden fees", "No interest", "No investment",
            "Package", "Promise", "Pure profit", "Risk-free",
            "Unfortunate", "Unlimited", "Unsolicited", "Warranty",
    };

    @Override
    @Transactional
    public void run(String... args) {

        Random random = new Random();

        List<SpamWordEntity> spamWords = Arrays.stream(words)
                .map(spamWord -> SpamWordEntity.builder()
                        .word(spamWord)
                        .impactFactor(random.nextDouble())
                        .relatedWords(new ArrayList<>())
                        .build())
                .collect(Collectors.toList());

        spamWords.forEach(spamWord -> {
            spamWords.stream()
                    .filter(potentialRelatedWord -> !spamWord.equals(potentialRelatedWord) && potentialRelatedWord.getWord().contains(spamWord.getWord()))
                    .forEach(potentialRelatedWord -> spamWord.getRelatedWords().add(potentialRelatedWord));
        });

        spamWordRepository.saveAll(spamWords);

        logger.info("Database initialized with {} spam words", spamWordRepository.count());
    }
}
