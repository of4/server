package servlets;

import com.google.gson.Gson;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

@WebServlet(name = "RegistrationServlet", urlPatterns = {"/registration"})
public class RegistrationServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        try (BufferedReader reader = req.getReader()) {
            StringBuilder content = new StringBuilder();
            reader.lines().forEach(content::append);

            User user = new Gson().fromJson(content.toString(), User.class);
            user.setToken("kakoi nahui token? sovsem ebanuls9I?");
            user.setName("xyi tebe a ne im9I");

            String jsonUser = new Gson().toJson(user);
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
