package ru.javawebinar.topjava.model;

import java.time.LocalDateTime;

public class MealTo extends BaseMeal{

    private final boolean excess;

    public MealTo(LocalDateTime dateTime, String description, int calories, boolean excess) {
        super(dateTime, description, calories);
        this.excess = excess;
    }

    public boolean isExcess() {
        return excess;
    }

    @Override
    public String toString() {
        return super.toString() + "MealTo{" +
                "excess=" + excess +
                '}';
    }
}
