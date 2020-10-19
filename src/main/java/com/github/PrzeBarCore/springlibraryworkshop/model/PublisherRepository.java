package com.github.PrzeBarCore.springlibraryworkshop.model;

import java.util.List;
import java.util.Optional;

public interface PublisherRepository {

    Optional<Publisher> findById(Integer id);

    List<Publisher> findAll();

}
