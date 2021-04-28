package com.github.PrzeBarCore.springlibraryworkshop.model.projection;

import com.github.PrzeBarCore.springlibraryworkshop.model.Book;
import com.github.PrzeBarCore.springlibraryworkshop.model.Section;
import com.github.PrzeBarCore.springlibraryworkshop.utils.EntitiesStringsPatterns;

import javax.validation.constraints.NotNull;

public class BookReqSectionDTO {
    @NotNull(message = "Section's id cannot be null")
    private Integer id;

    private Boolean isNewSection;
    private String name;

    public BookReqSectionDTO() {
        this.id = 0;
        this.isNewSection = false;
    }

    public BookReqSectionDTO(Section source) {
        this.id = source.getId();
        this.name = source.getName();
        this.isNewSection = false;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isNewSection() {
        return isNewSection;
    }

    public void setNewSection(boolean newSection) {
        this.isNewSection = newSection;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Section toSection(Book book) {
        String trimmedName = name.trim();
        if(isNewSection && trimmedName.isBlank()) {
            throw new IllegalArgumentException("Section's name cannot be blank");
        }
        if(!trimmedName.matches(EntitiesStringsPatterns.forSectionName)){
            throw new IllegalArgumentException("Section's name is invalid ");
        }
        return new Section(trimmedName, book);
    }
}
