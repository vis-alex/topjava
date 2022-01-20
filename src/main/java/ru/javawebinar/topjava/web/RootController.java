package ru.javawebinar.topjava.web;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.MealsUtil;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;

@ApiIgnore
@Controller
public class RootController {

    @GetMapping("/")
    public String root() {
        return "redirect:meals";
    }

    //    @Secured("ROLE_ADMIN")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users")
    public String getUsers() {
        return "users";
    }

//    @PostMapping("/users")
//    public String setUser(HttpServletRequest request) {
//        int userId = Integer.parseInt(request.getParameter("userId"));
//        SecurityUtil.setAuthUserId(userId);
//        return "redirect:meals";
//    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/meals")
    public String getMeals() {
        return "meals";
    }
}
