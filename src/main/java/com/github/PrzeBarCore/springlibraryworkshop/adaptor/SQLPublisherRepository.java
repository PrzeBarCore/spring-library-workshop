package com.github.PrzeBarCore.springlibraryworkshop.adaptor;

import com.github.PrzeBarCore.springlibraryworkshop.model.Publisher;
import com.github.PrzeBarCore.springlibraryworkshop.model.PublisherRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface SQLPublisherRepository extends PublisherRepository, JpaRepository<Publisher,Integer> {
}
