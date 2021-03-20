package com.github.PrzeBarCore.springlibraryworkshop.adaptor;

import com.github.PrzeBarCore.springlibraryworkshop.model.BookCopy;
import com.github.PrzeBarCore.springlibraryworkshop.model.BookCopyRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SQLBookCopyRepository extends JpaRepository<BookCopy,Integer>, BookCopyRepository {

}
