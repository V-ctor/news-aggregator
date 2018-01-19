package com.tochka.testtask.service;

import com.tochka.testtask.domain.Title;
import com.tochka.testtask.repositories.TitleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class TitleService {
    private static final int MAX_RETURN_SIZE = 1000;
    private TitleRepository titleRepository;

    @Autowired
    public TitleService(TitleRepository titleRepository) {
        this.titleRepository = titleRepository;
    }

    public Page<Title> findByAnyTitleContaining(String nameSubString) {
        return findByAnyTitleContaining(nameSubString, 0, MAX_RETURN_SIZE);
    }

    public Page<Title> findByAnyTitleContaining(String nameSubString, int start, int length) {
        assert start >= 0 && start <= MAX_RETURN_SIZE;
        assert length <= MAX_RETURN_SIZE;
        assert start < length;
        final int page = start / length;
        final Pageable pageable = new PageRequest(page, length);
        return titleRepository.findByTitleContaining(nameSubString, pageable);
    }

    public Page<Title> findByAnyTitleContaining(String nameSubString, int start, int length, String sortField, String sortDir) {
        assert start >= 0 && start <= MAX_RETURN_SIZE;
        assert length <= MAX_RETURN_SIZE;
        assert start < length;
        final int page = start / length;

        final Pageable pageable = new PageRequest(page, length, Sort.Direction.fromString(sortDir), sortField);
        return titleRepository.findByTitleContaining(nameSubString, pageable);
    }

    public void save(Title title) {
        titleRepository.save(title);
    }

}
