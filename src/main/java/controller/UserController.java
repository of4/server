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
                userService.updateToken(selectedUser);
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
    public User changeUser(HttpServletRequest request, HttpServletResponse response) {
        User user = new User();
        try (BufferedReader reader = request.getReader()) {
            StringBuilder content = new StringBuilder();
            reader.lines().forEach(content::append);
            user = new Gson().fromJson(content.toString(), User.class);
            if (userService.getUserByToken(user.getToken()) != null) {
                User selectedUser = userService.getUserByToken(user.getToken());
                selectedUser.setEmail(user.getEmail());
                selectedUser.setAdvertiser(user.isAdvertiser());
                selectedUser.setName(user.getName());
                selectedUser.setPassword(user.getPassword());
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

    public String generateToken() {
        int tokenLength = 50;
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