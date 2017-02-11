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
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static util.Constants.JDBC_DATABASE_URL;

@WebServlet(name = "AuthenticationServlet", urlPatterns = {"/authentication"})
public class AuthenticationServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (BufferedReader reader = req.getReader()) {
            StringBuilder content = new StringBuilder();
            reader.lines().forEach(content::append);

            User user = new Gson().fromJson(content.toString(), User.class);

            if (user.getEmail().equals("a@a.ru")) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            user.setToken("Вошел");
            user.setName(user.getEmail());

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

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
//        try (Connection connection = DriverManager.getConnection(JDBC_DATABASE_URL)) {
//            Statement statement = connection.createStatement();
//            ResultSet resultSet = statement.executeQuery("SELECT * FROM users");
//            StringBuilder stringBuilder = new StringBuilder();
//            while (resultSet.next()) {
//                stringBuilder.append(resultSet.getString("name"));
//                stringBuilder.append(resultSet.getString("email"));
//                stringBuilder.append(resultSet.getString("password"));
//                stringBuilder.append("\n");
//            }
//            response.setContentType("text/html;charset=utf-8");
//            response.setStatus(HttpServletResponse.SC_OK);
//            PrintWriter out = response.getWriter();
//            out.print(stringBuilder);
//            out.flush();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }

    }
}
