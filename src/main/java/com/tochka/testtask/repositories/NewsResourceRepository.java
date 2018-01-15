package com.tochka.testtask.repositories;

import com.tochka.testtask.domain.NewsResource;
import org.springframework.data.repository.CrudRepository;

public interface NewsResourceRepository extends CrudRepository<NewsResource, Long> {
    NewsResource findByUrl(String url);
    void deleteByUrl(String url);
}
