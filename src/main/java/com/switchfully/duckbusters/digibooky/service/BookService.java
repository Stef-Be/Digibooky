package com.switchfully.duckbusters.digibooky.service;

import com.switchfully.duckbusters.digibooky.api.AllBookDTO;
import com.switchfully.duckbusters.digibooky.api.BookMapper;
import com.switchfully.duckbusters.digibooky.api.RegisterBookDTO;
import com.switchfully.duckbusters.digibooky.api.SingleBookDto;
import com.switchfully.duckbusters.digibooky.domain.Book;
import com.switchfully.duckbusters.digibooky.domain.Feature;
import com.switchfully.duckbusters.digibooky.domain.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.switchfully.duckbusters.digibooky.domain.Feature.REGISTER_BOOK;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    private final ValidationService validation;

    public BookService(BookRepository bookRepository, BookMapper bookMapper, ValidationService validation) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
        this.validation = validation;
    }

    public List<AllBookDTO> getAllBooks() {
        return bookRepository.getAllBooks().stream()
                .map(bookMapper::mapToAllBookDto)
                .collect(Collectors.toList());
    }

    public SingleBookDto getByIsbn(String isbn){
        Book foundBook = bookRepository.getByIsbn(isbn);
        return bookMapper.mapToSingleBookDto(foundBook);
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




}