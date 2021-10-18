package ru.javawebinar.topjava.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.http.HttpServletRequest;
import java.net.http.HttpRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Controller
@RequestMapping("meals")
public class JspMealController {

    @Autowired
    private MealService mealService;

    @GetMapping("")
    public String getMeals(Model model) {
        model.addAttribute("meals", MealsUtil.getTos(mealService.getAll(SecurityUtil.authUserId()), SecurityUtil.authUserCaloriesPerDay()));
        return "meals";
    }

    @RequestMapping(value = "/filter", method = RequestMethod.GET)
    public String getFilteredMeals(@RequestParam("startDate") String startDate,
                                   @RequestParam("endDate") String endDate,
                                   @RequestParam("startTime") String startTime,
                                   @RequestParam("endTime") String endTime,
                                   Model model) {
        List<Meal> filteredDateMeals = mealService.getBetweenInclusive(LocalDate.parse(startDate),
                LocalDate.parse(endDate), SecurityUtil.authUserId());
        model.addAttribute("meals",
                MealsUtil.getFilteredTos(filteredDateMeals, SecurityUtil.authUserCaloriesPerDay(),
                        LocalTime.parse(startTime), LocalTime.parse(endTime)));
        return "meals";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("id") String mealId) {
        mealService.delete(Integer.parseInt(mealId), SecurityUtil.authUserId());
        return "redirect:/meals";
    }

    @GetMapping("/update")
    public String update(@RequestParam("id") String mealId,
                         @RequestParam("dateTime") String dateTime,
                         @RequestParam("description") String description,
                         @RequestParam("calories") String calories
    ) {
        Meal meal = new Meal(LocalDateTime.parse(dateTime), description, Integer.parseInt(calories));
        meal.setId(Integer.parseInt(mealId));
        mealService.update(meal, SecurityUtil.authUserId());
        return "redirect:/meals";
    }

    @GetMapping("/create")
    public String create(@ModelAttribute("dateTime") String dateTime,
                         @ModelAttribute("description") String description,
                         @ModelAttribute("calories") String calories) {
        Meal meal = new Meal(LocalDateTime.parse(dateTime), description, Integer.parseInt(calories));
        mealService.create(meal, SecurityUtil.authUserId());
        return "redirect:/meals";
    }

    @GetMapping("/getForm")
    public String getForm(Model model, HttpServletRequest request) {
        if (request.getParameter("id") == null) {
            model.addAttribute("meal",
                    new Meal(LocalDateTime.now(), "", SecurityUtil.authUserCaloriesPerDay()));
        } else {
            Meal meal = new Meal(
                    LocalDateTime.parse(request.getParameter("dateTime")),
                    request.getParameter("description"),
                    Integer.parseInt(request.getParameter("calories")));
            meal.setId(Integer.parseInt(request.getParameter("id")));

            model.addAttribute("meal", meal);
        }
        return "/mealForm";
    }

    @GetMapping("/choose")
    public String createOrUpdate(@RequestParam(value = "id", required = false) Integer mealId ,
                                 @RequestParam("dateTime") String dateTime,
                                 @RequestParam("description") String description,
                                 @RequestParam("calories") String calories,
                                 Model model) {
        model.addAttribute("dateTime", dateTime);
        model.addAttribute("description", description);
        model.addAttribute("calories", calories);

        if (mealId == null) {
            return "redirect:/meals/create";
        } else {
            model.addAttribute("id", mealId);
            return "redirect:/meals/update";
        }
    }
}
