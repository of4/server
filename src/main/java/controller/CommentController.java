package controller;

import model.Comment;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import service.CommentService;
import service.UserService;
import util.Parser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    public void addComment(HttpServletRequest request, HttpServletResponse response) {
        try (BufferedReader reader = request.getReader()) {
            StringBuilder content = new StringBuilder();
            reader.lines().forEach(content::append);
            String token = Parser.getToken(content.toString());
            int postId = Parser.getPostId(content.toString());
            String text = Parser.getText(content.toString());
            if (userService.getUserByToken(token) != null) {
                User user = userService.getUserByToken(token);
                commentService.create(postId, user.getId(), text);
                response.setStatus(HttpServletResponse.SC_OK);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_BAD_GATEWAY);
        }
    }

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, value = "/comments")
    public List<Comment> getCommentsForThisPost(HttpServletRequest request, HttpServletResponse response) {
        List<Comment> comments = new ArrayList<>();
        try (BufferedReader reader = request.getReader()) {
            StringBuilder content = new StringBuilder();
            reader.lines().forEach(content::append);
            String token = Parser.getToken(content.toString());
            int postId = Parser.getPostId(content.toString());
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
