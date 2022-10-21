package com.switchfully.duckbusters.digibooky.api;

import com.switchfully.duckbusters.digibooky.service.PersonService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestController
@RequestMapping("person")
public class PersonController {

    private final PersonService service;

    public PersonController(PersonService service) {
        this.service = service;
    }

    @PostMapping(consumes = "application/json")
    public void createPerson(@RequestBody CreatePersonDTO freshPerson){
        service.addPersonToRepo(freshPerson);
    }

    @ExceptionHandler({IllegalArgumentException.class})
    protected void courseID(IllegalArgumentException ex, HttpServletResponse response) throws IOException {
        System.out.println(ex.getMessage());
        response.sendError(BAD_REQUEST.value(), ex.getMessage());
    }

}
