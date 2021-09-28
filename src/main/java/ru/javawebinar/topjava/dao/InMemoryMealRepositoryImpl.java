package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryMealRepositoryImpl implements MealRepository {
    private Map<Long, Meal> mealMap;

    private InMemoryMealRepositoryImpl() {
    }

    public  Map<Long, Meal> getMealMapInstance() {
        if (mealMap == null) {
            synchronized (InMemoryMealRepositoryImpl.class) {
                if (mealMap == null) {
                    mealMap = new ConcurrentHashMap<>();
                }
            }
        }
        return mealMap;
    }

    @Override
    public void create(Meal meal) {
        mealMap.put(meal.getId(), meal);
    }

    @Override
    public void delete(Long mealId) {
        mealMap.remove(mealId);
    }

    @Override
    public void update(Meal meal) {
        Meal oldMeal = mealMap.get(meal.getId());

        if (oldMeal != null) {
            oldMeal.setCalories(meal.getCalories());
            oldMeal.setDateTime(meal.getDateTime());
            oldMeal.setDescription(meal.getDescription());
            oldMeal.setId(meal.getId());
        }
    }

    @Override
    public Meal getMealById(Long mealId) {
        return mealMap.get(mealId);
    }

    @Override
    public List<Meal> getAllMeals() {
        return new ArrayList<>(mealMap.values());
    }
}
