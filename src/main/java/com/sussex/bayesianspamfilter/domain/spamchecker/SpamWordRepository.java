package com.sussex.bayesianspamfilter.domain.spamchecker;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpamWordRepository extends JpaRepository<SpamWordEntity, Long> {
}
