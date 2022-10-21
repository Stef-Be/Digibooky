package com.switchfully.duckbusters.digibooky.api;

import com.switchfully.duckbusters.digibooky.domain.Book;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {

    public AllBookDTO mapToAllBookDto(Book book) {
        return new AllBookDTO()
                .setIsbn(book.getIsbn())
                .setTitle(book.getTitle())
                .setAuthor(book.getAuthor());
    }

    public SingleBookDto mapToSingleBookDto(Book book) {
        return new SingleBookDto()
                .setIsbn(book.getIsbn())
                .setTitle(book.getTitle())
                .setAuthor(book.getAuthor())
                .setSummary(book.getSummary());
    }
}
