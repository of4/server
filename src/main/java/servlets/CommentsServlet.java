package servlets;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import model.Comment;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "CommentsServlet", urlPatterns = "comments")
public class CommentsServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (BufferedReader reader = req.getReader()) {
            StringBuilder content = new StringBuilder();
            reader.lines().forEach(content::append);

            JsonParser parser = new JsonParser();
            JsonObject jsonObject = parser.parse(content.toString()).getAsJsonObject();

            String token = jsonObject.getAsJsonPrimitive("token").getAsString();
            String postId = jsonObject.getAsJsonPrimitive("postId").getAsString();
            String text = jsonObject.getAsJsonPrimitive("text").getAsString();

            List<Comment> comments = new ArrayList<>();
            comments.add(new Comment(1, 2, "text", 21,
                    new User(1, "cvs", "fd", "c")));
            comments.add(new Comment(1, 2, "pidor vyshe", 21,
                    new User(4, postId, text, "c")));

            String jsonUser = new Gson().toJson(comments);
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().write(jsonUser);
            resp.getWriter().flush();
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_BAD_GATEWAY);
        }
    }
}
