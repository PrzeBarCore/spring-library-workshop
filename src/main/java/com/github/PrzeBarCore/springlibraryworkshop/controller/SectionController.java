package com.github.PrzeBarCore.springlibraryworkshop.controller;



import com.github.PrzeBarCore.springlibraryworkshop.service.SectionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sections")
class SectionController {
    private final SectionService service;
    private static final Logger logger= LoggerFactory.getLogger(SectionController.class);

    SectionController(final SectionService service) {
        this.service = service;
    }



}
