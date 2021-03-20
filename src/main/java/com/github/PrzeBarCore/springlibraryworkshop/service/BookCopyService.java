package com.github.PrzeBarCore.springlibraryworkshop.service;

import com.github.PrzeBarCore.springlibraryworkshop.model.*;
import com.github.PrzeBarCore.springlibraryworkshop.model.projection.Book.BookBookCopyWriteModel;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class BookCopyService {
    PublisherRepository publisherRepository;
    BookCopyRepository repository;

    BookCopyService(PublisherRepository publisherRepository, BookCopyRepository bookCopyRepository) {
        this.publisherRepository = publisherRepository;
        this.repository = bookCopyRepository;
    }

    BookCopy createBookCopy(BookBookCopyWriteModel toCreate, Book book) {
        BookCopy bookCopy= toCreate.toBookCopy(book);

        Integer publisherId = toCreate.getPublisher().getId();
        if(publisherId==null){
            bookCopy.setPublisher(toCreate.getPublisher().toPublisher(bookCopy));
        } else {
            Optional<Publisher> publisher = publisherRepository.findById(publisherId);
            bookCopy.setPublisher(Optional.ofNullable(publisher)
                    .get()
                    .orElseThrow(() -> new IllegalArgumentException("Publisher with given ID doesnt exist")));
        }
       return bookCopy;
    }
}
