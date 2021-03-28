package com.github.PrzeBarCore.springlibraryworkshop.model.adapter;

import com.github.PrzeBarCore.springlibraryworkshop.model.PublisherRepository;
import com.github.PrzeBarCore.springlibraryworkshop.model.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface SQLPublisherRepository extends PublisherRepository, JpaRepository<Publisher,Integer> {
}
