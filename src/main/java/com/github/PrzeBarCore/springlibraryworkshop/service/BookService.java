package com.github.PrzeBarCore.springlibraryworkshop.service;

import com.github.PrzeBarCore.springlibraryworkshop.model.BookRepository;
import com.github.PrzeBarCore.springlibraryworkshop.model.projection.BookReadModel;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
public class BookService {
    BookRepository repository;

    public BookService(final BookRepository repository) {
        this.repository = repository;
    }

    public List<BookReadModel> findAll(){
        return repository.findAll().stream().map(BookReadModel::new).collect(toList());
    }

    public List<BookReadModel> findAll(Optional<Integer> pageNumber, Optional<Integer> pageSize){

        Pageable pageable= PageRequest.of(pageNumber.orElse(0), pageSize.orElse(10));
        return repository.findAll(pageable).stream().map(BookReadModel::new).collect(toList());
    }
}
