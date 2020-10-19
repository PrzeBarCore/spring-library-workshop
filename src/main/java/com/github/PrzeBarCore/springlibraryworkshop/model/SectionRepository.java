package com.github.PrzeBarCore.springlibraryworkshop.model;

import java.util.List;
import java.util.Optional;

public interface SectionRepository {

    Optional<Section> findById(Integer id);

    List<Section> findAll();

}
