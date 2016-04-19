package photo.baby.controller;

import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import photo.baby.bean.Photo;
import photo.baby.bean.User;
import photo.baby.entity.AccessToken;
import photo.baby.service.PhotoService;

import javax.servlet.ServletContext;
import java.io.IOException;

/**
 * Created by apple on 16/3/25.
 */
@Controller
@RequestMapping(value = "/")
public class IndexController extends BaseController {

    @Autowired
    private PhotoService photoService;

    @Autowired
    private ServletContext servletContext;

    @RequestMapping("/")
    public String index(@ModelAttribute("user") User user,
                        Model model) throws IOException {
        return index_token(user, "", model);
    }

    @RequestMapping("photos")
    public String photos(Model model) throws IOException {
        Iterable<Photo> photos = photoService.all();
        model.addAttribute("photos", JSONArray.fromObject(photos));
        return "photos";
    }


    @RequestMapping("{token:.*}")
    public String index_token(@ModelAttribute("user") User user,
                              @PathVariable String token,
                              Model model) throws IOException {
        long time = 0;
        if (user == null) {
            time = validateToken(token);
            if (time <= 0) {
                return "no_permission";
            } else {
                model.addAttribute("time", time);
            }
        }
        Iterable<Photo> photos = photoService.all();
        model.addAttribute("photos", JSONArray.fromObject(photos));
        return "index";
    }

    private long validateToken(String token) {
        try {
            AccessToken at = (AccessToken) servletContext.getAttribute(token);
            return at.getExpired() - System.currentTimeMillis();
        } catch (Exception e) {
        }
        return 0;
    }
}
