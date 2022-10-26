package com.switchfully.duckbusters.digibooky.api;

import com.switchfully.duckbusters.digibooky.api.dto.*;
import com.switchfully.duckbusters.digibooky.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("books")
@RestController
@CrossOrigin
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
    public SingleBookDto getExactBookByIsbn(@PathVariable String isbn) {
        return bookService.getExactBookByIsbn(isbn);
    }

    @GetMapping(params = "isbn", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<SingleBookDto> getBookByIsbn(@RequestParam(required = false) String isbn) {
        return bookService.getByIsbn(isbn);
    }
    @GetMapping(params = "title", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<SingleBookDto> getBookByTitle(@RequestParam(required = false) String title) {
        return bookService.getByTitle(title);
    }

    @GetMapping(path="author", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<SingleBookDto> getBookByAuthor(@RequestParam(required = false) String firstName, @RequestParam (required = false) String lastName) {
        return bookService.getByAuthor(firstName, lastName);
    }

    @PostMapping(path = "register", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void registerNewBook(@RequestHeader String authorization, @RequestBody RegisterBookDTO freshBook){
        bookService.registerNewBook(authorization,freshBook);
    }

    @PutMapping(path = "delete")
    @ResponseStatus (HttpStatus.OK)
    public void softDeleteBook(@RequestHeader String authorization, @RequestParam String isbn){
        bookService.softDeleteBook(authorization,isbn);
    }

    @PutMapping(path = "update",consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus (HttpStatus.OK)
    public void updateBook(@RequestHeader String authorization, @RequestParam String isbn, @RequestBody UpdateBookDTO update){
        bookService.updateBook(authorization,isbn,update);
    }


}
