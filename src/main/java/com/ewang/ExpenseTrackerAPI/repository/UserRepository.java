package com.ewang.ExpenseTrackerAPI.repository;

import com.ewang.ExpenseTrackerAPI.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
