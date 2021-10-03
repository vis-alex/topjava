package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.repository.inmemory.InMemoryMealRepository;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.meal.MealRestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class MealServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);

    private ConfigurableApplicationContext appCtx;
    private MealRestController mealRestController;

    @Override
    public void init() {
        appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml");
        mealRestController = appCtx.getBean(MealRestController.class);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");

        Meal meal = new Meal( null,
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));

        if (id.isEmpty()) {
            log.info("Create {}", meal);
            mealRestController.create(SecurityUtil.authUserId(), meal);
        } else {
            meal.setId(Integer.parseInt(id));
            log.info("Update {}", meal);
            mealRestController.update(SecurityUtil.authUserId(), meal, meal.getId());
        }

        response.sendRedirect("meals");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        switch (action == null ? "all" : action) {
            case "filter" :
                LocalDate from_date = request.getParameter("from_date").isEmpty() ?
                        LocalDate.MIN : LocalDate.parse(request.getParameter("from_date"));
                LocalDate to_date = request.getParameter("to_date").isEmpty() ?
                        LocalDate.MAX : LocalDate.parse(request.getParameter("to_date"));
                LocalTime from_time = request.getParameter("from_time").isEmpty() ?
                        LocalTime.MIN : LocalTime.parse(request.getParameter("from_time"));
                LocalTime to_time = request.getParameter("to_time").isEmpty() ?
                        LocalTime.MAX : LocalTime.parse(request.getParameter("to_time"));

                request.setAttribute("meals", MealsUtil.getFilteredTos(mealRestController.getAll(SecurityUtil.authUserId()),
                        SecurityUtil.authUserCaloriesPerDay(), from_date, to_date, from_time, to_time));
                request.getRequestDispatcher("/meals.jsp").forward(request, response);

            case "delete":
                int id = getId(request);
                log.info("Delete {}", id);
                mealRestController.delete(SecurityUtil.authUserId(), id);
                response.sendRedirect("meals");
                break;
            case "create":
            case "update":
                final Meal meal = "create".equals(action) ?
                        new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000) :
                        mealRestController.get(SecurityUtil.authUserId(), getId(request));
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("/mealForm.jsp").forward(request, response);
                break;
            case "all":
            default:
                log.info("getAll");
                request.setAttribute("meals",
                        MealsUtil.getTos(mealRestController.getAll(SecurityUtil.authUserId()), SecurityUtil.authUserCaloriesPerDay()));
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }
}
