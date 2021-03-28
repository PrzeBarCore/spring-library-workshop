package com.github.PrzeBarCore.springlibraryworkshop.service;

import com.github.PrzeBarCore.springlibraryworkshop.model.SectionRepository;
import com.github.PrzeBarCore.springlibraryworkshop.model.Book;
import com.github.PrzeBarCore.springlibraryworkshop.model.projection.BookSectionWriteModel;
import com.github.PrzeBarCore.springlibraryworkshop.model.Section;
import org.springframework.stereotype.Service;

@Service
public class SectionService {
    private final SectionRepository repository;

    SectionService(final SectionRepository repository) {
        this.repository = repository;
    }

    public Section findSection(int id){
        Section result=repository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("Section with given id doesnt exist"));
        return result;
    }

    public Integer createSectionAndGetId(Book book, BookSectionWriteModel toCreate){
        Section createdSection= repository.save(toCreate.toSection(book));
        return createdSection.getId();
    }

}
