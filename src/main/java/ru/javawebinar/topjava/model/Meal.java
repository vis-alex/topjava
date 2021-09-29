package ru.javawebinar.topjava.model;

import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.concurrent.atomic.AtomicLong;

public class Meal extends BaseMeal{

    public Meal(LocalDateTime dateTime, String description, int calories) {
        super(dateTime, description, calories);
        super.id = MealsUtil.increment();
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
