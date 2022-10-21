package com.switchfully.duckbusters.digibooky.api;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PersonControllerTest {


    private static String requestBody = "{\n" +
            "  \"inss\": \"1\",\n" +
            "  \"eMail\": \"x@x.x\",\n" +
            "  \"firstName\": \"a\",\n" +
            "  \"lastName\": \"b\",\n" +
            "  \"street\": \"abcstreet\", \n" +
            "  \"houseNumber\": \"11\", \n" +
            "  \"postalCode\": \"1234\", \n" +
            "  \"city\": \"Brussels\"}";

    @LocalServerPort
    private int port;

    @Test
    void createPerson() {

        given()
                .baseUri("http://localhost")
                .port(port)
                .header("Content-type", "application/json")
                .and()
                .body(requestBody)
                .when()
                .post("/person")
                .then()
                .assertThat()
                .statusCode(HttpStatus.CREATED.value())
                .extract();
    }
}