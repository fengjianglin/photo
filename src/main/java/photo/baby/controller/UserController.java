package photo.baby.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import photo.baby.bean.User;
import photo.baby.entity.AccessToken;
import photo.baby.utils.Utils;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Date;

/**
 * Created by apple on 16/3/25.
 */
@Controller
@RequestMapping(value = "user")
public class UserController extends BaseController {

    @Autowired
    private ServletContext servletContext;

    @Value("#{props['access_token_expired']}")
    private long access_token_expired;

    @RequestMapping(value = "login", method = {RequestMethod.GET}, produces = "text/html;charset=utf-8")
    public String _login(@RequestParam(required = false, defaultValue = "/") String from, Model model) {
        model.addAttribute("from", from);
        return "login";
    }

    @RequestMapping(value = "login", method = {RequestMethod.POST}, produces = "text/html;charset=utf-8")
    public String login(HttpSession session,
                        HttpServletResponse response,
                        String username,
                        String password,
                        @RequestParam(required = false, defaultValue = "/") String from) {
        if ("admin".equals(username) && "abc123".equals(password)) {
            String token = Utils.token();
            session.setAttribute(token, new Date(System.currentTimeMillis() + 1000 * 60 * 60));
            Cookie cookie = new Cookie("token", token);
            cookie.setPath("/");
            response.addCookie(cookie);
            return "redirect:" + from;
        }
        return "login";
    }

    @RequestMapping(value = "generate_access", method = {RequestMethod.GET}, produces = "application/json;charset=utf-8")
    @ResponseBody
    public AccessToken generate_access(HttpServletRequest request, @ModelAttribute("user") User user) {
        AccessToken at = new AccessToken();
        if (user == null || !"admin".equals(user.getUsername())) {
            return at;
        }
        at.setToken(Utils.accessToken());
        at.setStatus(1);
        at.setExpired(System.currentTimeMillis() + 1000 * 60 * access_token_expired);
        try {
            URL url = new URL(request.getRequestURL().toString());
            String host = url.getHost();
            String scheme = url.getProtocol();
            int port = url.getPort();
            URI uri = new URI(scheme, null, host, port, "/" + at.getToken(), null, null);
            at.setUrl(uri.toString());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        servletContext.setAttribute(at.getToken(), at);
        return at;
    }

}
