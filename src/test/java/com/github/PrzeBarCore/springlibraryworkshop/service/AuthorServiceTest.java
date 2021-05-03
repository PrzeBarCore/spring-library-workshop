package com.github.PrzeBarCore.springlibraryworkshop.service;

import com.github.PrzeBarCore.springlibraryworkshop.dao.AuthorRepository;
import com.github.PrzeBarCore.springlibraryworkshop.model.Author;
import com.github.PrzeBarCore.springlibraryworkshop.model.Book;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AuthorServiceTest {

    @Test
    @DisplayName("Should throw IllegalArgumentException when no fitting author found")
    void readAuthorById_noAuthorFound_throwIllegalArgumentException() {
        //given
        var mockRepository= mock(AuthorRepository.class);
        when(mockRepository.findById(anyInt())).thenReturn(Optional.empty());
        //System under test
        var toTest= new AuthorService(mockRepository);
        //when
        var exception= Assertions.catchThrowable(()->toTest.readAuthorById(2));
        //then
        Assertions.assertThat(exception)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("with given ID");
    }
    @Test
    @DisplayName("Should return author instance")
    void readAuthorById_authorFound_returnsAuthorInstance() {
        //given
        var mockRepository= mock(AuthorRepository.class);
        when(mockRepository.findById(anyInt())).thenReturn(Optional.of(new Author()));
        //System under test
        var toTest= new AuthorService(mockRepository);
        //when
        var result= toTest.readAuthorById(2);
        //then
        Assertions.assertThat(result).isInstanceOf(Author.class);
    }

    @Test
    @DisplayName("Should return first page if demanded page number bigger than total pages")
    void readAllAuthors_incorrectPageNumber_returnsFirstPage(){
        //given
        inMemoryRepository.save(new Author());
        inMemoryRepository.save(new Author());
        //System under test
        var toTest= new AuthorService(inMemoryRepository);
        //when
        Page<?> result=toTest.readAllAuthors(PageRequest.of(3,3));
        //then
        Assertions.assertThat(result.isFirst()).isTrue();
    }

    @Test
    @DisplayName("Should return expected page")
    void readAllAuthor_correctPageNumber_returnsExpectedPage(){
        //given
        inMemoryRepository.save(new Author());
        inMemoryRepository.save(new Author());
        //System under test
        var toTest= new AuthorService(inMemoryRepository);
        //when
        Page<?> result=toTest.readAllAuthors(PageRequest.of(1,1));
        //then
        Assertions.assertThat(result.getNumber()).isEqualTo(1);
    }


    @Test
    @DisplayName("Should delete author")
    void deleteAuthorIfListOfBookIsEmpty_authorWithoutBooks_deletesAuthor(){
        //given
        inMemoryRepository.save(new Author());
        //System under test
        var toTest= new AuthorService(inMemoryRepository);
        //when
        toTest.deleteAuthorIfListOfBooksIsEmpty(1);
        //then
        Assertions.assertThat(inMemoryRepository.findById(1)).isNull();
    }

    @Test
    @DisplayName("Shouldn't delete author")
    void deleteAuthorIfListOfBookIsEmpty_authorWithBooks_doesntDeleteAuthor(){
        //given
        var auth=new Author();
        auth.getBooks().add(new Book());
        inMemoryRepository.save(auth);
        //System under test
        var toTest= new AuthorService(inMemoryRepository);
        //when
        toTest.deleteAuthorIfListOfBooksIsEmpty(1);
        //then
        Assertions.assertThat(inMemoryRepository.findById(1))
                .isInstanceOf(Author.class);
    }

    private final AuthorRepository inMemoryRepository=new AuthorRepository() {
        private final Map<Integer, Author> map= new HashMap<>();
        private int index=0;

        /*
        Real repo in case of pageable with page number which is too big to return
        anything from the list, returns empty page with correct fields (like total pages)
        instead.
         */
        @Override
        public Page<Author> findAll(Pageable page) {
            var listSize=map.size();
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
            return new PageImpl<Author>(new ArrayList<>(map.values()), page, map.size());
        }

        @Override
        public Optional<Author> findById(Integer id) {
            return Optional.ofNullable(map.get(id));
        }


        @Override
        public void deleteById(Integer id) {
            map.remove(id);
        }

        @Override
        public Author save(Author toCreate) {
            if(toCreate.getId()==null || toCreate.getId()<=0){
                toCreate.setId(++index);
            }
            map.put(toCreate.getId(),toCreate);
            return toCreate;
        }
    };

}