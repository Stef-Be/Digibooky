package com.switchfully.duckbusters.digibooky.api.mapper;

import com.switchfully.duckbusters.digibooky.api.dto.AllBookDTO;
import com.switchfully.duckbusters.digibooky.api.dto.RegisterBookDTO;
import com.switchfully.duckbusters.digibooky.api.dto.SingleBookDto;
import com.switchfully.duckbusters.digibooky.domain.book.Author;
import com.switchfully.duckbusters.digibooky.domain.book.Book;
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


