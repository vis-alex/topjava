package ru.javawebinar.topjava.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class JspMealController {

    @Autowired
    private MealService mealService;

    @GetMapping("/meals")
    public String getMeals(Model model) {
        model.addAttribute("meals", mealService.getAll(SecurityUtil.authUserId()));
        return "meals";
    }

    @GetMapping("/filter")
    public String getFilteredMeals(@RequestParam("startDate") LocalDateTime startDate,
                                   @RequestParam("endDate") LocalDateTime endDate,
                                   @RequestParam("startTime") LocalDateTime startTime,
                                   @RequestParam("endTime") LocalDateTime endTime,
                                   Model model) {
        List<Meal> filteredDateMeals = mealService.getBetweenInclusive(startDate.toLocalDate(), endDate.toLocalDate(), SecurityUtil.authUserId());
        model.addAttribute("meals",
                MealsUtil.getFilteredTos(filteredDateMeals, SecurityUtil.authUserCaloriesPerDay(), startTime.toLocalTime(), endTime.toLocalTime()));
        return "meals";
    }

    @PostMapping("/deleteMeal")
    public String deleteMeal(@RequestParam("id") int mealId) {
        mealService.delete(mealId, SecurityUtil.authUserId());
        return "redirect:/meals";
    }

    @PostMapping("updateMeal")
    public String updateMeal(@RequestParam("id") int mealId,
                             @RequestParam("dateTime") LocalDateTime dateTime,
                             @RequestParam("description") String description,
                             @RequestParam("calories") int calories
    ) {
        Meal meal = new Meal(dateTime, description, calories);
        meal.setId(mealId);
        mealService.update(meal, SecurityUtil.authUserId());
        return "redirect:/meals";
    }

    @PostMapping("createMeal")
    public String createMeal(@RequestParam("dateTime") LocalDateTime dateTime,
                             @RequestParam("description") String description,
                             @RequestParam("calories") int calories) {
        Meal meal = new Meal(dateTime, description, calories);
        mealService.create(meal, SecurityUtil.authUserId());
        return "redirect:/meals";
    }
}
