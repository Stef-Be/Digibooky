package com.switchfully.duckbusters.digibooky.api;

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
}
