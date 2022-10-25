package com.switchfully.duckbusters.digibooky.api;

import com.switchfully.duckbusters.digibooky.domain.person.Role;
import com.switchfully.duckbusters.digibooky.domain.repository.PersonRepository;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class PersonControllerTest {

    @Autowired
    private PersonRepository personRepository;
    private static String requestBody = "{\n" +
            "  \"inss\": \"1\",\n" +
            "  \"eMail\": \"x@x.x\",\n" +
            "  \"firstName\": \"a\",\n" +
            "  \"lastName\": \"b\",\n" +
            "  \"street\": \"abcstreet\", \n" +
            "  \"houseNumber\": \"11\", \n" +
            "  \"postalCode\": \"1234\", \n" +
            "  \"city\": \"Brussels\", \n" +
            "  \"password\": \"Brussels\"}";


    @LocalServerPort
    private int port;

    @Test
    void createPerson() {

        System.out.println(requestBody);

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

    @Test
    void registerLibrarian() {
        given()
                .baseUri("http://localhost")
                .port(port)
                .header("Content-type", "application/json")
                .auth()
                .preemptive()
                .basic("admin@digibooky.com", "password")
                .header("Accept", ContentType.JSON.getAcceptHeader())
                .body(requestBody)
                .when()
                .post("/person/registerLibrarian")
                .then()
                .assertThat()
                .statusCode(HttpStatus.CREATED.value())
                .extract();

        Role role = personRepository.getAllPersons().stream()
                .filter(person -> person.getInss().equals( "1"))
                .toList()
                .get(0)
                .getRole();
        System.out.println(role);
        Assertions.assertEquals(Role.LIBRARIAN,role);
    }

    @Test
    void registerLibrarian_whenUnauthorizedPersonAccess_thenReturnForbiddenStatus() {
        String adminId = personRepository.getAllPersons().stream()
                .filter(person -> person.getRole() == Role.LIBRARIAN)
                .toList()
                .get(0)
                .getId();
        given()
                .baseUri("http://localhost")
                .port(port)
                .auth()
                .preemptive()
                .basic("alibrarian@digibooky.com", "password")
                .header("Accept", ContentType.JSON.getAcceptHeader())
                .header("Content-type", "application/json")
                .and()
                .body(requestBody)
                .when()
                .post("/person/registerLibrarian")
                .then()
                .assertThat()
                .statusCode(HttpStatus.FORBIDDEN.value())
                .extract();
    }

    @Test
    void viewAllMember_WhenIsAdmin() {

        given()
                .baseUri("http://localhost")
                .port(port)
                .auth()
                .preemptive()
                .basic("admin@digibooky.com", "password")
                .header("Accept", ContentType.JSON.getAcceptHeader())
                .header("Content-type", "application/json")
                .and()
                .when()
                .get("/person/listofpersons")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract();

    }
    @Test
    void viewAllMember_WhenIsNotAdmin() {
        given()
                .baseUri("http://localhost")
                .port(port)
                .auth()
                .preemptive()
                .basic("alibrarian@digibooky.com", "password")
                .header("Accept", ContentType.JSON.getAcceptHeader())
                .header("Content-type", "application/json")
                .and()
                .when()
                .get("/person/listofpersons")
                .then()
                .assertThat()
                .statusCode(HttpStatus.FORBIDDEN.value())
                .extract();

    }
}