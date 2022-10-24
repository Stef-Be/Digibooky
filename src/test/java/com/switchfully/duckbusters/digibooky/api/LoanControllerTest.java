package com.switchfully.duckbusters.digibooky.api;

import com.switchfully.duckbusters.digibooky.domain.Address;
import com.switchfully.duckbusters.digibooky.domain.Person;
import com.switchfully.duckbusters.digibooky.domain.repository.PersonRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LoanControllerTest {

    @Autowired
    private PersonRepository personRepository;



    @LocalServerPort
    private int port;

    @Test
    void createLoan() {

        Address address = new Address("funstreet", "11", "1111", "funcity");

        Person person = new Person("1", "x@x.x", "a", "b", address);

        personRepository.addPerson(person);

        String requestBody = "{\n" +
                "  \"memberId\": \"" + person.getId() + "\",\n" +
                "  \"isbn\": \"1234567890123\"}";

        System.out.println(requestBody);

        given()
                .baseUri("http://localhost")
                .port(port)
                .header("Content-type", "application/json")
                .and()
                .body(requestBody)
                .when()
                .post("/loans/new")
                .then()
                .assertThat()
                .statusCode(HttpStatus.CREATED.value())
                .extract();
    }

    @Test
    void createLoanNoSuchMember() {

        String requestBody = "{\n" +
                "  \"memberId\": \"" + "1" + "\",\n" +
                "  \"isbn\": \"1234567890123\"}";

        System.out.println(requestBody);

        given()
                .baseUri("http://localhost")
                .port(port)
                .header("Content-type", "application/json")
                .and()
                .body(requestBody)
                .when()
                .post("/loans/new")
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .extract();
    }
}