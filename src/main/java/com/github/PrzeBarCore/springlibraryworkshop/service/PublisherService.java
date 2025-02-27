package com.github.PrzeBarCore.springlibraryworkshop.service;

import com.github.PrzeBarCore.springlibraryworkshop.dao.PublisherRepository;
import com.github.PrzeBarCore.springlibraryworkshop.model.BookEdition;
import com.github.PrzeBarCore.springlibraryworkshop.model.Publisher;
import com.github.PrzeBarCore.springlibraryworkshop.model.projection.BookPublishersRespPublisherDTO;
import com.github.PrzeBarCore.springlibraryworkshop.model.projection.BookReqPublisherDTO;
import com.github.PrzeBarCore.springlibraryworkshop.model.projection.PublisherReqRespPublisherDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class PublisherService {
    private final PublisherRepository repository;
    private final Logger logger= LoggerFactory.getLogger(PublisherService.class);
    public PublisherService(final PublisherRepository repository) {
        this.repository = repository;
    }

    @Transactional
     public Publisher createPublisher(BookReqPublisherDTO toCreate, BookEdition bookEdition){
         if (toCreate.isNewPublisher()) {
             throwExceptionIfPublisherNameIsTaken(toCreate.getName(), toCreate.getId());
             return toCreate.toPublisher(bookEdition);
         } else {
             return readPublisherById(toCreate.getId());
         }
     }

    public Page<BookPublishersRespPublisherDTO> readAllPublishers(Pageable pageable) {
        Page<Publisher> result= repository.findAll(pageable);

        if(pageable.getPageNumber()>=result.getTotalPages()){
            result=repository.findAll(pageable.first());
        }

        return result.map(BookPublishersRespPublisherDTO::new);
    }

    Publisher readPublisherById(int id){
         return repository.findById(id)
                 .orElseThrow(()->new IllegalArgumentException("Publisher with given ID doesn't exist!"));
    }

    public PublisherReqRespPublisherDTO getPublisherReadModelById(int id) {
        return new PublisherReqRespPublisherDTO(readPublisherById(id));
    }

     void deletePublisherIfBookEditionsIsEmpty(Integer id) {
        Publisher publisher=readPublisherById(id);
        if(publisher.getBookEditions().isEmpty()){
            repository.deleteById(id);
            logger.info("Deleting publisher");
        }
    }

    public void throwExceptionIfPublisherNameIsTaken(String name, Integer idOfPublisherToCheck){
        Optional<Publisher> publisher=repository.findByName(name.trim());
        if(publisher.isPresent()){
            if(!publisher.get().getId().equals(idOfPublisherToCheck))
            throw new IllegalArgumentException("Publisher with given name already exists");
        }
    }

    public PublisherReqRespPublisherDTO updatePublisher(PublisherReqRespPublisherDTO publisherToUpdate) {
        throwExceptionIfPublisherNameIsTaken(publisherToUpdate.getName(), publisherToUpdate.getId());
        Publisher publisher=readPublisherById(publisherToUpdate.getId());
        repository.save(publisherToUpdate.updatePublisherFromDTO(publisher));
        return new PublisherReqRespPublisherDTO(publisher);
    }
}
