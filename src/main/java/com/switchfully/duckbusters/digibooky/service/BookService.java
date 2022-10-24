package com.switchfully.duckbusters.digibooky.service;

import com.switchfully.duckbusters.digibooky.api.AllBookDTO;
import com.switchfully.duckbusters.digibooky.api.BookMapper;
import com.switchfully.duckbusters.digibooky.api.SingleBookDto;
import com.switchfully.duckbusters.digibooky.domain.Book;
import com.switchfully.duckbusters.digibooky.domain.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public BookService(BookRepository bookRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
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
}