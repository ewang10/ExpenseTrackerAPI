package com.ewang.ExpenseTrackerAPI.controller;

import com.ewang.ExpenseTrackerAPI.domain.User;
import com.ewang.ExpenseTrackerAPI.exception.UserNotFoundException;
import com.ewang.ExpenseTrackerAPI.repository.UserRepository;
import com.ewang.ExpenseTrackerAPI.util.UserModelAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
public class UserController {
    private final UserRepository repository;
    private final UserModelAssembler assembler;
    UserController(UserRepository repository, UserModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @GetMapping("/users")
    public CollectionModel<EntityModel<User>> all() {
        List<EntityModel<User>> users = repository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(users,
                linkTo(methodOn(UserController.class).all()).withSelfRel());
    }

    @GetMapping("/users/{id}")
    public EntityModel<User> getUserById(@PathVariable Long id) {
        User user = repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        return assembler.toModel(user);
    }

    @PostMapping("/users")
    ResponseEntity<?> createUser(@RequestBody User user) {
        EntityModel<User> modelEntity = assembler.toModel(repository.save(user));
        return ResponseEntity.created(modelEntity.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(modelEntity);
    }

    @PutMapping("/users/{id}")
    ResponseEntity<?> updateUser(@RequestBody User newUser, @PathVariable Long id) {
        User updatedUser = repository.findById(id)
                .map((user) -> {
                    user.setUsername(newUser.getUsername());
                    user.setPassword(newUser.getPassword());
                    return repository.save(user);
                })
                .orElseGet(() -> {
                    newUser.setId(id);
                    return repository.save(newUser);
                });
        EntityModel<User> modelEntity = assembler.toModel(updatedUser);
        return ResponseEntity.created(modelEntity.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(modelEntity);
    }

    @DeleteMapping("/users/{id}")
    ResponseEntity<?> deleteUser(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
