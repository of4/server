package controller;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import model.Location;
import model.Post;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
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
public class PostController {

    @Autowired
    PostService postService;

    @Autowired
    LocationService locationService;

    @Autowired
    UserService userService;

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, value = "/get_all_posts")
    public List<Post> getAllPosts() {
        return postService.getAllPosts();
    }

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, value = "/new_post")
    public Post createNewPost(HttpServletRequest request, HttpServletResponse response) {
        Post post = new Post();
        try (BufferedReader reader = request.getReader()) {
            StringBuilder content = new StringBuilder();
            reader.lines().forEach(content::append);
            post = new Gson().fromJson(content.toString(), Post.class);
            User user = post.getUser();
            if (userService.getUserByToken(user.getToken()) != null) {
                user = userService.getUserByToken(user.getToken());
                postService.create(post, user);
                return post;
            } else {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_BAD_GATEWAY);
        }
        return post;
    }

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, value = "/get_posts")
    public List<Post> getPostsInLocationNewPost(HttpServletRequest request, HttpServletResponse response) {
        try (BufferedReader reader = request.getReader()) {
            StringBuilder content = new StringBuilder();
            reader.lines().forEach(content::append);
            Location location = Parser.getLocation(content.toString());
            String token = Parser.getToken(content.toString());
            String category = Parser.getCategory(content.toString());
            if (userService.getUserByToken(token) != null) {
                return postService.getNearPosts(location, category);
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_BAD_GATEWAY);
        }
        return new ArrayList<>();
    }

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, value = "/new_favorite")
    public String addToFavorite(HttpServletRequest request, HttpServletResponse response) {
        try (BufferedReader reader = request.getReader()) {
            StringBuilder content = new StringBuilder();
            reader.lines().forEach(content::append);
            String token = Parser.getToken(content.toString());
            int postId = Parser.getPostId(content.toString());
            if (userService.getUserByToken(token) != null) {
                User user = userService.getUserByToken(token);
                postService.addToFavorite(user.getId(), postId);
                response.setStatus(HttpServletResponse.SC_OK);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_BAD_GATEWAY);
        }
        return "";
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
                posts.addAll(postService.getFavorites(user.getId()));
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_BAD_GATEWAY);
        }
        return posts;
    }
}
