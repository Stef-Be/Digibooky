package com.switchfully.duckbusters.digibooky.api;

import com.switchfully.duckbusters.digibooky.domain.person.Address;
import com.switchfully.duckbusters.digibooky.domain.loan.BookLoan;
import com.switchfully.duckbusters.digibooky.domain.person.Person;
import com.switchfully.duckbusters.digibooky.domain.repository.LoanRepository;
import com.switchfully.duckbusters.digibooky.domain.repository.PersonRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LoanControllerTest {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private LoanRepository loanRepository;

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

    @Test
    void returnLoan() {

        BookLoan bookLoan = new BookLoan("123", "456");

       loanRepository.addLoan(bookLoan);

        given()
                .baseUri("http://localhost")
                .port(port)
                .when()
                .put("/loans/return/" + bookLoan.getId())
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract();
    }
}