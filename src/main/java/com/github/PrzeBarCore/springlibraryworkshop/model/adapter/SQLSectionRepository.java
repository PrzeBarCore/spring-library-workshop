package com.github.PrzeBarCore.springlibraryworkshop.model.adapter;

import com.github.PrzeBarCore.springlibraryworkshop.model.SectionRepository;
import com.github.PrzeBarCore.springlibraryworkshop.model.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface SQLSectionRepository extends SectionRepository, JpaRepository<Section,Integer> {

}
