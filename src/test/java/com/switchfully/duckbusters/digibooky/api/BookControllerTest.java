package com.switchfully.duckbusters.digibooky.api;

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
import static org.junit.jupiter.api.Assertions.*;
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
        BookDTO[] response = given()
                .port(port)
                .when()
                .get("/books")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(BookDTO[].class);
        assertThat(response.length).isEqualTo(9);
    }

}