package com.github.PrzeBarCore.springlibraryworkshop.controller;


import com.github.PrzeBarCore.springlibraryworkshop.model.projection.BookSectionsRespSectionDTO;
import com.github.PrzeBarCore.springlibraryworkshop.service.SectionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sections")
class SectionController {
    private final SectionService service;
    private static final Logger logger= LoggerFactory.getLogger(SectionController.class);

    SectionController(final SectionService service) {
        this.service = service;
    }


    @GetMapping("/{id}")
    String readSection(@PathVariable int id, Model model){
        model.addAttribute("section",service.getSectionReadModelById(id));
        return "sectionDisplay";
    }

    @GetMapping
    String readAllSections(){
        return "sections";
    }

    @ModelAttribute
    void getAllSections(@PageableDefault(size=10) Pageable pageable, Model model) {
        Page<BookSectionsRespSectionDTO> result = service.readAllSections(pageable);

        int pageNumber = pageable.getPageNumber();
        int totalPages = result.getTotalPages();

        if (pageNumber >= totalPages) {
            model.addAttribute("pageNumber", 0);
        } else {
            model.addAttribute("pageNumber", pageNumber);
        }
        model.addAttribute("pageSize", pageable.getPageSize());
        model.addAttribute("sections", result);
        model.addAttribute("totalPages", totalPages);
    }
}
