package com.ewang.ExpenseTrackerAPI.controller;

import com.ewang.ExpenseTrackerAPI.domain.Expense;
import com.ewang.ExpenseTrackerAPI.exception.ExpenseNotFoundException;
import com.ewang.ExpenseTrackerAPI.repository.ExpenseRepository;
import com.ewang.ExpenseTrackerAPI.util.ExpenseModelAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

//RestController indicates that the data returned by each method will be
//written straight into the response body instead of rendering a template.
@RestController
public class ExpenseController {
    private final ExpenseRepository repository;
    private final ExpenseModelAssembler assembler;
    ExpenseController(ExpenseRepository repository, ExpenseModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }
    //CollectionModel<> is another Spring HATEOAS container. It's aimed at encapsulating
    //collections of resources—instead of a single resource entity, like EntityModel<>
    //CollectionModel<> lets you include links as well
    @GetMapping("/expenses")
    public CollectionModel<EntityModel<Expense>> all() {
        List<EntityModel<Expense>> expenses = repository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(expenses,
                linkTo(methodOn(ExpenseController.class).all()).withSelfRel());
    }

    //EntityModel<T> is a generic container from Spring HATEOAS that includes not only the data but a collection of links.
    @GetMapping("/expenses/{id}")
    public EntityModel<Expense> getExpenseById(@PathVariable Long id) {
        Expense expense = repository.findById(id)
                .orElseThrow(() -> new ExpenseNotFoundException(id));
        return assembler.toModel(expense);
    }

    @GetMapping("/expenses/year/{year}")
    CollectionModel<EntityModel<Expense>> getExpensesByYear(@PathVariable int year) {
        List<EntityModel<Expense>> expensesByYear = repository.findByYear(year).stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(expensesByYear,
                linkTo(methodOn(ExpenseController.class).getExpensesByYear(year)).withSelfRel());
    }

    @GetMapping("/expenses/year/{year}/month/{month}")
    CollectionModel<EntityModel<Expense>> getExpensesByYearAndMonth(@PathVariable int year, @PathVariable String month) {
        if ("All".equals(month)) {
            return this.getExpensesByYear(year);
        }
        List<EntityModel<Expense>> expensesByYearAndMonth = repository.findByYearAndMonth(year, month).stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(expensesByYearAndMonth,
                linkTo(methodOn(ExpenseController.class).getExpensesByYearAndMonth(year, month)).withSelfRel());
    }

    @PostMapping("/expenses")
    ResponseEntity<?> createExpense(@RequestBody Expense expense) {
        EntityModel<Expense> entityModel = assembler.toModel(repository.save(expense));
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @PutMapping("/expenses/{id}")
    ResponseEntity<?> updateExpense(@RequestBody Expense newExpense, @PathVariable Long id) {
        Expense updatedExpense = repository.findById(id)
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
        EntityModel<Expense> entityModel = assembler.toModel(updatedExpense);
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @DeleteMapping("/expenses/{id}")
    ResponseEntity<?> deleteExpense(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
