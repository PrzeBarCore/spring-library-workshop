package com.github.PrzeBarCore.springlibraryworkshop.service;

import com.github.PrzeBarCore.springlibraryworkshop.model.Book;
import com.github.PrzeBarCore.springlibraryworkshop.model.Section;
import com.github.PrzeBarCore.springlibraryworkshop.model.SectionRepository;
import com.github.PrzeBarCore.springlibraryworkshop.model.projection.Book.BookSectionWriteModel;
import org.springframework.stereotype.Service;

@Service
public class SectionService {
    private SectionRepository repository;

    SectionService(final SectionRepository repository) {
        this.repository = repository;
    }

    Section findSection(int id){
        Section result=repository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("Section with given id doesnt exist"));
        return result;
    }

    Integer createSectionAndGetId(Book book, BookSectionWriteModel toCreate){
        Section createdSection= repository.save(toCreate.toSection(book));
        return createdSection.getId();
    }

//    public List<BookSectionModel> findAll(Optional<Integer> page, Optional<Integer> numberOfPositions){
//
//        Pageable pageable= PageRequest.of(page.orElse(0), numberOfPositions.orElse(10));
//        return repository.findAll(pageable).stream().map(BookSectionModel::new).collect(toList());
//    }
}
