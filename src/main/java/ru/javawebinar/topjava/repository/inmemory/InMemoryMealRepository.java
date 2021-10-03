package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        for (Meal meal : MealsUtil.meals) {
            save(SecurityUtil.authUserId(), meal);
        }
    }

    @Override
    public Meal save(int userId, Meal meal) {
        Map<Integer, Meal> mealMap = repository.get(userId) == null ? new ConcurrentHashMap<>() : repository.get(userId);

        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
        }
        mealMap.put(meal.getId(), meal);
        repository.put(userId, mealMap);
        return meal;
    }

    @Override
    public boolean delete(int userId, int mealId) {
        if (repository.get(userId) != null) {
            return repository.get(userId).remove(mealId) != null;
        }
        return false;
    }

    @Override
    public Meal get(int userId, int mealId) {
        if (repository.get(userId) != null) {
            return repository.get(userId).get(mealId);
        }
        return null;
    }

    @Override
    public List<Meal> getAll(int userId) {
        if (repository.get(userId) == null) {
            return new ArrayList<>();
        }
        return repository.get(userId).values().stream()
                .sorted(Comparator.comparing(Meal::getDate).reversed())
                .collect(Collectors.toList());
    }
}

