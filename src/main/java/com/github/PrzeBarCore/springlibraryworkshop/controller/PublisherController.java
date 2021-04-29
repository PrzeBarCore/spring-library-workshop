package com.github.PrzeBarCore.springlibraryworkshop.controller;

import com.github.PrzeBarCore.springlibraryworkshop.model.projection.BookPublishersRespPublisherDTO;
import com.github.PrzeBarCore.springlibraryworkshop.model.projection.PublisherReqRespPublisherDTO;
import com.github.PrzeBarCore.springlibraryworkshop.service.PublisherService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/publishers")
class PublisherController {
    private final PublisherService service;

     PublisherController(final PublisherService service) {
        this.service = service;
    }

    @GetMapping
    String readAllPublishers(){
        return "publishers";
    }

    @GetMapping("/{id}")
    String readPublisher(@PathVariable int id, Model model){

        model.addAttribute("publisher",service.getPublisherReadModelById(id));
        return "publisherDisplay";
    }
    @GetMapping("/update/{id}")
    String getPublisherUpdateForm(@PathVariable int id, Model model){

        model.addAttribute("publisherToUpdate",service.getPublisherReadModelById(id));
        return "publisherUpdateForm";
    }

    @PostMapping("/update/{id}")
    String updatePublisher(@ModelAttribute("publisherToUpdate") @Valid PublisherReqRespPublisherDTO toUpdate,
                           BindingResult bindingResult,
                           @PathVariable int id, Model model){
        if (bindingResult.hasErrors()){
            model.addAttribute("publisherToUpdate",toUpdate);
            model.addAttribute(bindingResult);
            return "publisherUpdateForm";
        }
        model.addAttribute("publisher",service.updatePublisher(toUpdate));
        return "publisherDisplay";
    }

    @ModelAttribute
    void getAllPublishers(@PageableDefault(size=10) Pageable pageable, Model model) {

        Page<BookPublishersRespPublisherDTO> result = service.readAllPublishers(pageable);
        int pageNumber = pageable.getPageNumber();
        int totalPages = result.getTotalPages();

        if (pageNumber >= totalPages) {
            model.addAttribute("pageNumber", 0);
        } else {
            model.addAttribute("pageNumber", pageNumber);
        }
        model.addAttribute("pageSize", pageable.getPageSize());
        model.addAttribute("publishers", result);
        model.addAttribute("totalPages", totalPages);
    }
}
