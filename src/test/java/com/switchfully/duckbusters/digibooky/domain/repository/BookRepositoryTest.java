package com.switchfully.duckbusters.digibooky.domain.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class BookRepositoryTest {
    @Autowired
    private BookRepository bookRepository;
    @Test
    void getByIsbnWithWildcard() {
        var list=bookRepository.getByIsbnWithWildcard("1?");
        list.forEach(book -> System.out.println( book.getIsbn()));
        Assertions.assertThat(list.size()).isEqualTo(1);
    }
}