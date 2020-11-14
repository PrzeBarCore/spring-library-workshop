package com.github.PrzeBarCore.springlibraryworkshop.model.projection;

import com.github.PrzeBarCore.springlibraryworkshop.model.Section;

public class BookSectionReadModel {
    private String name;

    public BookSectionReadModel(final Section source) {
        this.name = source.getName();
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }
}
