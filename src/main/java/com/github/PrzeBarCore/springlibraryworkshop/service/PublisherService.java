package com.github.PrzeBarCore.springlibraryworkshop.service;

import com.github.PrzeBarCore.springlibraryworkshop.model.Publisher;
import com.github.PrzeBarCore.springlibraryworkshop.dao.PublisherRepository;
import com.github.PrzeBarCore.springlibraryworkshop.model.projection.BookPublishersRespPublisherDTO;
import com.github.PrzeBarCore.springlibraryworkshop.model.projection.PublisherRespPublisherDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PublisherService {
    private final PublisherRepository repository;

    public PublisherService(final PublisherRepository repository) {
        this.repository = repository;
    }

     Publisher readPublisherById(int id){
        Publisher result=repository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("Publisher with given ID doesn't exist!"));
        return result;
    }

    public Page<BookPublishersRespPublisherDTO> readAllPublishers(Pageable pageable) {
        Page<Publisher> result= repository.findAll(pageable);

        if(pageable.getPageNumber()>=result.getTotalPages()){
            result=repository.findAll(pageable.first());
        }

        return result.map(BookPublishersRespPublisherDTO::new);
    }

    public PublisherRespPublisherDTO getPublisherReadModelById(int id) {
        PublisherRespPublisherDTO result=repository.findById(id)
                .map(PublisherRespPublisherDTO::new)
                .orElseThrow(()->new IllegalArgumentException("Publisher with given ID doesn't exist!"));
        return result;
    }
}
