package com.tochka.testtask.repositories;

import com.tochka.testtask.domain.Title;
import org.springframework.data.jpa.repository.JpaRepository;

interface TitleRepository extends JpaRepository<Title, Long> {
}
