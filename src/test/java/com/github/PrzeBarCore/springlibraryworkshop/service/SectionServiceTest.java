package com.github.PrzeBarCore.springlibraryworkshop.service;

import com.github.PrzeBarCore.springlibraryworkshop.dao.SectionRepository;
import com.github.PrzeBarCore.springlibraryworkshop.model.Book;
import com.github.PrzeBarCore.springlibraryworkshop.model.Section;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.*;

import static org.mockito.Mockito.*;

class SectionServiceTest {
    @Test
    @DisplayName("Should throw IllegalArgumentException when no fitting section found")
    void readSectionById_noSectionFound_throwIllegalArgumentException() {
        //given
        var mockRepository= mock(SectionRepository.class);
        when(mockRepository.findById(anyInt())).thenReturn(Optional.empty());
        //System under test
        var toTest= new SectionService(mockRepository);
        //when
        var exception= Assertions.catchThrowable(()->toTest.readSectionById(2));
        //then
        Assertions.assertThat(exception)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("with given id");
    }
    @Test
    @DisplayName("Should return Section instance")
    void readSectionById_sectionFound_throwIllegalArgumentException() {
        //given
        var mockRepository= mock(SectionRepository.class);
        when(mockRepository.findById(anyInt())).thenReturn(Optional.of(new Section()));
        //System under test
        var toTest= new SectionService(mockRepository);
        //when
        var result= toTest.readSectionById(2);
        //then
        Assertions.assertThat(result).isInstanceOf(Section.class);
    }

    @Test
    @DisplayName("Should return first page if demanded page number bigger than total pages")
    void readAllSections_incorrectPageNumber_returnsFirstPage(){
        //given
        inMemoryRepository.save(new Section());
        inMemoryRepository.save(new Section());
        //System under test
        var toTest= new SectionService(inMemoryRepository);
        //when
        Page<?> result=toTest.readAllSections(PageRequest.of(3,3));
        //then
        Assertions.assertThat(result.isFirst()).isTrue();
    }

    @Test
    @DisplayName("Should return expected page")
    void readAllSections_correctPageNumber_returnsExpectedPage(){
        //given
        inMemoryRepository.save(new Section());
        inMemoryRepository.save(new Section());
        //System under test
        var toTest= new SectionService(inMemoryRepository);
        //when
        Page<?> result=toTest.readAllSections(PageRequest.of(1,1));
        //then
        Assertions.assertThat(result.getNumber()).isEqualTo(1);
    }

    @Test
    @DisplayName("Shouldn delete section")
    void deleteSectionIfListOfBooksIsEmpty_sectionWithoutBooks_deletesSection(){
        //given
        inMemoryRepository.save(new Section());
        //System under test
        var toTest= new SectionService(inMemoryRepository);
        //when
        toTest.deleteSectionIfListOfBooksIsEmpty(1);
        //then
        Assertions.assertThat(inMemoryRepository.findAll()).isEmpty();
    }

    @Test
    @DisplayName("Shouldn't delete section")
    void deleteSectionIfListOfBooksIsEmpty_sectionWithBooks_doesntDeleteSection(){
        //given
        var sec=new Section();
        sec.getBooks().add(new Book());
        inMemoryRepository.save(sec);
        //System under test
        var toTest= new SectionService(inMemoryRepository);
        //when
        toTest.deleteSectionIfListOfBooksIsEmpty(1);
        //then
        Assertions.assertThat(inMemoryRepository.findAll())
                .isNotEmpty();
    }

    @Test
    @DisplayName("Should throw exception when name is taken ")
    void throwExceptionIfSectionNameIsTaken_nameIsTaken_throwsIllegalArgumentException(){
        //given
        var sec1=new Section();
        sec1.setName("");
        var sec2=new Section();
        sec2.setName("");
        inMemoryRepository.save(sec1);
        inMemoryRepository.save(sec2);

        //System under test
        var toTest= new SectionService(inMemoryRepository);

        //when
        var exception= Assertions.catchThrowable(
                ()-> toTest.throwExceptionIfSectionNameIsTaken("",3));

        //then
        Assertions.assertThat(exception)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("name already exists");
    }

    private final SectionRepository inMemoryRepository=new SectionRepository() {
        private final Map<Integer, Section> map= new HashMap<>();
        private int index=0;
        @Override
        public List<Section> findAll() {
            return new ArrayList<>(map.values());
        }

        /*
        Real repo in case of pageable with page number which is too big to return
        anything from the list, returns empty page with correct fields (like total pages)
        instead.
         */
        @Override
        public Page<Section> findAll(Pageable page) {
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
        public Optional<Section> findById(Integer id) {
            return Optional.ofNullable(map.get(id));
        }

        @Override
        public Optional<Section> findByName(String trim) {
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
        public Section save(Section toCreate) {
            if(toCreate.getId()==null || toCreate.getId()<=0){
                  toCreate.setId(++index);
            }
            map.put(toCreate.getId(),toCreate);
            return toCreate;
        }
    };
}