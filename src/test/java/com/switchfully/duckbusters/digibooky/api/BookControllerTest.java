package com.switchfully.duckbusters.digibooky.api;

import com.switchfully.duckbusters.digibooky.api.dto.AllBookDTO;
import com.switchfully.duckbusters.digibooky.api.dto.SingleBookDto;
import com.switchfully.duckbusters.digibooky.domain.loan.BookLoan;
import com.switchfully.duckbusters.digibooky.domain.person.Address;
import com.switchfully.duckbusters.digibooky.domain.person.Person;
import com.switchfully.duckbusters.digibooky.domain.person.Role;
import com.switchfully.duckbusters.digibooky.domain.repository.BookRepository;
import com.switchfully.duckbusters.digibooky.domain.repository.LoanRepository;
import com.switchfully.duckbusters.digibooky.domain.repository.PersonRepository;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;

import static com.switchfully.duckbusters.digibooky.domain.person.Role.*;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class BookControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private LoanRepository loanRepository;

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "http://localhost";
    }

    @Test
    void getAllBooks() {
        AllBookDTO[] response = given()
                .port(port)
                .when()
                .get("/books")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(AllBookDTO[].class);
        assertThat(response.length).isEqualTo(9);
    }

    @Test
    void getBookByIsbn() {
        SingleBookDto[] response = given()
                .port(port)
                .when()
                .get("/books?isbn=1234567890123")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(SingleBookDto[].class);
        assertThat(response).isNotNull();
        assertThat(response[0].getTitle()).isEqualTo("Harry Potter");
    }

    @Test
    void getBookByIsbn_whenWrongIsbnIsGiven_then() {
        given()
                .port(port)
                .when()
                .get("/books?isbn=1234567999999")
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .extract();
    }

    @Test
    void getBookByIsbnThatContainsWildcard() {
        SingleBookDto[] response = given()
                .port(port)
                .when()
                .get("/books?isbn=12345*")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(SingleBookDto[].class);
        assertThat(response.length).isEqualTo(4);
    }

    @Test
    void getBookByIsbnThatContainsWildcardWithNoResult() {
        given()
                .port(port)
                .when()
                .get("/books?isbn=99999*")
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .extract();

    }

    @Test
    void getBookByIsbnEnhancedWithLenderInformationWhenNoSuchLenderExists(){

        SingleBookDto[] response = given()
                .port(port)
                .when()
                .get("/books?isbn=1234567890123")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(SingleBookDto[].class);
        assertThat(response[0].getLender()).isEqualTo("no such lender");
    }

    @Test
    void getBookByIsbnEnhancedWithLenderInformationWhenLenderExists(){

        String adminId = personRepository.getAllPersons().stream()
                .filter(person -> person.getRole() == Role.ADMIN)
                .toList()
                .get(0)
                .getId();

        loanRepository.addLoan(new BookLoan(adminId, "1234567890123"));

        SingleBookDto[] response = given()
                .port(port)
                .when()
                .get("/books?isbn=1234567890123")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(SingleBookDto[].class);
        assertThat(response[0].getLender()).isEqualTo("admin admin");
    }

    @Test
    void getAllBooksWhenNoIsbnProvided() {
        SingleBookDto[] response = given()
                .port(port)
                .when()
                .get("/books?isbn=")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(SingleBookDto[].class);

        assertThat(response.length).isEqualTo(bookRepository.getAllBooks().size());
    }

    @Test
    void addBookHappyPath(){

        Person person = new Person("1",
                "Chad",
                "Giga",
                "gigachad@based.com",
                new Address("street","1","420","city"));
        person.setRole(LIBRARIAN);
        personRepository.addPerson(person);

        String requestBody = "{\n" +
                "  \"isbn\": \"1111111111111\",\n" +
                "  \"title\": \"a book\",\n" +
                "  \"authorFirstName\": \"a\",\n" +
                "  \"authorLastName\": \"e\",\n" +
                "  \"summary\": \"this is a summary\"}"
                ;

        given()
                .baseUri("http://localhost")
                .port(port)
                .header("Content-type", "application/json")
                .and()
                .body(requestBody)
                .when()
                .post("/books/" +person.getId()+"/register")
                .then()
                .assertThat()
                .statusCode(HttpStatus.CREATED.value())
                .extract();
    }

    @Test
    void addBookNotLibrarian(){

        Person person = new Person("1",
                "Chad",
                "Giga",
                "gigachad@based.com",
                new Address("street","1","420","city"));

        personRepository.addPerson(person);

        String requestBody = "{\n" +
                "  \"isbn\": \"1111111111111\",\n" +
                "  \"title\": \"a book\",\n" +
                "  \"authorFirstName\": \"a\",\n" +
                "  \"authorLastName\": \"e\",\n" +
                "  \"summary\": \"this is a summary\"}"
                ;

        given()
                .baseUri("http://localhost")
                .port(port)
                .header("Content-type", "application/json")
                .and()
                .body(requestBody)
                .when()
                .post("/books/" +person.getId()+"/register")
                .then()
                .assertThat()
                .statusCode(HttpStatus.FORBIDDEN.value())
                .extract();
    }

    @Test
    void getBookByTitle() {
        SingleBookDto[] response = given()
                .port(port)
                .when()
                .get("/books?title=Harry Potter 2")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(SingleBookDto[].class);
        assertThat(response[0].getTitle()).isEqualTo("Harry Potter 2");
    }

    @Test
    void getBookByTitleWithWildcard() {
        SingleBookDto[] response = given()
                .port(port)
                .when()
                .get("/books?title=Harry Potter*")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(SingleBookDto[].class);
        assertThat(response.length).isEqualTo(4);
    }

    @Test
    void getBookByTitleThatContainsWildcardWithNoResult() {
        given()
                .port(port)
                .when()
                .get("/books?title=*random*")
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .extract();
    }

    @Test
    void getAllBooksWhenNoTitleProvided() {
        SingleBookDto[] response = given()
                .port(port)
                .when()
                .get("/books?title=")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(SingleBookDto[].class);

        assertThat(response.length).isEqualTo(bookRepository.getAllBooks().size());
    }

    @Test
    void getBookByAuthor() {
        SingleBookDto[] response = given()
                .port(port)
                .when()
                .get("/books/author?firstName=*t*&lastName=*v*")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(SingleBookDto[].class);
        assertThat(response.length).isEqualTo(bookRepository.getAllBooks().stream().filter(book -> book.getAuthor().getFirstName().equals("Tim")).toList().size());
    }

    @Test
    void getBookByAuthorWhenNoFirstNameProvided() {
        SingleBookDto[] response = given()
                .port(port)
                .when()
                .get("/books/author?firstName=&lastName=*v*")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(SingleBookDto[].class);
        assertThat(response.length).isEqualTo(bookRepository.getAllBooks().stream().filter(book -> book.getAuthor().getLastName().contains("V")).toList().size());
    }

    @Test
    void getBookByAuthorWhenNoLastNameProvided() {
        SingleBookDto[] response = given()
                .port(port)
                .when()
                .get("/books/author?firstName=Tim&lastName=")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(SingleBookDto[].class);
        assertThat(response.length).isEqualTo(bookRepository.getAllBooks().stream().filter(book -> book.getAuthor().getFirstName().equals("Tim")).toList().size());
    }

    @Test
    void getBookByAuthorWhenNoNamesProvided() {
        SingleBookDto[] response = given()
                .port(port)
                .when()
                .get("/books/author?firstName=&lastName=")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(SingleBookDto[].class);
        assertThat(response.length).isEqualTo(bookRepository.getAllBooks().size());
    }
}