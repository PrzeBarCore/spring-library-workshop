package com.github.PrzeBarCore.springlibraryworkshop.model.projection;

import com.github.PrzeBarCore.springlibraryworkshop.model.Book;
import com.github.PrzeBarCore.springlibraryworkshop.model.Section;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SectionReqRespSectionDTO {
    private int id;
    private String name;
    private List<AuthorSectionPublisherRespBookDTO> books;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public static SectionReqRespSectionDTO fromSection(Section section){
        var sectionDTO= new SectionReqRespSectionDTO();
        sectionDTO.id=section.getId();
        sectionDTO.name = section.getName();
        sectionDTO.books = section.getBooks()
                .stream()
                .sorted(Comparator.comparing(Book::getTitle))
                .map(AuthorSectionPublisherRespBookDTO::new)
                .collect(Collectors.toList());
        return sectionDTO;
    }

    public Section updateSectionFromDTO(Section section){
      section.setName(this.name);
      return section;
    }
}
