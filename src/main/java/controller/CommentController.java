package controller;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import model.Comment;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import service.CommentService;
import service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;

@RestController
public class CommentController {

    @Autowired
    CommentService commentService;

    @Autowired
    UserService userService;

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, value = "/add_comment")
    public void userAuthentication(HttpServletRequest request, HttpServletResponse response) {
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
            String text = parser.parse(content.toString()).
                    getAsJsonObject().
                    getAsJsonPrimitive("text").getAsString();
            if (userService.getUserByToken(token) != null) {
                User user = userService.getUserByToken(token);
                commentService.create(postId, user.getId(), text);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_BAD_GATEWAY);
        }
    }

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, value = "/comments")
    public List<Comment> getCommetsForThisPost(HttpServletRequest request, HttpServletResponse response) {
        List<Comment> comments = new ArrayList<>();
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
            if (userService.getUserByToken(token) != null) {
                comments.addAll(commentService.getCommentsForThisPost(postId));
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_BAD_GATEWAY);
        }
        return comments;
    }
}
