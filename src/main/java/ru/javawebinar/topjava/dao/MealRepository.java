package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealRepository {

    void create(Meal meal);

    void delete(Long mealId);

    void update(Meal meal);

    Meal getMealById(Long mealId);

    List<Meal> getAllMeals();
}
