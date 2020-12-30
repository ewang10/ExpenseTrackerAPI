package com.ewang.ExpenseTrackerAPI.util;

import com.ewang.ExpenseTrackerAPI.controller.ExpenseController;
import com.ewang.ExpenseTrackerAPI.domain.Expense;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class ExpenseModelAssembler implements RepresentationModelAssembler<Expense, EntityModel<Expense>> {
    @Override
    public EntityModel<Expense> toModel(Expense expense) {
        return EntityModel.of(expense,
                linkTo(methodOn(ExpenseController.class).getExpenseById(expense.getId())).withSelfRel(), //asks Spring HATEOAS to build a link to getExpenseById() method, and flag it as as self link
                linkTo(methodOn(ExpenseController.class).all()).withRel("expenses")); //asks Spring HATEOAS to build a link to the aggregate root, all(), and call it "expenses"
    }
}
