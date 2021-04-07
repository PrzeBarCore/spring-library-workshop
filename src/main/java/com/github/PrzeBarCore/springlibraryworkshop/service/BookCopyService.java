package com.github.PrzeBarCore.springlibraryworkshop.service;

import com.github.PrzeBarCore.springlibraryworkshop.model.Book;
import com.github.PrzeBarCore.springlibraryworkshop.model.BookCopy;
import com.github.PrzeBarCore.springlibraryworkshop.model.BookCopyRepository;
import com.github.PrzeBarCore.springlibraryworkshop.model.projection.BookBookCopyWriteModel;
import org.springframework.stereotype.Service;

@Service
public class BookCopyService {
    private final PublisherService publisherService;
    private final BookCopyRepository repository;

    BookCopyService(final PublisherService publisherService, final BookCopyRepository bookCopyRepository) {
        this.publisherService = publisherService;
        this.repository = bookCopyRepository;
    }

    public BookCopy createBookCopy(BookBookCopyWriteModel toCreate, Book book) {
        BookCopy bookCopy= toCreate.toBookCopy(book);

        Integer publisherId = toCreate.getPublisher().getId();
        if(toCreate.isNewPublisher()){
            bookCopy.setPublisher(toCreate.getPublisher().toPublisher(bookCopy));
        } else {
            bookCopy.setPublisher(publisherService.findPublisherById(publisherId));
        }
       return bookCopy;
    }
}
