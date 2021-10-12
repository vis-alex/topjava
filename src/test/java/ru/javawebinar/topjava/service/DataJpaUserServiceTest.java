package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.MatcherFactory;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;

import java.util.List;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.*;

@ActiveProfiles(Profiles.DATAJPA)
public class DataJpaUserServiceTest extends UserServiceTest{

    @Test
    public void getWithMeals() {
        User user = service.get(ADMIN_ID);
        List<Meal> meals = user.getMeals();

        MatcherFactory.usingIgnoringFieldsComparator("user").assertMatch(meals, List.of(adminMeal2, adminMeal1));
    }
}
