package com.github.PrzeBarCore.springlibraryworkshop.model.projection;

import com.github.PrzeBarCore.springlibraryworkshop.model.Section;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import static com.github.PrzeBarCore.springlibraryworkshop.utils.EntitiesStringsPatterns.forSectionName;

public class BookSectionsRespSectionDTO {
    @NotNull(message = "Section's id cannot be null")
    Integer id;

    @NotBlank(message = "Section's name cannot be blank")
    @Pattern(regexp = forSectionName, message = "Section's name is invalid")
    String name;

    public BookSectionsRespSectionDTO(Section source){
        this.id= source.getId();
        this.name= source.getName();
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
}
