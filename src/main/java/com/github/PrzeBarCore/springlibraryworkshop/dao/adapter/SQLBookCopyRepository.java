package com.github.PrzeBarCore.springlibraryworkshop.dao.adapter;

import com.github.PrzeBarCore.springlibraryworkshop.dao.BookCopyRepository;
import com.github.PrzeBarCore.springlibraryworkshop.model.BookCopy;
import com.github.PrzeBarCore.springlibraryworkshop.model.BookEdition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SQLBookCopyRepository extends BookCopyRepository, JpaRepository<BookCopy,Integer> {

    int countByBookEdition(BookEdition bookEdition);

}
