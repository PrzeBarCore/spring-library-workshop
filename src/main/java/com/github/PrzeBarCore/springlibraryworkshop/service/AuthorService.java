package com.github.PrzeBarCore.springlibraryworkshop.service;

import com.github.PrzeBarCore.springlibraryworkshop.model.AuthorRepository;
import com.github.PrzeBarCore.springlibraryworkshop.model.projection.BookAuthorReadModel;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
public class AuthorService {
    private AuthorRepository repository;

    AuthorService(final AuthorRepository repository) {
        this.repository = repository;
    }

    public List<BookAuthorReadModel> findAll(Optional<Integer> page, Optional<Integer> numberOfPositions){

        Pageable pageable= PageRequest.of(page.orElse(0), numberOfPositions.orElse(10));
        return repository.findAll(pageable).stream().map(BookAuthorReadModel::new).collect(toList());
    }
}
