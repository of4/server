package controller;


import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import service.UserService;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping(method = RequestMethod.GET, value = "/users")
    public List<User> getAllUsers(ModelMap model, HttpServletResponse response, HttpSession session) {
        List<User> users = userService.getAll();
        response.setStatus(HttpServletResponse.SC_CONFLICT);
        session.setAttribute("user", users.get(0));
        return users;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/session")
    public User getUserFromSession(ModelMap model, HttpServletResponse response, HttpSession session) {
        if (session.getAttribute("user") != null) {
            User user = (User) session.getAttribute("user");
            user.setName("OK");
            return user;
        }
        return new User(1, "1", "1", "1");
    }

    @RequestMapping(method = RequestMethod.GET, value = "/users/{id}")
    public User getUser(@PathVariable("id") String userId, ModelMap model) {
        return userService.getById(Long.parseLong(userId));
    }

}