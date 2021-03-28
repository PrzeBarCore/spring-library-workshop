package com.github.PrzeBarCore.springlibraryworkshop.model.adapter;

import com.github.PrzeBarCore.springlibraryworkshop.model.BookCopyRepository;
import com.github.PrzeBarCore.springlibraryworkshop.model.BookCopy;
import org.springframework.data.jpa.repository.JpaRepository;

interface SQLBookCopyRepository extends JpaRepository<BookCopy,Integer>, BookCopyRepository {

}
