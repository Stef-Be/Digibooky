package com.switchfully.duckbusters.digibooky.api;

import com.switchfully.duckbusters.digibooky.api.dto.AddLoanDTO;
import com.switchfully.duckbusters.digibooky.api.dto.returnBookDTO;
import com.switchfully.duckbusters.digibooky.service.LoanService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "loans")
public class LoanController {

    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @PostMapping(path = "/new", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public void newLoan(@RequestBody AddLoanDTO loanInfo){
        loanService.LoanBook(loanInfo);
    }

    @PutMapping(path = "/return/{id}", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public returnBookDTO returnLoan(@PathVariable String id){
        return loanService.returnBook(id);
    }

}
