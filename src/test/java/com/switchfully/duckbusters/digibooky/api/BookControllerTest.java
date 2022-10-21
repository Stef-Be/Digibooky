package com.switchfully.duckbusters.digibooky.api;

import com.switchfully.duckbusters.digibooky.domain.Book;
import com.switchfully.duckbusters.digibooky.domain.repository.BookRepository;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class BookControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private BookRepository bookRepository;

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
        SingleBookDto response = given()
                .port(port)
                .when()
                .get("/books/1234567890123")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(SingleBookDto.class);
        assertThat(response).isNotNull();
        assertThat(response.getTitle()).isEqualTo("Harry Potter");
    }

    @Test
    void getBookByIsbn_whenWrongIsbnIsGiven_then() {
       given()
                .port(port)
                .when()
                .get("/books/1234567999999")
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .extract();

    }
}