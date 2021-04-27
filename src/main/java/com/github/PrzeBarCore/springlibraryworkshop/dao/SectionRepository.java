package com.github.PrzeBarCore.springlibraryworkshop.dao;

import com.github.PrzeBarCore.springlibraryworkshop.model.Section;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface SectionRepository {
    List<Section> findAll();

    Page<Section> findAll(Pageable page);

    Optional<Section> findSectionById(Integer id);

    Optional<Section> findSectionByName(String trim);

    void deleteSectionById(Integer id);

    Section save(Section toCreate);
}
