package ru.javawebinar.topjava.web.meal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import ru.javawebinar.topjava.web.AbstractControllerTest;
import ru.javawebinar.topjava.web.SecurityUtil;
import ru.javawebinar.topjava.web.json.JsonUtil;
;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.javawebinar.topjava.MealTestData.*;

class MealRestControllerTest extends AbstractControllerTest {

    @Autowired
    MealService mealService;

    @Test
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(MealRestController.REST_URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MEAL_TO_MATCHER.contentJson(MealsUtil.getTos(meals, MealsUtil.DEFAULT_CALORIES_PER_DAY)));
    }

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(MealRestController.REST_URL + "/" + MEAL1_ID))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER.contentJson(meal1));
    }

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(MealRestController.REST_URL + "/" + MEAL1_ID))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertThrows(NotFoundException.class, () -> mealService.get(MEAL1_ID, SecurityUtil.authUserId()));
    }

    @Test
    void update() throws Exception {
        Meal updated = MealTestData.getUpdated();

        perform(MockMvcRequestBuilders.put(MealRestController.REST_URL + "/" + MEAL1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isNoContent());

        MATCHER.assertMatch(mealService.get(MEAL1_ID, SecurityUtil.authUserId()), updated);
    }

    @Test
    void createWithLocation() throws Exception {
        Meal created = MealTestData.getNew();

        ResultActions action = perform(MockMvcRequestBuilders.post(MealRestController.REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(created)))
                .andExpect(status().isCreated());

        Meal meal = MATCHER.readFromJson(action);
        int id = meal.id();
        created.setId(id);

        MATCHER.assertMatch(meal, created);
        MATCHER.assertMatch(mealService.get(id, SecurityUtil.authUserId()), created);
    }

    @Test
    void getBetweenFilter() throws Exception {

        perform(MockMvcRequestBuilders.get(MealRestController.REST_URL + "/filter?startDate=2020-01-29T10:15:30&endDate=2020-01-30T10:15:30&startTime=2022-01-30T09:15:30&endTime=2022-01-30T15:15:30"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MEAL_TO_MATCHER.contentJson(MealsUtil.getTos(Arrays.asList(meal2, meal1), SecurityUtil.authUserCaloriesPerDay())));
    }
}