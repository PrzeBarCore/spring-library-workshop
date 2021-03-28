package com.github.PrzeBarCore.springlibraryworkshop.model.projection;

import com.github.PrzeBarCore.springlibraryworkshop.model.BookCopy;

public class BookBookCopyReadModel {

    private String state;
    private int publicationDate;
    private BookCopyPublisherReadModel publisher;

     BookBookCopyReadModel(BookCopy source) {
         int sourceState=source.getState();
        if(sourceState==0) {
            this.state = "Available";
        } else if(sourceState==1){
            this.state = "Unavailable";
        } else{
            this.state = "Ordered";
        }
        this.publicationDate = source.getPublicationDate();
        this.publisher = new BookCopyPublisherReadModel(source.getPublisher());
    }

    public String getState() {
        return state;
    }

    public int getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(int publicationDate) {
        this.publicationDate = publicationDate;
    }

    public BookCopyPublisherReadModel getPublisher() {
        return publisher;
    }

    public void setPublisher(BookCopyPublisherReadModel publisher) {
        this.publisher = publisher;
    }
}

