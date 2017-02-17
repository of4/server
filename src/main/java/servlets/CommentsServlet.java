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
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "CommentsServlet", urlPatterns = {"/comments"})
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

            List<Comment> comments = new ArrayList<>();
//            comments.add(new Comment("3"));
//            comments.add(new Comment("2"));

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
