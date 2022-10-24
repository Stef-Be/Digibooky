package com.switchfully.duckbusters.digibooky.api;

import com.switchfully.duckbusters.digibooky.api.dto.AllBookDTO;
import com.switchfully.duckbusters.digibooky.api.dto.RegisterBookDTO;
import com.switchfully.duckbusters.digibooky.api.dto.SingleBookDto;
import com.switchfully.duckbusters.digibooky.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping(params = "isbn", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<SingleBookDto> getBookByIsbn(@RequestParam String isbn) {
        return bookService.getByIsbn(isbn);
    }
    @GetMapping(params = "title", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<SingleBookDto> getBookByTitle(@RequestParam String title) {
        return bookService.getByTitle(title);
    }

    @PostMapping(path = "{librarianId}/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void registerNewBook(@PathVariable String librarianId, @RequestBody RegisterBookDTO freshBook){
        bookService.registerNewBook(librarianId,freshBook);
    }


}
