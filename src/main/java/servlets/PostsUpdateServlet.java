package servlets;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dao.UserDao;
import model.Location;
import model.Post;
import model.User;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.service.ServiceRegistry;

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


//            ApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"springExample.xml"});
//            RegionService regionService = (RegionService) context.getBean("regionService");
//            Region spb = new Region("SPbb");
//            regionService.createRegion(spb);


//            Configuration configuration = new Configuration();
//            configuration.configure("hibernate.cfg.xml");
//            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
//            SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
//
//            Session session = sessionFactory.getCurrentSession();
//            session.beginTransaction();
//            Criteria criteria = session.createCriteria(UserDao.class);
//            criteria.addOrder(Order.asc("id"));
//            List<UserDao> users = criteria.list();
//
//            UserDao userDao = new UserDao();
//            User user = userDao.getUserByName("User");
//
            JsonParser parser = new JsonParser();
            Location location = new Gson().
                    fromJson(parser.parse(content.toString()).
                            getAsJsonObject().
                            getAsJsonObject("location"), Location.class);
            String token = parser.parse(content.toString()).
                    getAsJsonObject().
                    getAsJsonPrimitive("token").getAsString();


            List<Post> posts = new ArrayList<>();
            posts.add(new Post(1, "текст на русском", 300,
                    new User(322, "HE JOHN", "@@@", "здесь должен быть путь до ебососа"),
                    new Location("mysorka street", 1, 2)));
            posts.add(new Post(2, "текст на українському", 200,
                    new User(223, "vdv", "@^@^@^", "nbv"),
                    location));

            String jsonUser = new Gson().toJson(posts);
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().write(jsonUser);
            resp.getWriter().flush();
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_BAD_GATEWAY);
        }
    }
}
