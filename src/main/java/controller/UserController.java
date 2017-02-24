package controller;

import com.google.gson.Gson;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.util.List;
import java.util.Random;

@RestController
public class UserController {

    @Autowired
    HttpSession session;

    @Autowired
    UserService userService;

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, value = "/registration")
    public User userRegistration(HttpServletRequest request, HttpServletResponse response) {
        User user = new User();
        try (BufferedReader reader = request.getReader()) {
            StringBuilder content = new StringBuilder();
            reader.lines().forEach(content::append);
            user = new Gson().fromJson(content.toString(), User.class);
            if (userService.getAll().contains(user)) {
                response.setStatus(HttpServletResponse.SC_CONFLICT);
            } else {
                String token = generateToken();
                user.setToken(token);
                user.setName(user.getEmail());
                userService.create(user);
                session.setAttribute(token, user);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_BAD_GATEWAY);
        }
        return user;
    }

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, value = "/authentication")
    public User userAuthentication(HttpServletRequest request, HttpServletResponse response) {
        User user = new User();
        try (BufferedReader reader = request.getReader()) {
            StringBuilder content = new StringBuilder();
            reader.lines().forEach(content::append);
            user = new Gson().fromJson(content.toString(), User.class);
            User selectedUser = userService.getByEmailAndPassword(user.getEmail(), user.getPassword());
            if (selectedUser != null) {
                String token = generateToken();
                selectedUser.setToken(token);
                session.setAttribute(token, selectedUser);
                return selectedUser;
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_BAD_GATEWAY);
        }
        return user;
    }

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, value = "/change_user")
    public User changeUser(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        User user = new User();
        try (BufferedReader reader = request.getReader()) {
            StringBuilder content = new StringBuilder();
            reader.lines().forEach(content::append);
            user = new Gson().fromJson(content.toString(), User.class);
            if (session.getAttribute(user.getToken()) != null) {
                User selectedUser = (User) session.getAttribute(user.getToken());
                selectedUser.setEmail(user.getEmail());
                selectedUser.setAdvertiser(user.isAdvertiser());
                selectedUser.setName(user.getName());
                userService.update(selectedUser);
                return selectedUser;
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_BAD_GATEWAY);
        }
        return user;
    }

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, value = "/get_users")
    public List<User> getAllUsers(HttpServletResponse response, HttpSession session) {
        return userService.getAll();
    }

//    @RequestMapping(method = RequestMethod.GET, value = "/users/{id}")
//    public User getUser(@PathVariable("id") String userId, ModelMap model) {
//        return userService.getById(Long.parseLong(userId));
//    }

    public String generateToken() {
        int tokenLength = 30;
        char[] chars = "1234567890abcdefghijklmnopqrstuvwxyz".toCharArray();
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < tokenLength; i++) {
            char c = chars[random.nextInt(chars.length)];
            sb.append(c);
        }
        return sb.toString();
    }
}