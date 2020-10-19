package com.github.PrzeBarCore.springlibraryworkshop.adaptor;

import com.github.PrzeBarCore.springlibraryworkshop.model.Section;
import com.github.PrzeBarCore.springlibraryworkshop.model.SectionRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface SQLSectionRepository extends SectionRepository, JpaRepository<Section,Integer> {

}
