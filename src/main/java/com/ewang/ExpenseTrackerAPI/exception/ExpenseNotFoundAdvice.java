package com.ewang.ExpenseTrackerAPI.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice
public class ExpenseNotFoundAdvice {
    //ResponseBody signals that this advice is rendered straight into the response body
    @ResponseBody
    //ExceptionHandler configures the advice to only respond if an ExpenseNotFoundException is thrown.
    @ExceptionHandler(ExpenseNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String expenseNotFoundHandler(ExpenseNotFoundException ex) {
        return ex.getMessage();
    }
}
