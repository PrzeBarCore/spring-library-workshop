package com.github.PrzeBarCore.springlibraryworkshop.service;

import com.github.PrzeBarCore.springlibraryworkshop.dao.BookCopyRepository;
import com.github.PrzeBarCore.springlibraryworkshop.dao.BookEditionRepository;
import com.github.PrzeBarCore.springlibraryworkshop.model.Book;
import com.github.PrzeBarCore.springlibraryworkshop.model.BookCopy;
import com.github.PrzeBarCore.springlibraryworkshop.model.BookEdition;
import com.github.PrzeBarCore.springlibraryworkshop.model.projection.BookEditionReqBookCopyDTO;
import com.github.PrzeBarCore.springlibraryworkshop.model.projection.BookEditionReqBookEditionDTO;
import com.github.PrzeBarCore.springlibraryworkshop.model.projection.BookReqBookEditionDTO;
import com.github.PrzeBarCore.springlibraryworkshop.model.projection.BookReqPublisherDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class BookEditionService {
    private final PublisherService publisherService;
    private final BookEditionRepository repository;
    private final BookCopyRepository copyRepository;
    private final Logger logger = LoggerFactory.getLogger(BookEditionService.class);

    BookEditionService(final PublisherService publisherService, final BookEditionRepository bookEditionRepository, final BookCopyRepository copyRepository) {
        this.publisherService = publisherService;
        this.repository = bookEditionRepository;
        this.copyRepository = copyRepository;
    }

    BookEdition createBookEdition(BookReqBookEditionDTO toCreate, Book book) {
        BookEdition bookEdition = toCreate.toBookEdition(book);
        Integer publisherId = toCreate.getPublisher().getId();
        BookReqPublisherDTO publisherOfToCreate=toCreate.getPublisher();

        if (publisherOfToCreate.isNewPublisher()) {
            publisherService.throwExceptionIfPublisherNameIsTaken(publisherOfToCreate.getName(), publisherId);
            bookEdition.setPublisher(publisherOfToCreate.toPublisher(bookEdition));
        } else {
            bookEdition.setPublisher(publisherService.readPublisherById(publisherId));
        }
        return bookEdition;
    }

    public BookEditionReqBookEditionDTO getBookEditionWriteModelById(int id) {
        return new BookEditionReqBookEditionDTO(
                repository.findById(id)
                        .orElseThrow(() -> new IllegalArgumentException("Book editions with given id doesn't exist")));
    }

    BookEdition getBookEditionById(int id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Book editions with given id doesn't exist"));
    }

    @Transactional
    public void updateEdition(BookEditionReqBookEditionDTO current, int id) {
        BookEdition editionToUpdate = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Book Edition with given id doesn't exist"));

        editionToUpdate.setPublicationDate(current.getPublicationDate());

        BookReqPublisherDTO newPublisher = current.getPublisher();
        Integer newPublisherId= newPublisher.getId();
        Integer previousPublisherId = editionToUpdate.getPublisher().getId();

        if (newPublisher.isNewPublisher()) {
            publisherService.throwExceptionIfPublisherNameIsTaken(newPublisher.getName(), newPublisherId);
            editionToUpdate.setPublisher(newPublisher.toPublisher(editionToUpdate));
        } else {
            if (!previousPublisherId.equals(newPublisherId)) {
                editionToUpdate.setPublisher(publisherService.readPublisherById(newPublisherId));
            }
        }

        current.getBookCopiesToRemove().stream()
                .map(BookEditionReqBookCopyDTO::getId)
                .forEach(bookCopyIdToRemove -> {
                    editionToUpdate.getBookCopies()
                            .remove(copyRepository
                                    .findBookCopyById(bookCopyIdToRemove)
                                    .orElseThrow(() -> new IllegalArgumentException("Book copy with given id doesnt exist")));
                    copyRepository.deleteBookCopyById(bookCopyIdToRemove);
                });

        current.getBookCopies().forEach(bookCopy -> {
            if (bookCopy.isNewCopy())
                editionToUpdate.getBookCopies()
                        .add(new BookCopy(editionToUpdate));
        });

        repository.save(editionToUpdate);

        current.getBookCopiesToRemove().stream()
                .map(BookEditionReqBookCopyDTO::getId).
                forEach(copyRepository::deleteBookCopyById);
        publisherService.deletePublisherIfBookEditionsIsEmpty(previousPublisherId);
    }


    @Transactional
    void deleteBookEditionById(int editionId) {
        BookEdition toDelete = repository.findById(editionId)
                .orElseThrow(() -> new IllegalArgumentException("Book editions with given id doesn't exist"));
        int publisherId = toDelete.getPublisher().getId();

        toDelete.getBookCopies().forEach(copy -> {
            if (copy.getState() != 0)
                throw new IllegalStateException("Cannot delete book's edition when not every copy is available");
        });
        toDelete.getPublisher().getBookEditions().remove(toDelete);

        repository.deleteById(editionId);
        publisherService.deletePublisherIfBookEditionsIsEmpty(publisherId);
        logger.info("deleted book edition");
    }
}
