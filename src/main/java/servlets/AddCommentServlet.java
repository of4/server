package servlets;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import model.Location;
import model.Post;
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

@WebServlet(name = "AddCommentServlet", urlPatterns = {"/add_comment"})
public class AddCommentServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (BufferedReader reader = req.getReader()) {
            StringBuilder content = new StringBuilder();
            reader.lines().forEach(content::append);

            JsonParser parser = new JsonParser();
            JsonObject jsonObject = parser.parse(content.toString()).getAsJsonObject();

            String token = jsonObject.getAsJsonPrimitive("token").getAsString();
            int postId = Integer.parseInt(jsonObject.getAsJsonPrimitive("postId").getAsString());
            String text = jsonObject.getAsJsonPrimitive("text").getAsString();

            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_BAD_GATEWAY);
        }
    }
}
