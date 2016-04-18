package photo.baby.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import photo.baby.bean.User;

import javax.servlet.http.HttpServletRequest;

@Controller
public class BaseController {

    @ModelAttribute("user")
    public User getUser(HttpServletRequest request) {
        return (User) request.getAttribute("user");
    }

}
