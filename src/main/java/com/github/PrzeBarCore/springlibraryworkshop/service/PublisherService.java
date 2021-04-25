package com.github.PrzeBarCore.springlibraryworkshop.service;

import com.github.PrzeBarCore.springlibraryworkshop.dao.PublisherRepository;
import com.github.PrzeBarCore.springlibraryworkshop.model.Publisher;
import com.github.PrzeBarCore.springlibraryworkshop.model.projection.BookPublishersRespPublisherDTO;
import com.github.PrzeBarCore.springlibraryworkshop.model.projection.PublisherRespPublisherDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PublisherService {
    private final PublisherRepository repository;
    private final Logger logger= LoggerFactory.getLogger(PublisherService.class);
    public PublisherService(final PublisherRepository repository) {
        this.repository = repository;
    }

     Publisher readPublisherById(int id){
         return repository.findById(id)
                 .orElseThrow(()->new IllegalArgumentException("Publisher with given ID doesn't exist!"));
    }

    public Page<BookPublishersRespPublisherDTO> readAllPublishers(Pageable pageable) {
        Page<Publisher> result= repository.findAll(pageable);

        if(pageable.getPageNumber()>=result.getTotalPages()){
            result=repository.findAll(pageable.first());
        }

        return result.map(BookPublishersRespPublisherDTO::new);
    }

    public PublisherRespPublisherDTO getPublisherReadModelById(int id) {
        return repository.findById(id)
                .map(PublisherRespPublisherDTO::fromPublisher)
                .orElseThrow(()->new IllegalArgumentException("Publisher with given ID doesn't exist!"));
    }

     void deletePublisherIfBookEditionsIsEmpty(Integer id) {
        Publisher publisher=readPublisherById(id);
        if(publisher.getBookEditions().isEmpty()){
            repository.deletePublisherById(id);
            logger.info("Deleting publisher");
        }
    }

    public PublisherRespPublisherDTO updatePublisher(PublisherRespPublisherDTO publisherToUpdate) {
        Publisher publisher=readPublisherById(publisherToUpdate.getId());
        repository.save(publisherToUpdate.updatePublisherFromDTO(publisher));
        return PublisherRespPublisherDTO.fromPublisher(publisher);
    }
}
