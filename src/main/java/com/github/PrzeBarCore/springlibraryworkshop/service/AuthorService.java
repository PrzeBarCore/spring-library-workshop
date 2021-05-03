package com.github.PrzeBarCore.springlibraryworkshop.service;

import com.github.PrzeBarCore.springlibraryworkshop.dao.AuthorRepository;
import com.github.PrzeBarCore.springlibraryworkshop.model.Author;
import com.github.PrzeBarCore.springlibraryworkshop.model.Book;
import com.github.PrzeBarCore.springlibraryworkshop.model.projection.AuthorReqRespAuthorDTO;
import com.github.PrzeBarCore.springlibraryworkshop.model.projection.BookAuthorsRespAuthorDTO;
import com.github.PrzeBarCore.springlibraryworkshop.model.projection.BookReqAuthorDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {
    private final AuthorRepository repository;
    private final Logger logger= LoggerFactory.getLogger(AuthorService.class);
    AuthorService(final AuthorRepository repository) {
        this.repository = repository;
    }


    Author createAuthor(BookReqAuthorDTO source, Book book) {
        Author author = source.toAuthor(book);
        return repository.save(author);
    }

    public Page<BookAuthorsRespAuthorDTO> readAllAuthors(Pageable pageable) {
        Page<Author> result = repository.findAll(pageable);

        if (pageable.getPageNumber() >= result.getTotalPages()) {
            result = repository.findAll(pageable.first());
        }
        return result.map(BookAuthorsRespAuthorDTO::new);
    }

    Author readAuthorById(int id) {
        return repository.findById(id).
                orElseThrow(() -> new IllegalArgumentException("Author with given ID doesn't exist!"));
    }

    public AuthorReqRespAuthorDTO getAuthorReadWriteModelById(Integer id) {
        return new AuthorReqRespAuthorDTO(readAuthorById(id));
    }

    void deleteAuthorIfListOfBooksIsEmpty(Integer id) {
        Author author= readAuthorById(id);
       if(author.getBooks().isEmpty()){
           repository.deleteById(id);
            logger.info("deleting author");
       }
    }

    public AuthorReqRespAuthorDTO updateAuthor(AuthorReqRespAuthorDTO authorToUpdate) {
        Author author = readAuthorById(authorToUpdate.getId());
        repository.save(authorToUpdate.updateAuthorFromDTO(author));
        return new AuthorReqRespAuthorDTO(author);
    }
}
