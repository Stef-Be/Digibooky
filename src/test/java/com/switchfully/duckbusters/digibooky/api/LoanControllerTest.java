package com.switchfully.duckbusters.digibooky.api;

import com.switchfully.duckbusters.digibooky.domain.person.Address;
import com.switchfully.duckbusters.digibooky.domain.loan.BookLoan;
import com.switchfully.duckbusters.digibooky.domain.person.Person;
import com.switchfully.duckbusters.digibooky.domain.repository.LoanRepository;
import com.switchfully.duckbusters.digibooky.domain.repository.PersonRepository;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import static com.switchfully.duckbusters.digibooky.domain.person.Role.LIBRARIAN;
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

        given()
                .baseUri("http://localhost")
                .port(port)
                .auth()
                .preemptive()
                .basic("amember@digibooky.com", "password")
                .header("Accept", ContentType.JSON.getAcceptHeader())
                .when()
                .post("/loans/new?isbn=1234567890123")
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
                .auth()
                .preemptive()
                .basic("amember@digibooky.com", "password")
                .header("Accept", ContentType.JSON.getAcceptHeader())
                .when()
                .put("/loans/" + bookLoan.getId()+"/return")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract();
    }

    @Test
    void viewLoansOfMemberAsLibrarian() {
        Person person = new Person("420",
                "Chad",
                "Giga",
                "gigachad@based.com",
                new Address("street", "1", "420", "city"), "password123");
        personRepository.addPerson(person);

        given()
                .baseUri("http://localhost")
                .port(port)
                .auth()
                .preemptive()
                .basic("alibrarian@digibooky.com", "password")
                .header("Accept", ContentType.JSON.getAcceptHeader())
                .when()
                .get("/loans/view?memberId=" + person.getId())
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract();

    }

    @Test
    void viewOverdueAsLibrarian() {


        given()
                .baseUri("http://localhost")
                .port(port)
                .auth()
                .preemptive()
                .basic("alibrarian@digibooky.com", "password")
                .header("Accept", ContentType.JSON.getAcceptHeader())
                .when()
                .get("/loans/overdue")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract();

    }


}