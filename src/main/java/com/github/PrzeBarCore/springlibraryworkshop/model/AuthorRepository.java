package com.github.PrzeBarCore.springlibraryworkshop.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

@RepositoryRestResource
interface AuthorRepository extends JpaRepository<Author,Integer> {
    @Override
    @RestResource(exported=false)
    void deleteById(Integer integer);

    @Override
    @RestResource(exported=false)
    void delete(Author author);
}
