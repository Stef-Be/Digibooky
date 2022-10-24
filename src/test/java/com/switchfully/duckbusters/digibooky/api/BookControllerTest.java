package com.switchfully.duckbusters.digibooky.api;

import com.switchfully.duckbusters.digibooky.domain.Address;
import com.switchfully.duckbusters.digibooky.domain.Book;
import com.switchfully.duckbusters.digibooky.domain.Person;
import com.switchfully.duckbusters.digibooky.domain.Role;
import com.switchfully.duckbusters.digibooky.domain.repository.BookRepository;
import com.switchfully.duckbusters.digibooky.domain.repository.PersonRepository;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;

import static com.switchfully.duckbusters.digibooky.domain.Role.*;
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
}