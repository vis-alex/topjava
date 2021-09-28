package ru.javawebinar.topjava.web;
import org.slf4j.Logger;
import ru.javawebinar.topjava.Main;
import ru.javawebinar.topjava.dao.InMemoryMealRepositoryImpl;
import ru.javawebinar.topjava.dao.MealRepository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(UserServlet.class);

    private final MealRepository mealRepository = InMemoryMealRepositoryImpl.getMealMapInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("forward to meals");

        List<MealTo> result = MealsUtil.mealToConverter(mealRepository.getAllMeals());
        req.setAttribute("meals", result);
        req.getRequestDispatcher("meals.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        System.out.println(req.getParameter("date"));
//        if("create".equals(req.getParameter("action"))) {
            mealRepository.create(new Meal(
                    LocalDateTime.parse(req.getParameter("date")),
                    req.getParameter("description"),
                    Integer.parseInt(req.getParameter("calories"))
            ));
//        }
//        else if ("update".equals(req.getParameter("action"))) {
//            mealRepository.update();
//        }

        List<MealTo> result = MealsUtil.mealToConverter(mealRepository.getAllMeals());
        req.setAttribute("meals", result);
        req.getRequestDispatcher("meals.jsp").forward(req, resp);
    }
}
