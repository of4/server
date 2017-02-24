package controller;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import model.Comment;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@RestController
public class PostController {

    @Autowired
    PostService postService;

    @Autowired
    LocationService locationService;

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, value = "/get_all_posts")
    public List<Post> getAllPosts() {
        return postService.getAllPosts();
    }

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, value = "/new_post")
    public Post createNewPost(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        Post post = new Post();
        try (BufferedReader reader = request.getReader()) {
            StringBuilder content = new StringBuilder();
            reader.lines().forEach(content::append);
            post = new Gson().fromJson(content.toString(), Post.class);
            User user = post.getUser();
            if (session.getAttribute(user.getToken()) != null) {
                user = (User) session.getAttribute(user.getToken());
                locationService.create(post.getLocation());
                post.setLocationId(post.getLocation().getId());
                post.setUserId(user.getId());
                postService.create(post);
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
    public List<Post> getPostsInLocationNewPost(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        try (BufferedReader reader = request.getReader()) {
            StringBuilder content = new StringBuilder();
            reader.lines().forEach(content::append);

            JsonParser parser = new JsonParser();
            Location location = new Gson().
                    fromJson(parser.parse(content.toString()).
                            getAsJsonObject().
                            getAsJsonObject("location"), Location.class);
            String token = parser.parse(content.toString()).
                    getAsJsonObject().
                    getAsJsonPrimitive("token").getAsString();

            if (session.getAttribute(token) != null) {
                return postService.getNearPosts(location);
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
    public void addToFavorite(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        try (BufferedReader reader = request.getReader()) {
            StringBuilder content = new StringBuilder();
            reader.lines().forEach(content::append);
            JsonParser parser = new JsonParser();
            String token = parser.parse(content.toString()).
                    getAsJsonObject().
                    getAsJsonPrimitive("token").getAsString();
            int postId = Integer.parseInt(parser.parse(content.toString()).
                    getAsJsonObject().
                    getAsJsonPrimitive("postId").getAsString());
            if (session.getAttribute(token) != null) {
                User user = (User) session.getAttribute(token);
                postService.addToFavorite(user.getId(), postId);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_BAD_GATEWAY);
        }
    }


    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, value = "/get_favorites")
    public List<Post> getFavorites(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        List<Post> posts = new ArrayList<>();
        try (BufferedReader reader = request.getReader()) {
            StringBuilder content = new StringBuilder();
            reader.lines().forEach(content::append);
            JsonParser parser = new JsonParser();
            String token = parser.parse(content.toString()).
                    getAsJsonObject().
                    getAsJsonPrimitive("token").getAsString();
            if (session.getAttribute(token) != null) {
                User user = (User) session.getAttribute(token);
                posts.addAll(postService.getFavorites(user.getId()));
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_BAD_GATEWAY);
        }
        return posts;
    }
}
