package com.switchfully.duckbusters.digibooky.api;

import com.switchfully.duckbusters.digibooky.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RequestMapping("books")
@RestController

public class BookController {

    private final BookService bookService;
    private final Logger logger= LoggerFactory.getLogger(BookController.class);

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<AllBookDTO> getAllBooks() {
        logger.info("returning all the books");
        return bookService.getAllBooks();
    }

    @GetMapping(path = "{isbn}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public SingleBookDto getProfessor(@PathVariable String isbn) {
        logger.info("Showing single book");
        return bookService.getByIsbn(isbn);
    }

    @PostMapping(path = "{librarianId}/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void registerNewBook(@PathVariable String librarianId, @RequestBody RegisterBookDTO freshBook){
        bookService.registerNewBook(librarianId,freshBook);
    }

}
