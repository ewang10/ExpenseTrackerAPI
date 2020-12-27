package com.ewang.ExpenseTrackerAPI.repository;

import com.ewang.ExpenseTrackerAPI.domain.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findByYear(int year);
    List<Expense> findByYearAndMonth(int year, String month);
}
