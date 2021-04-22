package com.github.PrzeBarCore.springlibraryworkshop.service;

import com.github.PrzeBarCore.springlibraryworkshop.dao.SectionRepository;
import com.github.PrzeBarCore.springlibraryworkshop.model.Book;
import com.github.PrzeBarCore.springlibraryworkshop.model.Section;
import com.github.PrzeBarCore.springlibraryworkshop.model.projection.BookReqSectionDTO;
import com.github.PrzeBarCore.springlibraryworkshop.model.projection.BookSectionsRespSectionDTO;
import com.github.PrzeBarCore.springlibraryworkshop.model.projection.SectionRespSectionDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class SectionService {
    private final SectionRepository repository;

    SectionService(final SectionRepository repository) {
        this.repository = repository;
    }

    public Section readSectionById(int id){
        Section result=repository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("Section with given id doesnt exist"));
        return result;
    }

    public Integer createSectionAndGetId(Book book, BookReqSectionDTO toCreate){
        Section createdSection= repository.save(toCreate.toSection(book));
        return createdSection.getId();
    }

    public Page<BookSectionsRespSectionDTO> readAllSections(Pageable pageable) {
        Page<Section> result= repository.findAll(pageable);

        if(pageable.getPageNumber()>=result.getTotalPages()){
            result=repository.findAll(pageable.first());
        }

        return result.map(BookSectionsRespSectionDTO::new);
    }

    public SectionRespSectionDTO getSectionReadModelById(int id) {
        SectionRespSectionDTO result= repository.findById(id).map(SectionRespSectionDTO::new)
                .orElseThrow(()->new IllegalArgumentException("Section with given id doesn't exist"));
        return result;
    }
}
