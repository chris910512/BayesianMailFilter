package com.sussex.bayesianspamfilter.domain;


import com.sussex.bayesianspamfilter.domain.spamchecker.SpamWordEntity;
import com.sussex.bayesianspamfilter.domain.spamchecker.SpamWordRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class DatabaseInitializer implements CommandLineRunner {

    private final SpamWordRepository spamWordRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        Resource resource = new ClassPathResource("spam-word.sql");
        String sqlStatements = FileCopyUtils.copyToString(new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8));
        String[] spamWords = sqlStatements.split(";");
        Arrays.stream(spamWords)
                .map(spamWord -> SpamWordEntity.builder().word(spamWord).build())
                .forEach(spamWordRepository::save);
    }
}
