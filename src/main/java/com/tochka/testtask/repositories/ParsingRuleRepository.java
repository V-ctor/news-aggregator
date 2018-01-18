package com.tochka.testtask.repositories;

import com.tochka.testtask.domain.NewsResource;
import com.tochka.testtask.domain.ParsingRule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParsingRuleRepository extends JpaRepository<ParsingRule, Long> {
    ParsingRule findByNewsResource (NewsResource newsResource);
    void deleteByNewsResource (NewsResource newsResource);
}
