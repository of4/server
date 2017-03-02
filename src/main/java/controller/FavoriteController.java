package controller;

import com.google.gson.Gson;
import model.Location;
import model.Post;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import service.FavoriteService;
import service.LocationService;
import service.PostService;
import service.UserService;
import util.Parser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;

@RestController
public class FavoriteController {

    @Autowired
    FavoriteService favoriteService;

    @Autowired
    UserService userService;

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, value = "/new_favorite")
    public void addToFavorite(HttpServletRequest request, HttpServletResponse response) {
        try (BufferedReader reader = request.getReader()) {
            StringBuilder content = new StringBuilder();
            reader.lines().forEach(content::append);
            String token = Parser.getToken(content.toString());
            int postId = Parser.getPostId(content.toString());
            if (userService.getUserByToken(token) != null) {
                User user = userService.getUserByToken(token);
                if (favoriteService.isFavorite(user.getId(), postId)) {
                    favoriteService.delete(user.getId(), postId);
                } else {
                    favoriteService.addToFavorite(user.getId(), postId);
                }
                response.setStatus(HttpServletResponse.SC_OK);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_BAD_GATEWAY);
        }
    }

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, value = "/get_favorites")
    public List<Post> getFavorites(HttpServletRequest request, HttpServletResponse response) {
        List<Post> posts = new ArrayList<>();
        try (BufferedReader reader = request.getReader()) {
            StringBuilder content = new StringBuilder();
            reader.lines().forEach(content::append);
            String token = Parser.getToken(content.toString());
            if (userService.getUserByToken(token) != null) {
                User user = userService.getUserByToken(token);
                posts.addAll(favoriteService.getFavorites(user.getId()));
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_BAD_GATEWAY);
        }
        return posts;
    }
}
