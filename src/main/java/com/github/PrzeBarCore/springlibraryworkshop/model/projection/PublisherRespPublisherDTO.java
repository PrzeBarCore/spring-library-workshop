package com.github.PrzeBarCore.springlibraryworkshop.model.projection;

import com.github.PrzeBarCore.springlibraryworkshop.model.Book;
import com.github.PrzeBarCore.springlibraryworkshop.model.Publisher;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class PublisherRespPublisherDTO {
    private int id;
    private String name;
    private List<AuthorSectionPublisherRespBookDTO> books;



    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<AuthorSectionPublisherRespBookDTO> getBooks() {
        return books;
    }

    public void setBooks(List<AuthorSectionPublisherRespBookDTO> books) {
        this.books = books;
    }

    public static PublisherRespPublisherDTO fromPublisher(Publisher publisher){
        var publisherDTO= new PublisherRespPublisherDTO();
        publisherDTO.id=publisher.getId();
        publisherDTO.name = publisher.getName();
        publisherDTO.books = publisher.getBookEditions()
                .stream()
                .map(bookEdition -> bookEdition.getBook())
                .sorted(Comparator.comparing(Book::getTitle))
                .map(AuthorSectionPublisherRespBookDTO::new)
                .collect(Collectors.toList());
        return publisherDTO;
    }

    public Publisher updatePublisherFromDTO(Publisher publisher){
        publisher.setName(this.name);
        return publisher;
    }
}
