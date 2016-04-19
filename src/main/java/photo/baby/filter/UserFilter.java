package photo.baby.filter;

import photo.baby.bean.User;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;

public class UserFilter implements Filter {

    public void init(FilterConfig filterConfig) throws ServletException {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;

        String cookies_token = null;
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                if ("token".equals(c.getName())) {
                    cookies_token = c.getValue();
                    break;
                }
            }
        }

        if (validateToken(req.getSession(), cookies_token)) {
            User user = new User();
            user.setUsername("admin");
            req.setAttribute("user", user);
        }
        chain.doFilter(request, response);
    }

    public void destroy() {

    }

    private boolean validateToken(HttpSession session, String token) {
        try {
            Date date = (Date) session.getAttribute(token);
            return new Date().before(date);
        } catch (Exception e) {
        }
        return false;
    }
}
