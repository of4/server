package controller;

import model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SpringController {


//    @RequestMapping(value = "/hello", method = RequestMethod.GET)
//    public String sayHello(ModelMap model) {
//        model.addAttribute("message", "Spring 3 MVC Hello World");
//        return "hello";
//    }
//
//    @RequestMapping(value = "/bye" ,method = RequestMethod.GET)
//    public User sayBye(ModelMap model) {
//        model.addAttribute("message", "Spring 3 MVC bye-bye!");
//        return new User(1, "2", "3", "4");
//    }
//
//    @RequestMapping(value = {"/hello2", "/hello3"} ,method = {RequestMethod.GET, RequestMethod.POST},
//            headers = "User-Agent: Android")
//    public ModelAndView sayHello2(ModelMap model) {
//        model.addAttribute("message", "Spring 3 MVC Hello World 2");
//        return new ModelAndView("hello", model);
//    }

    /*
        @RequestMapping(method = RequestMethod.GET, value = "/users")
    public List<User> getAllUsers(ModelMap model, HttpServletResponse response, HttpSession session) {
        List<User> users = userService.getAll();
        response.setStatus(HttpServletResponse.SC_CONFLICT);
        session.setAttribute("user", users.get(0));
        return users;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/session")
    public User getUserFromSession(ModelMap model, HttpServletResponse response, HttpSession session) {
        if (session.getAttribute("user") != null) {
            User user = (User) session.getAttribute("user");
            user.setName("OK");
            return user;
        }
        return new User(1, "1", "1", "1");
    }

    @RequestMapping(method = RequestMethod.GET, value = "/users/{id}")
    public User getUser(@PathVariable("id") String userId, ModelMap model) {
        return userService.getById(Long.parseLong(userId));
    }
    * */

    //    @RequestMapping(method = RequestMethod.GET, value = "/users/{id}")
//    public User getUser(@PathVariable("id") String userId, ModelMap model) {
//        return userService.getById(Long.parseLong(userId));
//    }
}
