package com.github.PrzeBarCore.springlibraryworkshop.service;

import com.github.PrzeBarCore.springlibraryworkshop.dao.AuthorRepository;
import com.github.PrzeBarCore.springlibraryworkshop.model.Author;
import com.github.PrzeBarCore.springlibraryworkshop.model.Book;
import com.github.PrzeBarCore.springlibraryworkshop.model.projection.AuthorReqRespAuthorDTO;
import com.github.PrzeBarCore.springlibraryworkshop.model.projection.BookAuthorsRespAuthorDTO;
import com.github.PrzeBarCore.springlibraryworkshop.model.projection.BookReqAuthorDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {
    private final AuthorRepository repository;

    AuthorService(final AuthorRepository repository) {
        this.repository = repository;
    }


    public Author createAuthor(BookReqAuthorDTO source, Book book) {
        Author author = source.toAuthor(book);
        Author result = repository.save(author);
        return result;
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

    public AuthorReqRespAuthorDTO getAuthorReadModelById(Integer id) {
        return repository.findById(id)
                .map(AuthorReqRespAuthorDTO::fromAuthor)
                .orElseThrow(() -> new IllegalArgumentException("Author with given ID doesn't exist!"));

    }

    public AuthorReqRespAuthorDTO getAuthorWriteModelById(Integer id) {
        AuthorReqRespAuthorDTO result = repository.findById(id).map(AuthorReqRespAuthorDTO::fromAuthor)
                .orElseThrow(() -> new IllegalArgumentException("Author with given ID doesn't exist!"));
        return result;

    }

    public void deleteAuthorIfBooksIsEmpty(Integer id) {
        if (!repository.existsAuthorById(id)) {
            throw new IllegalArgumentException("Author with given ID doesn't exist!");
        }
        repository.deleteAuthorByIdAndBooksEmpty(id);
    }

    public AuthorReqRespAuthorDTO updateAuthor(AuthorReqRespAuthorDTO authorToUpdate) {
        Author author = readAuthorById(authorToUpdate.getId());
        repository.save(authorToUpdate.updateAuthorFromDTO(author));
        return AuthorReqRespAuthorDTO.fromAuthor(author);
    }
}
