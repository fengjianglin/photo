package photo.baby.controller;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import photo.baby.bean.Photo;
import photo.baby.service.PhotoService;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by apple on 16/3/25.
 */
@Controller
@RequestMapping(value = "/")
public class IndexController {

    @Autowired
    private PhotoService photoService;

    @RequestMapping("/")
    public String index(@RequestParam(required = false, defaultValue = "0") int p,
                        Model model) throws IOException {
        p--;
        p = p < 0 ? 0 : p;

        List<Photo> photos = photoService.latestPhotos(p);

        if (photos.size() < 10) {
            p = 0;
            photos = photoService.latestPhotos(p);
        }

        model.addAttribute("photos", photos);
        model.addAttribute("page", p + 1);
        return "index";
    }


}
