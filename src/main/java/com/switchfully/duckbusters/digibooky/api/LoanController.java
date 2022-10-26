package com.switchfully.duckbusters.digibooky.api;

import com.switchfully.duckbusters.digibooky.api.dto.LoanDto;
import com.switchfully.duckbusters.digibooky.api.dto.LoanReceipt;
import com.switchfully.duckbusters.digibooky.api.dto.returnBookDTO;
import com.switchfully.duckbusters.digibooky.service.LoanService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "loans")
@CrossOrigin
public class LoanController {

    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @PostMapping(path = "/new" ,produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public LoanReceipt newLoan(@RequestHeader String authorization, @RequestParam String isbn){
        return loanService.loanBook(authorization, isbn);
    }

    @PutMapping(path = "{loanId}/return", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public returnBookDTO returnLoan(@RequestHeader String authorization, @PathVariable String loanId){
        return loanService.returnBook(authorization, loanId);
    }

    @GetMapping(path = "view", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public List<LoanDto> getLoansFromMember(@RequestHeader String authorization, @RequestParam String memberId){
        return loanService.getLoansFromMember(authorization,memberId);
    }

    @GetMapping(path = "overdue", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public List<LoanDto> getOverDueLoans(@RequestHeader String authorization){
        return loanService.getOverdueLoans(authorization);
    }


}
