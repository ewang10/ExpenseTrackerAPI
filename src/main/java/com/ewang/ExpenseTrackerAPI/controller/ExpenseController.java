package com.ewang.ExpenseTrackerAPI.controller;

import com.ewang.ExpenseTrackerAPI.domain.Expense;
import com.ewang.ExpenseTrackerAPI.exception.ExpenseNotFoundException;
import com.ewang.ExpenseTrackerAPI.repository.ExpenseRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//RestController indicates that the data returned by each method will be
//written straight into the response body instead of rendering a template.
@RestController
public class ExpenseController {
    private final ExpenseRepository repository;
    ExpenseController(ExpenseRepository repository) {
        this.repository = repository;
    }
    @GetMapping("/expenses")
    List<Expense> all() {
        return repository.findAll();
    }

    @GetMapping("/expense/{id}")
    Expense getExpenseById(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ExpenseNotFoundException(id));
    }

    @PostMapping("/expense")
    Expense createExpense(@RequestBody Expense expense) {
        return repository.save(expense);
    }

    @PutMapping("/expense/{id}")
    Expense updateExpense(@RequestBody Expense newExpense, @PathVariable Long id) {
        return repository.findById(id)
                .map(expense -> {
                    expense.setName(newExpense.getName());
                    expense.setDescription(newExpense.getDescription());
                    expense.setAmount(newExpense.getAmount());
                    expense.setYear(newExpense.getYear());
                    expense.setMonth(newExpense.getMonth());
                    return repository.save(expense);
                })
                .orElseGet(() -> {
                    newExpense.setId(id);
                    return repository.save(newExpense);
                });
    }

    @DeleteMapping("/expense/{id}")
    void deleteExpense(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
