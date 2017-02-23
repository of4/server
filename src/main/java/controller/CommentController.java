package controller;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import service.CommentService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;

@RestController
@RequestMapping("/spring")
public class CommentController {

    @Autowired
    CommentService commentService;

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, value = "/add_comment")
    public void userAuthentication(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
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
            if (session.getAttribute(token) != null) {
                User user = (User) session.getAttribute(token);
                commentService.create(postId, user.getId(), text);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_BAD_GATEWAY);
        }
    }
}
