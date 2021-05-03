package com.github.PrzeBarCore.springlibraryworkshop.dao;

import com.github.PrzeBarCore.springlibraryworkshop.model.Publisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PublisherRepository {

    List<Publisher> findAll();

    Page<Publisher> findAll(Pageable pageable);

    Optional<Publisher> findById(Integer id);

    Optional<Publisher> findByName(String name);

    void deleteById(Integer id);

    Publisher save(Publisher toPublisher);
}
