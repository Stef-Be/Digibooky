package com.switchfully.duckbusters.digibooky.api;

import com.switchfully.duckbusters.digibooky.api.dto.CreatePersonDTO;
import com.switchfully.duckbusters.digibooky.api.dto.PersonDTO;
import com.switchfully.duckbusters.digibooky.service.PersonService;
import org.springframework.boot.autoconfigure.quartz.QuartzTransactionManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("person")
@CrossOrigin
public class PersonController {

    private final PersonService service;

    public PersonController(PersonService service) {
        this.service = service;
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public void createPerson(@RequestBody CreatePersonDTO freshPerson) {
        service.addPersonToRepo(freshPerson);
    }

    @PostMapping(path = "registerLibrarian", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void registerLibrarian(@RequestHeader String authorization, @RequestBody CreatePersonDTO newPerson) {
        service.registerLibrarian(authorization, newPerson);
    }

    @GetMapping(path = "listofpersons", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<PersonDTO> getListOfPersons(@RequestHeader String authorization) {
        return service.getAllPersons(authorization);
    }

}