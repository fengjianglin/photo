package photo.baby.controller;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;
import net.sf.json.util.JSONUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import photo.baby.bean.Photo;
import photo.baby.service.PhotoService;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by apple on 16/3/25.
 */
@Controller
@RequestMapping(value = "/")
public class IndexController {

    @Autowired
    private PhotoService photoService;

    @RequestMapping("/")
    public String index(Model model) throws IOException {
        Iterable<Photo> photos = photoService.all();
        model.addAttribute("photos", JSONArray.fromObject(photos));
        return "index";
    }
}
