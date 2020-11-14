package com.github.PrzeBarCore.springlibraryworkshop.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface SectionRepository {

    Optional<Section> findById(Integer id);

    Page<Section> findAll(Pageable page);

    List<Section> findAll();

}
