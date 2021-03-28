package com.github.PrzeBarCore.springlibraryworkshop.service;

import com.github.PrzeBarCore.springlibraryworkshop.model.Publisher;
import com.github.PrzeBarCore.springlibraryworkshop.model.PublisherRepository;
import org.springframework.stereotype.Service;

@Service
public class PublisherService {
    private final PublisherRepository repository;

    public PublisherService(final PublisherRepository repository) {
        this.repository = repository;
    }

     Publisher findPublisherById(int id){
        Publisher result=repository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("Publisher with given ID doesn't exist!"));
        return result;
    }

}
