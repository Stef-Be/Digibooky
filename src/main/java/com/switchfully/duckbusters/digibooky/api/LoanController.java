package com.switchfully.duckbusters.digibooky.api;

import com.switchfully.duckbusters.digibooky.api.dto.AddLoanDTO;
import com.switchfully.duckbusters.digibooky.api.dto.LoanDto;
import com.switchfully.duckbusters.digibooky.api.dto.returnBookDTO;
import com.switchfully.duckbusters.digibooky.service.LoanService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.tags.Param;

import java.util.List;

@RestController
@RequestMapping(path = "loans")
@CrossOrigin
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

    @GetMapping(path = "{librarianId}/view", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public List<LoanDto> getLoansFromMember(@PathVariable String librarianId, @RequestParam String memberId){
        return loanService.getLoansFromMember(librarianId,memberId);
    }

    @GetMapping(path = "{librarianId}/overdue", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public List<LoanDto> getOverDueLoans(@PathVariable String librarianId){
        return loanService.getOverdueLoans(librarianId);
    }


}
