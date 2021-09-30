package ru.javawebinar.topjava.model;

import ru.javawebinar.topjava.dao.InMemoryMealRepositoryImpl;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Meal extends BaseMeal{

    public Meal(LocalDateTime dateTime, String description, int calories) {
        super(dateTime, description, calories);
        super.id = InMemoryMealRepositoryImpl.count.incrementAndGet();
    }

    public LocalDate getDate() {
        return super.getDateTime().toLocalDate();
    }

    public LocalTime getTime() {
        return super.getDateTime().toLocalTime();
    }

    @Override
    public String toString() {
        return "Meal{}" + super.toString();
    }
}
