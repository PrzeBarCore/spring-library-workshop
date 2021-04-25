package com.github.PrzeBarCore.springlibraryworkshop.model.projection;

import com.github.PrzeBarCore.springlibraryworkshop.model.Book;
import com.github.PrzeBarCore.springlibraryworkshop.model.Section;

public class BookReqSectionDTO {
    private Integer id;
    private String name;
    private boolean isNewSection;

    public BookReqSectionDTO() {
        this.id=0;
        this.isNewSection =false;
    }

    public BookReqSectionDTO(Integer id, String name, boolean isNewSection) {
        this.id = id;
        this.name = name;
        this.isNewSection = isNewSection;
    }

    public boolean isNewSection() { return isNewSection; }

    public void setNewSection(boolean newSection) { this.isNewSection = newSection; }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Section toSection(Book book){
        return new Section(name, book);
    }

    public static BookReqSectionDTO fromSection(Section source) {
        var sectionWriteModel= new BookReqSectionDTO();
        sectionWriteModel.id=source.getId();
        sectionWriteModel.name= source.getName();
        sectionWriteModel.isNewSection=false;
        return sectionWriteModel;
    }
}
