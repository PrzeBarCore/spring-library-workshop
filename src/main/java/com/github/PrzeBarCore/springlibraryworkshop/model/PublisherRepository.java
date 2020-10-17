package com.github.PrzeBarCore.springlibraryworkshop.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
interface PublisherRepository extends JpaRepository<Publisher,Integer> {
}
