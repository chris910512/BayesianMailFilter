package com.sussex.bayesianspamfilter.domain.spamchecker;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpamWordRepository extends JpaRepository<SpamWordEntity, Long> {
    Optional<SpamWordEntity> findByWord(String word);
}
