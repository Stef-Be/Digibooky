package com.switchfully.duckbusters.digibooky.service;

import com.switchfully.duckbusters.digibooky.api.dto.AllBookDTO;
import com.switchfully.duckbusters.digibooky.api.dto.AuthorDTO;
import com.switchfully.duckbusters.digibooky.api.mapper.AuthorMapper;
import com.switchfully.duckbusters.digibooky.api.mapper.BookMapper;
import com.switchfully.duckbusters.digibooky.api.dto.RegisterBookDTO;
import com.switchfully.duckbusters.digibooky.api.dto.SingleBookDto;
import com.switchfully.duckbusters.digibooky.domain.book.Book;
import com.switchfully.duckbusters.digibooky.domain.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.switchfully.duckbusters.digibooky.domain.person.Feature.REGISTER_BOOK;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    private final AuthorMapper authorMapper;

    private final ValidationService validation;

    public BookService(BookRepository bookRepository, BookMapper bookMapper, ValidationService validation, AuthorMapper authorMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
        this.validation = validation;
        this.authorMapper = authorMapper;
    }

    public List<AllBookDTO> getAllBooks() {
        return bookRepository.getAllBooks().stream()
                .map(bookMapper::mapToAllBookDto)
                .collect(Collectors.toList());
    }

    public List<SingleBookDto> getByIsbn(String isbn) {
        List<Book> foundBooks = bookRepository.getByIsbn(isbn);
        return foundBooks.stream().map(bookMapper::mapToSingleBookDto).collect(Collectors.toList());
    }

    public void registerNewBook(String librarianId,RegisterBookDTO freshBook){
        validation.validateAuthorization(librarianId, REGISTER_BOOK);
        validateIsbn(freshBook.getIsbn());
        validateTitle(freshBook.getTitle());
        validateLastName(freshBook.getAuthorLastName());
        bookRepository.addNewBook(bookMapper.createBookFromDto(freshBook));
    }

    private void validateIsbn(String isbn){
        if(isbn == null)throw new IllegalArgumentException("isbn is empty!");
        if(isbn.length() != 13) throw new IllegalArgumentException("isbn must be 13 characters long!");
        if(bookRepository.doesBookExist(isbn))throw new IllegalArgumentException("isbn must be unique!");
    }

    private void validateTitle(String title){
        if(title == null)throw new IllegalArgumentException("title is empty!");
    }

    private void validateLastName(String name){
        if(name == null)throw new IllegalArgumentException("author last name is empty!");
    }





    public List<SingleBookDto> getByTitle(String title) {
        List<Book> foundBooks = bookRepository.getByTitle(title);
        return foundBooks.stream().map(bookMapper::mapToSingleBookDto).collect(Collectors.toList());
    }

    public List<SingleBookDto> getByAuthor(String firstName, String lastName) {
        List<Book> foundBooks = bookRepository.getByAuthor(firstName, lastName);
        return foundBooks.stream().map(bookMapper::mapToSingleBookDto).collect(Collectors.toList());
    }
}