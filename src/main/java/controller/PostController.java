package controller;

import com.google.gson.Gson;
import dao.LocationDao;
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
import java.util.List;

@RestController
@RequestMapping("/spring")
public class PostController {

    @Autowired
    PostService postService;

    @Autowired
    LocationService locationService;

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, value = "/get_posts")
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
                post.setCreateTime(new Timestamp(System.currentTimeMillis()));
                locationService.create(post.getLocation());
                post.setLocationId(post.getLocation().getId());
                postService.create(post);
                return post;
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_BAD_GATEWAY);
        }
        return post;
    }
}
