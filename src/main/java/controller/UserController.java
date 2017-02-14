package controller;

import com.google.gson.Gson;
import model.Post;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/spring/users/")
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping(method = RequestMethod.POST, value = "/registration")
    public User userRegistration(HttpServletRequest request,
                                 HttpServletResponse response,
                                 HttpSession session) {
        User user = new User();
        try (BufferedReader reader = request.getReader()) {
            StringBuilder content = new StringBuilder();
            reader.lines().forEach(content::append);

            user = new Gson().fromJson(content.toString(), User.class);

            if (userService.getAll().contains(user)) {
                response.setStatus(HttpServletResponse.SC_CONFLICT);
                return new User();
            }

            user.setToken("Успешно зарегистрирован");
            user.setName(user.getEmail());
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_GATEWAY);
        }
        return user;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/get_all")
    public List<User> getAllUsers(HttpServletResponse response, HttpSession session) {
        List<User> users = userService.getAll();
        response.setStatus(HttpServletResponse.SC_CONFLICT);
        session.setAttribute("user", users.get(0));
        return users;
    }

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, value = "/create")
    public void createUser(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        try (BufferedReader reader = request.getReader()) {
            StringBuilder content = new StringBuilder();
            reader.lines().forEach(content::append);

            User user = new Gson().fromJson(content.toString(), User.class);
//            User user = new User(1, "2", "3", "4");
//            user.setPassword("pass");
//            user.setCreateTime(new Timestamp(300));

            userService.create(user);

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_BAD_GATEWAY);
        }
    }

//    @RequestMapping(method = RequestMethod.POST, value = "/posts")
//    public List<Post> getAllPosts(ModelMap model, HttpServletResponse response, HttpSession session) {
//        List<Post> posts = userService.getAllPosts();
//        return posts;
//    }

    @RequestMapping(method = RequestMethod.GET, value = "/users/{id}")
    public User getUser(@PathVariable("id") String userId, ModelMap model) {
        return userService.getById(Long.parseLong(userId));
    }
}