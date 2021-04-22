package com.github.PrzeBarCore.springlibraryworkshop.dao.adapter;

import com.github.PrzeBarCore.springlibraryworkshop.dao.BookEditionRepository;
import com.github.PrzeBarCore.springlibraryworkshop.model.BookEdition;
import org.springframework.data.jpa.repository.JpaRepository;

interface SQLBookEditionRepository extends JpaRepository<BookEdition,Integer>, BookEditionRepository {

}
