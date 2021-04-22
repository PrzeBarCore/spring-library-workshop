package com.github.PrzeBarCore.springlibraryworkshop.service;

import com.github.PrzeBarCore.springlibraryworkshop.dao.BookEditionRepository;
import com.github.PrzeBarCore.springlibraryworkshop.model.Book;
import com.github.PrzeBarCore.springlibraryworkshop.model.BookEdition;
import com.github.PrzeBarCore.springlibraryworkshop.model.Publisher;
import com.github.PrzeBarCore.springlibraryworkshop.model.projection.BookReqBookEditionDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
public class BookEditionService {
    private final PublisherService publisherService;
    private final BookEditionRepository repository;
    private final Logger logger= LoggerFactory.getLogger(BookEditionService.class);

    BookEditionService(final PublisherService publisherService, final BookEditionRepository bookEditionRepository) {
        this.publisherService = publisherService;
        this.repository = bookEditionRepository;
    }

        BookEdition createBookEdition(BookReqBookEditionDTO toCreate, Book book) {
        BookEdition bookEdition= toCreate.toBookEdition(book);

        Integer publisherId = toCreate.getPublisher().getId();
        if(toCreate.isNewPublisher()){
            bookEdition.setPublisher(toCreate.getPublisher().toPublisher(bookEdition));
        } else {
            bookEdition.setPublisher(publisherService.readPublisherById(publisherId));
        }
       return bookEdition;
    }

    void deleteBookEdition(BookEdition toDelete){
        Publisher publisher=toDelete.getPublisher();
        int bookEditionId= toDelete.getId();
        repository.findById(bookEditionId).orElseThrow(()->new IllegalArgumentException("Book editions with given id doesn't exist"));
        repository.deleteById(bookEditionId);
        logger.info("deleted book edition");
        publisherService.deletePublisher(publisher.getId());
        logger.info("deleted publisher");
    }

}
