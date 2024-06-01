package com.sussex.bayesianspamfilter.domain.spamchecker;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SpamWordEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String word;

    @Setter
    private double impactFactor;

    @ManyToMany
    @JoinTable(
            name = "spam_word_relations",
            joinColumns = @JoinColumn(name = "word_id"),
            inverseJoinColumns = @JoinColumn(name = "related_word_id")
    )
    private List<SpamWordEntity> relatedWords;

}
