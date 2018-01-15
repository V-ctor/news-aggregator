package com.tochka.testtask.repositories;

import com.tochka.testtask.domain.NewsResource;
import com.tochka.testtask.domain.ParsingRule;
import org.springframework.data.repository.CrudRepository;

public interface ParsingRuleRepository extends CrudRepository<ParsingRule, Long> {
    ParsingRule findByNewsResource (NewsResource newsResource);
    void deleteByNewsResource (NewsResource newsResource);
}
