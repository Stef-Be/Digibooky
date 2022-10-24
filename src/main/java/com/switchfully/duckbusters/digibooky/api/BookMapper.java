package com.switchfully.duckbusters.digibooky.api;

import com.switchfully.duckbusters.digibooky.domain.Author;
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

    public Book createBookFromDto(RegisterBookDTO freshBook){
        Author author = new Author(freshBook.getAuthorFirstName(), freshBook.getAuthorLastName());
        return new Book(freshBook.getIsbn(), freshBook.getTitle(), author, freshBook.getSummary());
    }
}


