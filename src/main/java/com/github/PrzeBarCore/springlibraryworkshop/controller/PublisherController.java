package com.github.PrzeBarCore.springlibraryworkshop.controller;

import com.github.PrzeBarCore.springlibraryworkshop.service.PublisherService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/publisher")
public class PublisherController {
    private final PublisherService service;

    public PublisherController(PublisherService service) {
        this.service = service;
    }
}
