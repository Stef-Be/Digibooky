package com.switchfully.duckbusters.digibooky.domain.repository;

import com.switchfully.duckbusters.digibooky.domain.Author;
import com.switchfully.duckbusters.digibooky.domain.Book;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
@Repository
public class BookRepository {
    private Map<String, Book> books;

    public BookRepository() {
        books=new HashMap<>();
        getBooksFromDatabase();
    }

    private void getBooksFromDatabase() {
        Book book1=new Book("1234567890123","Harry Potter",new Author("J.K.","Rowling"));
        Book book2=new Book("6575678901283","Harry Potter 2",new Author("J.K.","Rowling"));
        Book book3=new Book("1234785490123","Harry Potter 3",new Author("J.K.","Rowling"));
        Book book4=new Book("9834567890123","Harry Potter 4",new Author("J.K.","Rowling"));
        Book book5=new Book("1234444590123","Java Advanced",new Author("Tim","Vercruysse"));
        Book book6=new Book("1234598566123","Spring Advanced",new Author("Karel","Van Roey"));
        Book book7=new Book("6987467890123","How to program very fast",new Author("Tim","Vercruysse"));
        Book book8=new Book("1234567874556","How to shave a beard",new Author("Karel","Van Roey"));
        Book book9=new Book("1234565585555","Security advanced",new Author("Christoph","Marckx"));
        books.put(book1.getIsbn(),book1);
        books.put(book2.getIsbn(),book2);
        books.put(book3.getIsbn(),book3);
        books.put(book4.getIsbn(),book4);
        books.put(book5.getIsbn(),book5);
        books.put(book6.getIsbn(),book6);
        books.put(book7.getIsbn(),book7);
        books.put(book8.getIsbn(),book8);
        books.put(book9.getIsbn(),book9);
        }

        public Collection<Book> getAllBooks(){
        return books.values();
        }
}
