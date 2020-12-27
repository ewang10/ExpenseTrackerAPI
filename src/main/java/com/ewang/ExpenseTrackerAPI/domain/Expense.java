package com.ewang.ExpenseTrackerAPI.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Expense {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String description;
    private double amount;
    private int year;
    private String month;

    //Need a no argument constructor for JPA
    public Expense() {

    }

    public Expense(String name, String description, double amount, int year, String month) {
        this.name = name;
        this.description = description;
        this.amount = amount;
        this.year = year;
        this.month = month;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Expense)) {
            return false;
        }
        Expense expense = (Expense) o;
        return Objects.equals(this.id, expense.id) && Objects.equals(this.name, expense.name) &&
                Objects.equals(this.description, expense.description) && Objects.equals(this.amount, expense.amount) &&
                Objects.equals(this.year, expense.year) && Objects.equals(this.month, expense.month);
    }
    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.name, this.description, this.amount, this.year, this.month);
    }

    @Override
    public String toString() {
        return "Expense{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", amount=" + amount +
                ", year=" + year +
                ", month='" + month + '\'' +
                '}';
    }
}
