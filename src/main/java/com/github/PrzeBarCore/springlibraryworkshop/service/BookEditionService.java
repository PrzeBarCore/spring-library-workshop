package com.github.PrzeBarCore.springlibraryworkshop.service;

import com.github.PrzeBarCore.springlibraryworkshop.dao.BookCopyRepository;
import com.github.PrzeBarCore.springlibraryworkshop.dao.BookEditionRepository;
import com.github.PrzeBarCore.springlibraryworkshop.model.Book;
import com.github.PrzeBarCore.springlibraryworkshop.model.BookEdition;
import com.github.PrzeBarCore.springlibraryworkshop.model.projection.BookEditionReqBookCopyDTO;
import com.github.PrzeBarCore.springlibraryworkshop.model.projection.BookEditionReqBookEditionDTO;
import com.github.PrzeBarCore.springlibraryworkshop.model.projection.BookReqBookEditionDTO;
import com.github.PrzeBarCore.springlibraryworkshop.utils.BookState;
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
     public BookEdition createBookEdition(BookReqBookEditionDTO toCreate, Book book, boolean isBookNew) {
        BookEdition bookEdition = toCreate.toBookEdition(book);

        bookEdition.setPublisher(publisherService
                .createPublisher(toCreate.getPublisher(),bookEdition));

        if (!isBookNew && !toCreate.getPublisher().isNewPublisher()) {
            throwExceptionIfBookEditionAlreadyExist(bookEdition);
            repository.save(bookEdition);
        }
        return bookEdition;
    }

    BookEdition findBookEditionById(int id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Book editions with given id doesn't exist"));
    }

    public BookEditionReqBookEditionDTO getBookEditionWriteModelById(int id) {
        return new BookEditionReqBookEditionDTO(findBookEditionById(id));
    }

    @Transactional
    public void updateBookEdition(BookEditionReqBookEditionDTO toUpdate, int id) {
        toUpdate.verify();
        BookEdition editionToUpdate = findBookEditionById(id);
        int currentPublisherId=editionToUpdate.getPublisher().getId();

        editionToUpdate.setPublicationDate(toUpdate.getPublicationDate());

        editionToUpdate.setPublisher(publisherService
                .createPublisher(toUpdate.getPublisher(),editionToUpdate));

        if (!toUpdate.getPublisher().isNewPublisher()) {
            throwExceptionIfBookEditionAlreadyExist(editionToUpdate);
        }

        toUpdate.getBookCopies()
                .stream()
                .filter(BookEditionReqBookCopyDTO::isNewCopy)
                .map(copy-> copy.toBookCopy(editionToUpdate))
                .forEach(copy->editionToUpdate.getBookCopies().add(copy));

        toUpdate.getBookCopiesToRemove().stream()
                .map(BookEditionReqBookCopyDTO::getId)
                .forEach(copyId -> {
                    editionToUpdate.getBookCopies()
                            .remove(copyRepository
                                    .findById(copyId)
                                    .orElseThrow(() -> new IllegalArgumentException("Book copy with given id doesnt exist")));
                    copyRepository.deleteById(copyId);
                });

      repository.save(editionToUpdate);
      publisherService.deletePublisherIfBookEditionsIsEmpty(currentPublisherId);
    }

    @Transactional
    public void deleteBookEditionById(int id) {
        BookEdition toDelete = findBookEditionById(id);
        int publisherId = toDelete.getPublisher().getId();

        toDelete.getBookCopies().forEach(copy -> {
            if (copy.getState() != BookState.AVAILABLE)
                throw new IllegalArgumentException("Cannot delete book's edition when not every copy is available");
        });
        toDelete.getPublisher().getBookEditions().remove(toDelete);

        repository.deleteById(id);
        publisherService.deletePublisherIfBookEditionsIsEmpty(publisherId);
        logger.info("deleted book edition");
    }

    /*
    * Since method uses instance of existing object,
    * hibernate saves updates before calling save() method,
    * list of objects is used as a return type.
    * */
    private void throwExceptionIfBookEditionAlreadyExist(BookEdition toCheck) {
        List<Optional<BookEdition>> foundBookEdition = repository.readByBookAndPublicationDateAndPublisher(
                toCheck.getBook(),
                toCheck.getPublicationDate(),
                toCheck.getPublisher());
        foundBookEdition.forEach(foundEdition->{
            if(foundEdition.isPresent()){
                if(!foundEdition.get().getId().equals(toCheck.getId())){
                    throw new IllegalArgumentException("Book's edition with given publisher and date of publication already exists");
                }
            }
        });
    }
}
