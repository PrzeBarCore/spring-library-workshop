package com.github.PrzeBarCore.springlibraryworkshop.service;

import com.github.PrzeBarCore.springlibraryworkshop.dao.PublisherRepository;
import com.github.PrzeBarCore.springlibraryworkshop.model.BookEdition;
import com.github.PrzeBarCore.springlibraryworkshop.model.Publisher;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.*;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PublisherServiceTest {
    @Test
    @DisplayName("Should throw IllegalArgumentException when no fitting section found")
    void readSectionById_noPublisherFound_throwIllegalArgumentException() {
        //given
        var mockRepository= mock(PublisherRepository.class);
        when(mockRepository.findById(anyInt())).thenReturn(Optional.empty());
        //System under test
        var toTest= new PublisherService(mockRepository);
        //when
        var exception= Assertions.catchThrowable(()->toTest.readPublisherById(2));
        //then
        Assertions.assertThat(exception)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("with given ID");
    }
    @Test
    @DisplayName("Should return Section instance")
    void readSectionById_publisherFound_throwIllegalArgumentException() {
        //given
        var mockRepository= mock(PublisherRepository.class);
        when(mockRepository.findById(anyInt())).thenReturn(Optional.of(new Publisher()));
        //System under test
        var toTest= new PublisherService(mockRepository);
        //when
        var result= toTest.readPublisherById(2);
        //then
        Assertions.assertThat(result).isInstanceOf(Publisher.class);
    }


    @Test
    @DisplayName("Shouldn delete section")
    void deletePublisherIfBookEditionsIsEmpty_publisherWithoutBooks_deletesPublisher(){
        //given
        inMemoryRepository.save(new Publisher());
        //System under test
        var toTest= new PublisherService(inMemoryRepository);
        //when
        toTest.deletePublisherIfBookEditionsIsEmpty(1);
        //then
        Assertions.assertThat(inMemoryRepository.findAll()).isEmpty();
    }

    @Test
    @DisplayName("Shouldn't delete section")
    void deleteSectionIfListOfBooksIsEmpty_sectionWithBooks_doesntDeleteSection(){
        //given
        var pub=new Publisher();
        pub.getBookEditions().add(new BookEdition());
        inMemoryRepository.save(pub);
        //System under test
        var toTest= new PublisherService(inMemoryRepository);
        //when
        toTest.deletePublisherIfBookEditionsIsEmpty(1);
        //then
        Assertions.assertThat(inMemoryRepository.findAll())
                .isNotEmpty();
    }

    @Test
    @DisplayName("Should throw exception when name is taken ")
    void throwExceptionIfSectionNameIsTaken_nameIsTaken_throwsIllegalArgumentException(){
        //given
        var pub1=new Publisher();
        pub1.setName("");
        var pub2=new Publisher();
        pub2.setName("");
        inMemoryRepository.save(pub1);
        inMemoryRepository.save(pub2);

        //System under test
        var toTest= new PublisherService(inMemoryRepository);

        //when
        var exception= Assertions.catchThrowable(
                ()-> toTest.throwExceptionIfPublisherNameIsTaken("",3));

        //then
        Assertions.assertThat(exception)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("name already exists");
    }

    private final PublisherRepository inMemoryRepository=new PublisherRepository() {
        private final Map<Integer, Publisher> map= new HashMap<>();
        private int index=0;


        @Override
        public List<Publisher> findAll() {
            return new ArrayList<>(map.values());
        }

        /*
        Real repo in case of pageable with page number which is too big to return
        anything from the list, returns empty page with correct fields (like total pages)
        instead.
         */
        @Override
        public Page<Publisher> findAll(Pageable page) {
            var listSize=findAll().size();
            var pageSize=page.getPageSize();
            var pageNum =page.getPageNumber();
            if(pageNum * pageSize >= listSize){
                if(listSize%pageSize!=0){
                    return new PageImpl<>(
                            new ArrayList<>(),
                            PageRequest.of(listSize / pageSize + 1, pageSize),
                            0);
                }
                return new PageImpl<>(
                        new ArrayList<>(),
                        PageRequest.of(listSize / pageSize, pageSize),
                        0);
            }
            return new PageImpl<>(findAll(), page, findAll().size());
        }

        @Override
        public Optional<Publisher> findById(Integer id) {
            return Optional.ofNullable(map.get(id));
        }

        @Override
        public Optional<Publisher> findByName(String trim) {
            return map.values()
                    .stream()
                    .filter(section -> section.getName().equals(trim))
                    .findAny();
        }
        @Override
        public void deleteById(Integer id) {
            map.remove(id);
        }

        @Override
        public Publisher save(Publisher toCreate) {
            if(toCreate.getId()==null || toCreate.getId()<=0){
                toCreate.setId(++index);
            }
            map.put(toCreate.getId(),toCreate);
            return toCreate;
        }
    };
}