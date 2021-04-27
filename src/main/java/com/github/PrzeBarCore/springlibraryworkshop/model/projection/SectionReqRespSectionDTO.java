package com.github.PrzeBarCore.springlibraryworkshop.model.projection;

import com.github.PrzeBarCore.springlibraryworkshop.model.Book;
import com.github.PrzeBarCore.springlibraryworkshop.model.Section;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SectionReqRespSectionDTO {
    @NotNull(message = "Section's id cannot be blank")
    private Integer id;
    @NotBlank(message = "Section's name cannot be blank")
    private String name;
    private List<AuthorSectionPublisherRespBookDTO> books;

    public SectionReqRespSectionDTO(){}

    public SectionReqRespSectionDTO(Section section){
        this.id=section.getId();
        this.name=section.getName();
        this.books=section.getBooks()
                .stream()
                .sorted(Comparator.comparing(Book::getTitle))
                .map(AuthorSectionPublisherRespBookDTO::new)
                .collect(Collectors.toList());
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name.trim();
    }

    public List<AuthorSectionPublisherRespBookDTO> getBooks() {
        return books;
    }

    public void setBooks(List<AuthorSectionPublisherRespBookDTO> books) {
        this.books = books;
    }

    public Section updateSectionFromDTO(Section section){
      section.setName(this.name);
      return section;
    }
}
