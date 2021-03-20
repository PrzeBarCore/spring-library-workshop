package com.github.PrzeBarCore.springlibraryworkshop.service;

import com.github.PrzeBarCore.springlibraryworkshop.model.Author;
import com.github.PrzeBarCore.springlibraryworkshop.model.AuthorRepository;
import com.github.PrzeBarCore.springlibraryworkshop.model.Book;
import com.github.PrzeBarCore.springlibraryworkshop.model.projection.Book.BookAuthorWriteModel;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {
    private AuthorRepository repository;

    AuthorService(final AuthorRepository repository) {
        this.repository = repository;
    }


    Author createAuthor(BookAuthorWriteModel source, Book book) {
        Author author= source.toAuthor(book);
        Author result = repository.save(author);
        return result;
    }

    //Trzeba będzie dodac wyjatek
    Author readAuthor(Integer id){
        return repository.findById(id).orElseThrow(()->new IllegalArgumentException("Incorrect author Id"));
    }

//    public AuthorReadModel readAuthor(Integer id) throws IOException {
//        AuthorReadModel result = repository.findById(id)
//                .map(AuthorReadModel::new)
//                .orElseThrow(() -> new IllegalArgumentException("Incorrect author Id"));
//
//        return result;
//    }

//    public List<BookAuthorModel> findAll(Optional<Integer> page, Optional<Integer> numberOfPositions) {
//
//        Pageable pageable = PageRequest.of(page.orElse(0), numberOfPositions.orElse(10));
//        return repository.findAll(pageable).stream().map(BookAuthorModel::new).collect(toList());
//    }
}
