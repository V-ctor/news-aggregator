package com.tochka.testtask.repositories;

import com.tochka.testtask.domain.Title;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TitleRepository extends JpaRepository<Title, Long> {

    Page<Title> findByTitleContainingIgnoreCase(String nameSubString, Pageable pageable);
}
