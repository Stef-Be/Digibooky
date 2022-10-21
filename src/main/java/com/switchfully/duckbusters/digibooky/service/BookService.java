package com.switchfully.duckbusters.digibooky.service;

import com.switchfully.duckbusters.digibooky.api.BookDTO;
import com.switchfully.duckbusters.digibooky.api.BookMapper;
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

    public List<BookDTO> getAllBooks() {
        return bookRepository.getAllBooks().stream()
                .map(bookMapper::mapToBookDto)
                .collect(Collectors.toList());
    }
}
