package com.github.PrzeBarCore.springlibraryworkshop.model.projection;

import com.github.PrzeBarCore.springlibraryworkshop.model.BookCopy;

import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class BookRespBookCopyDTO {
    private int id;
        private String state;
        private String borrowedUntil;
        private String reservedUntil;

        public BookRespBookCopyDTO(BookCopy source){
            this.id=source.getId();
            int stateOfBookCopy=source.getState();
            if(stateOfBookCopy==0){
                this.state="Available";
            } else if(stateOfBookCopy==1){
                this.state="Borrowed";
            } else{
                this.state="Reserved";
            }
            this.borrowedUntil=Optional.ofNullable(
                    source.getBorrowedUntil())
                    .map(date -> date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                    .orElse("");
            this.reservedUntil=Optional.ofNullable(
                    source.getReservedUntil())
                    .map(date -> date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                    .orElse("");
        }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getBorrowedUntil() {
            return borrowedUntil;
        }

        public void setBorrowedUntil(String borrowedUntil) {
            this.borrowedUntil = borrowedUntil;
        }

        public String getReservedUntil() {
            return reservedUntil;
        }

        public void setReservedUntil(String reservedUntil) {
            this.reservedUntil = reservedUntil;
        }
}


