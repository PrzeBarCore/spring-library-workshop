package com.github.PrzeBarCore.springlibraryworkshop.service;

import com.github.PrzeBarCore.springlibraryworkshop.model.SectionRepository;
import com.github.PrzeBarCore.springlibraryworkshop.model.projection.BookSectionReadModel;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
public class SectionService {
    private SectionRepository repository;

    SectionService(final SectionRepository repository) {
        this.repository = repository;
    }

    public List<BookSectionReadModel> findAll(Optional<Integer> page, Optional<Integer> numberOfPositions){

        Pageable pageable= PageRequest.of(page.orElse(0), numberOfPositions.orElse(10));
        return repository.findAll(pageable).stream().map(BookSectionReadModel::new).collect(toList());
    }
}
