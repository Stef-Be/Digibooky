package com.switchfully.duckbusters.digibooky.api;

import com.switchfully.duckbusters.digibooky.api.dto.CreatePersonDTO;
import com.switchfully.duckbusters.digibooky.service.PersonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("person")
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

    @PostMapping(path = "{adminId}/registerLibrarian", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void registerLibrarian(@PathVariable String adminId, @RequestBody CreatePersonDTO newPerson) {
        service.registerLibrarian(adminId, newPerson);
    }

}