package servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        processRequest(req, resp);
//    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Connection connection = DriverManager.getConnection(JDBC_DATABASE_URL);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users");
            StringBuilder stringBuilder = new StringBuilder();
            while (resultSet.next()) {
                stringBuilder.append(resultSet.getString("name"));
                stringBuilder.append(resultSet.getString("email"));
                stringBuilder.append(resultSet.getString("password"));
                stringBuilder.append("\n");
            }
            response.setContentType("text/html;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_OK);
            PrintWriter out = response.getWriter();
            out.print(stringBuilder);
            out.flush();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
