package com.sussex.bayesianspamfilter.domain;


import com.sussex.bayesianspamfilter.domain.spamchecker.SpamWordEntity;
import com.sussex.bayesianspamfilter.domain.spamchecker.SpamWordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class DatabaseInitializer implements CommandLineRunner {

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
            "Best rates", "Compare", "Drastically reduced"
    };

    @Override
    @Transactional
    public void run(String... args) {
        Arrays.stream(words)
                .map(spamWord -> SpamWordEntity.builder().word(spamWord).build())
                .forEach(spamWordRepository::save);
    }
}
