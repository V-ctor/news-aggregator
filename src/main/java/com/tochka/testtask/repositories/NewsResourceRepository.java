package com.tochka.testtask.repositories;

import com.tochka.testtask.domain.NewsResource;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsResourceRepository extends JpaRepository<NewsResource, Long> {
    NewsResource findByUrl(String url);
    void deleteByUrl(String url);
}
