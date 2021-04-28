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

import java.util.List;
import java.util.Optional;


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

    @Transactional
     BookEdition createBookEdition(BookReqBookEditionDTO toCreate, Book book, boolean isBookNew) {
        BookEdition bookEdition = toCreate.toBookEdition(book);
        Integer publisherId = toCreate.getPublisher().getId();
        BookReqPublisherDTO publisherOfToCreate = toCreate.getPublisher();

        if (publisherOfToCreate.isNewPublisher()) {
            publisherService.throwExceptionIfPublisherNameIsTaken(publisherOfToCreate.getName(), publisherId);
            bookEdition.setPublisher(publisherOfToCreate.toPublisher(bookEdition));
        } else {
            bookEdition.setPublisher(publisherService.readPublisherById(publisherId));
        }
        if (!isBookNew) {
            throwExceptionIfBookEditionAlreadyExist(bookEdition);
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
    public void updateEdition(BookEditionReqBookEditionDTO newEdition, int id) {
        BookEdition editionToUpdate = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Book Edition with given id doesn't exist"));

        editionToUpdate.setPublicationDate(newEdition.getPublicationDate());
        BookReqPublisherDTO publisherOfNewEdition = newEdition.getPublisher();
        Integer newPublisherId = publisherOfNewEdition.getId();
        Integer previousPublisherId = editionToUpdate.getPublisher().getId();

        if (publisherOfNewEdition.isNewPublisher()) {
            publisherService.throwExceptionIfPublisherNameIsTaken(publisherOfNewEdition.getName(), newPublisherId);
            editionToUpdate.setPublisher(publisherOfNewEdition.toPublisher(editionToUpdate));
        } else {
            if (!previousPublisherId.equals(newPublisherId)) {
                editionToUpdate.setPublisher(publisherService.readPublisherById(newPublisherId));
                throwExceptionIfBookEditionAlreadyExist(editionToUpdate);
            }
        }

        newEdition.getBookCopiesToRemove().stream()
                .map(BookEditionReqBookCopyDTO::getId)
                .forEach(bookCopyIdToRemove -> {
                    editionToUpdate.getBookCopies()
                            .remove(copyRepository
                                    .findBookCopyById(bookCopyIdToRemove)
                                    .orElseThrow(() -> new IllegalArgumentException("Book copy with given id doesnt exist")));
                    copyRepository.deleteBookCopyById(bookCopyIdToRemove);
                });

        newEdition.getBookCopies().forEach(bookCopy -> {
            if (bookCopy.isNewCopy())
                editionToUpdate.getBookCopies()
                        .add(new BookCopy(editionToUpdate));
        });

      repository.save(editionToUpdate);

        newEdition.getBookCopiesToRemove().stream()
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

    /*
    * Since method uses instance of existing object,
    * hibernate saves updates before calling save() method,
    * list of objects is used as a return type.
    * */
    private void throwExceptionIfBookEditionAlreadyExist(BookEdition editionToCheck) {
        List<Optional<BookEdition>> foundBookEdition = repository.readByBookAndPublicationDateAndPublisher(
                editionToCheck.getBook(),
                editionToCheck.getPublicationDate(),
                editionToCheck.getPublisher());
        foundBookEdition.forEach(foundEdition->{
            if(foundEdition.isPresent()){
                if(!foundEdition.get().getId().equals(editionToCheck.getId())){
                    throw new IllegalArgumentException("Book's edition with given publisher and date of publication already exists");
                }
            }
        });
    }
}
