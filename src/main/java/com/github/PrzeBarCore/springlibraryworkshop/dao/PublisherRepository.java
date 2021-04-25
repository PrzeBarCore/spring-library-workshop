package com.github.PrzeBarCore.springlibraryworkshop.dao;

import com.github.PrzeBarCore.springlibraryworkshop.model.Publisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PublisherRepository {

    Optional<Publisher> findById(Integer id);

    List<Publisher> findAll();

    Publisher save(Publisher toPublisher);

    Page<Publisher> findAll(Pageable pageable);

    void deletePublisherById(Integer id);
}
