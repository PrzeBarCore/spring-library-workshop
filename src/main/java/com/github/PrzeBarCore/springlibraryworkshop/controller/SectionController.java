package com.github.PrzeBarCore.springlibraryworkshop.controller;


import com.github.PrzeBarCore.springlibraryworkshop.model.projection.BookSectionsRespSectionDTO;
import com.github.PrzeBarCore.springlibraryworkshop.model.projection.SectionReqRespSectionDTO;
import com.github.PrzeBarCore.springlibraryworkshop.service.SectionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/sections")
class SectionController {
    private final SectionService service;
    private static final Logger logger= LoggerFactory.getLogger(SectionController.class);

    SectionController(final SectionService service) {
        this.service = service;
    }

    @GetMapping
    String readAllSections(){
        return "sections";
    }

    @GetMapping("/{id}")
    String readSection(@PathVariable int id, Model model){
        model.addAttribute("section",service.getSectionReadModelById(id));
        return "sectionDisplayForm";
    }

    @GetMapping("/update/{id}")
    String getSectionUpdateForm(@PathVariable int id, Model model){
        model.addAttribute("sectionToUpdate",service.getSectionReadModelById(id));
        return "sectionUpdateForm";
    }

    @PostMapping("/update/{id}")
    String updateSection(@PathVariable int id, Model model, @ModelAttribute SectionReqRespSectionDTO toUpdate){
        model.addAttribute("section",service.updateSection(toUpdate));
        return "sectionDisplayForm";
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
