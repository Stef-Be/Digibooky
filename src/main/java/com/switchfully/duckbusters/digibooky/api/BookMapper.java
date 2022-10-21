package com.switchfully.duckbusters.digibooky.api;

import com.switchfully.duckbusters.digibooky.domain.Book;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {

    public BookDTO mapToBookDto(Book book) {
        return new BookDTO()
                .setIsbn(book.getIsbn())
                .setTitle(book.getTitle())
                .setAuthor(book.getAuthor());
    }

    public Book mapToBook(BookDTO bookDTO) {
        return new Book(bookDTO.getIsbn(), bookDTO.getTitle(), bookDTO.getAuthor());
    }
}
