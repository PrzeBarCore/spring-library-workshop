package com.github.PrzeBarCore.springlibraryworkshop.model.projection;
import com.github.PrzeBarCore.springlibraryworkshop.model.Author;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import static com.github.PrzeBarCore.springlibraryworkshop.utils.EntitiesStringsPatterns.forAuthorLastName;
import static com.github.PrzeBarCore.springlibraryworkshop.utils.EntitiesStringsPatterns.forAuthorName;

public class BookAuthorsRespAuthorDTO {
    @NotNull(message = "Author's id cannot be null")
    private Integer id;

    @NotEmpty(message = "Author's name cannot be empty")
    @Size(min = 1, max = 40)
    @Pattern(regexp = forAuthorName, message = "Author's name is invalid")
    private String name;

    @Size(max = 40)
    @Pattern(regexp = forAuthorLastName, message = "Author's last name is invalid")
    private String lastName;

    public BookAuthorsRespAuthorDTO(Author source){
        this.id=source.getId();
        this.name=source.getName();
        this.lastName= source.getLastName();
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
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
