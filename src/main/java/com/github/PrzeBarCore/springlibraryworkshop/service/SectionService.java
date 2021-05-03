package com.github.PrzeBarCore.springlibraryworkshop.service;

import com.github.PrzeBarCore.springlibraryworkshop.dao.SectionRepository;
import com.github.PrzeBarCore.springlibraryworkshop.model.Book;
import com.github.PrzeBarCore.springlibraryworkshop.model.Section;
import com.github.PrzeBarCore.springlibraryworkshop.model.projection.BookReqSectionDTO;
import com.github.PrzeBarCore.springlibraryworkshop.model.projection.BookSectionsRespSectionDTO;
import com.github.PrzeBarCore.springlibraryworkshop.model.projection.SectionReqRespSectionDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SectionService {
    private final SectionRepository repository;
    private final Logger logger= LoggerFactory.getLogger(SectionService.class);
    SectionService(final SectionRepository repository) {
        this.repository = repository;
    }

    public Section createSection(BookReqSectionDTO toCreate, Book book){
        if (toCreate.isNewSection()) {
            throwExceptionIfSectionNameIsTaken(toCreate.getName(), toCreate.getId());
            return toCreate.toSection(book);
        } else {
            return readSectionById(toCreate.getId());
        }
    }


    public Page<BookSectionsRespSectionDTO> readAllSections(Pageable pageable) {
        Page<Section> result= repository.findAll(pageable);
        if(pageable.getPageNumber()>=result.getTotalPages()){
            result=repository.findAll(pageable.first());
        }
        return result.map(BookSectionsRespSectionDTO::new);
    }

    Section readSectionById(int id){
        return repository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("Section with given id doesnt exist"));
    }

    public SectionReqRespSectionDTO getSectionReadModelById(int id) {
        return new SectionReqRespSectionDTO(readSectionById(id));
    }

    void deleteSectionIfListOfBooksIsEmpty(Integer id) {
        Section section= readSectionById(id);
        if(section.getBooks().isEmpty()){
            repository.deleteById(id);
            logger.info("Deleting section");
        }
    }

    public void throwExceptionIfSectionNameIsTaken(String name, Integer idOfSectionToCheck){
        Optional<Section> section=repository.findByName(name.trim());
        if(section.isPresent()){
            if(!section.get().getId().equals(idOfSectionToCheck))
                throw new IllegalArgumentException("Section with given name already exists");
        }
    }

    public SectionReqRespSectionDTO updateSection(SectionReqRespSectionDTO toUpdate) {
        throwExceptionIfSectionNameIsTaken(toUpdate.getName(), toUpdate.getId());
        Section section=readSectionById(toUpdate.getId());
        repository.save(toUpdate.updateSectionFromDTO(section));
        return new SectionReqRespSectionDTO(section);
    }
}
