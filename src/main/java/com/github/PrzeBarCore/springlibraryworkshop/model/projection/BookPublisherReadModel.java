package com.github.PrzeBarCore.springlibraryworkshop.model.projection;

import com.github.PrzeBarCore.springlibraryworkshop.model.Publisher;

public class BookPublisherReadModel {
        private String name;

        public BookPublisherReadModel(final Publisher source) {
            this.name = source.getName();
        }

        public String getName() {
            return name;
        }

        public void setName(final String name) {
            this.name = name;
        }
}
