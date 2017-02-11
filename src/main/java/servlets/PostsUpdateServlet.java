package servlets;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
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

@WebServlet(name = "PostsUpdateServlet", urlPatterns = {"/get_posts"})
public class PostsUpdateServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (BufferedReader reader = req.getReader()) {
            StringBuilder content = new StringBuilder();
            reader.lines().forEach(content::append);

            JsonParser parser = new JsonParser();
//            Location location = new Gson().
//                    fromJson(parser.parse(content.toString()).
//                            getAsJsonObject().
//                            getAsJsonObject("location").
//                            getAsString(), Location.class);
            String token = parser.parse(content.toString()).
                    getAsJsonObject().
                    getAsJsonPrimitive("token").getAsString();

            List<Post> posts = new ArrayList<>();
            posts.add(new Post(1, "текст на русском", 300,
                    new User(322, "HE JOHN", "@@@", "здесь должен быть путь до ебососа"),
                    new Location("mysorka street", 1, 2)));
            posts.add(new Post(2, "текст на українському", 200,
                    new User(223, "HE fd", "@^@^@^", "//"),
                    location));

            String jsonUser = new Gson().toJson(posts);
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
