package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.MatcherFactory;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.model.Meal;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.*;

@ActiveProfiles(Profiles.DATAJPA)
public class DataJpaMealServiceTest extends MealServiceTest{

    @Test
    public void getWithUser() {
        Meal meal = service.get(MEAL1_ID, USER_ID);
        MatcherFactory.usingIgnoringFieldsComparator("meals", "registered").assertMatch(user, meal.getUser());
    }
}
