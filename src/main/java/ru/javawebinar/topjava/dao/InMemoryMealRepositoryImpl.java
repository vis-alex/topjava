package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryMealRepositoryImpl implements MealRepository {
    private static  MealRepository mealRepository;

    private final Map<Long, Meal> mealMap = new ConcurrentHashMap<>();

     {
        Meal meal1 = new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500);
        mealMap.put(meal1.getId(), meal1);
        Meal meal2 = new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000);
        mealMap.put(meal2.getId(), meal2);
        Meal meal7 = new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500);
        mealMap.put(meal7.getId(), meal7);
        Meal meal3 = new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100);
        mealMap.put(meal3.getId(), meal3);
        Meal meal4 = new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000);
        mealMap.put(meal4.getId(), meal4);
        Meal meal5 = new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500);
        mealMap.put(meal5.getId(), meal5);
        Meal meal6 = new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410);
        mealMap.put(meal6.getId(), meal6);
    }

    private InMemoryMealRepositoryImpl() {
    }

    public static MealRepository getMealMapInstance() {
        if (mealRepository == null) {
            synchronized (InMemoryMealRepositoryImpl.class) {
                if (mealRepository == null) {
                    mealRepository = new InMemoryMealRepositoryImpl();
                }
            }
        }
        return mealRepository;
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
