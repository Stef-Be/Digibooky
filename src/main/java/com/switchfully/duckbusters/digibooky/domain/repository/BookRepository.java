package com.switchfully.duckbusters.digibooky.domain.repository;

import com.switchfully.duckbusters.digibooky.domain.Author;
import com.switchfully.duckbusters.digibooky.domain.Book;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class BookRepository {
    private Map<String, Book> books;

    public BookRepository() {
        books = new HashMap<>();
        getBooksFromDatabase();
    }

    private void getBooksFromDatabase() {
        Book book1 = new Book("1234567890123", "Harry Potter", new Author("J.K.", "Rowling"), "Kid becomes magician");
        Book book2 = new Book("6123456701283", "Harry Potter 2", new Author("J.K.", "Rowling"), "Kid goes to school do magic");
        Book book3 = new Book("1234785490123", "Harry Potter 3", new Author("J.K.", "Rowling"), "more magic stuff");
        Book book4 = new Book("9834567890123", "Harry Potter 4", new Author("J.K.", "Rowling"), "end of the magic stuff");
        Book book5 = new Book("1234444590123", "Java Advanced", new Author("Tim", "Vercruysse"), "it is about java advance");
        Book book6 = new Book("1234598566123", "Spring Advanced", new Author("Karel", "Van Roey"), "it is about spring advance");
        Book book7 = new Book("6987467890123", "How to program very fast", new Author("Tim", "Vercruysse"), "he types very fast");
        Book book8 = new Book("1234567874556", "How to shave a beard", new Author("Karel", "Van Roey"), "He shaved for the king");
        Book book9 = new Book("1234565585555", "Security advanced", new Author("Christoph", "Marckx"), "it is about security advance");
        books.put(book1.getIsbn(), book1);
        books.put(book2.getIsbn(), book2);
        books.put(book3.getIsbn(), book3);
        books.put(book4.getIsbn(), book4);
        books.put(book5.getIsbn(), book5);
        books.put(book6.getIsbn(), book6);
        books.put(book7.getIsbn(), book7);
        books.put(book8.getIsbn(), book8);
        books.put(book9.getIsbn(), book9);
    }

    public Collection<Book> getAllBooks() {
        return books.values();
    }

//    public Book getByIsbn(String isbn){
//        var foundBook = books.get(isbn);
//        if (foundBook == null) {
//            throw new IllegalArgumentException("No book can be found with the isbn:" + isbn);
//        }
//        return foundBook;
//    }

    public List<Book> getByIsbn(String isbn){
        String regex =isbn.replace("?",".").replace("*",".*");
        var foundBooks = books.values().stream()
                .filter(book -> book.getIsbn().matches(regex))
                .collect(Collectors.toList());
        if (foundBooks.size()==0) {
            throw new IllegalArgumentException("No book can be found with the isbn:" + isbn);
        }
        return foundBooks;
    }

    public boolean doesBookExist(String isbn){
        return books.containsKey(isbn);
    }
}
