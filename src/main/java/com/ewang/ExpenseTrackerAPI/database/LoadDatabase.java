package com.ewang.ExpenseTrackerAPI.database;

import com.ewang.ExpenseTrackerAPI.domain.Expense;
import com.ewang.ExpenseTrackerAPI.domain.User;
import com.ewang.ExpenseTrackerAPI.repository.ExpenseRepository;
import com.ewang.ExpenseTrackerAPI.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatabase {
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);
    //Spring Boot will run all CommandLineRunner beans once the application context is loaded
    @Bean
    CommandLineRunner initDatabase(ExpenseRepository expenseRepository, UserRepository userRepository) {
        return args -> {
            expenseRepository.save(new Expense("test1", "description1", 10, 2020, "November"));
            expenseRepository.save(new Expense("test2", "description2", 11, 2020, "December"));
            expenseRepository.findAll().forEach(expense -> log.info("Preloaded " + expense));
            userRepository.save(new User("testUser1", "testPassword1"));
            userRepository.save(new User("testUser2", "testPassword2"));
            userRepository.findAll().forEach(user -> log.info("Preloaded " + user));
        };
    }
}
