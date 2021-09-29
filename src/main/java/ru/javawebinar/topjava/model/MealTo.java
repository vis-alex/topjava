package ru.javawebinar.topjava.model;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicLong;

public class MealTo extends BaseMeal{

    private final boolean excess;

    public MealTo(Long id, LocalDateTime dateTime, String description, int calories, boolean excess) {
        super(dateTime, description, calories);
        super.id = id;
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
