package com.ewang.ExpenseTrackerAPI.repository;

import com.ewang.ExpenseTrackerAPI.domain.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
}
