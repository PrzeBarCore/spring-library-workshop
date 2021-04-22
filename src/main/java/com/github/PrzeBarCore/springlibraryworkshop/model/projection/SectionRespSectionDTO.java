package com.github.PrzeBarCore.springlibraryworkshop.model.projection;

import com.github.PrzeBarCore.springlibraryworkshop.model.Section;

import java.util.List;
import java.util.stream.Collectors;

public class SectionRespSectionDTO {
    private String name;
    private List<AuthorSectionPublisherRespBookDTO> books;

    public SectionRespSectionDTO(Section source) {
        this.name = source.getName();
        this.books = source.getBooks()
                .stream()
                .map(AuthorSectionPublisherRespBookDTO::new)
                .collect(Collectors.toList());
    }

    public String getName() {
        return name;
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
}
