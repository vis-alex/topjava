package ru.javawebinar.topjava.model;

import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicLong;

public abstract class BaseMeal {

    private AtomicLong id;

    private LocalDateTime dateTime;

    private String description;

    private int calories;

    public BaseMeal(LocalDateTime dateTime, String description, int calories) {
        MealsUtil.increment();
        this.id = MealsUtil.count;
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }

    public Long getId() {
        return id.get();
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public void setId(Long id) {
        this.id = new AtomicLong(id);
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    @Override
    public String toString() {
        return "BaseMeal{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                '}';
    }
}
