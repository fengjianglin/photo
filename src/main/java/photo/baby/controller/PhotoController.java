package photo.baby.controller;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import photo.baby.bean.Comment;
import photo.baby.service.PhotoService;

import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * Created by apple on 16/3/25.
 */
@Controller
@RequestMapping(value = "photo")
public class PhotoController extends BaseController {

    @Autowired
    private PhotoService photoService;

    @RequestMapping("{name:.*}")
    public ResponseEntity<byte[]> download(@PathVariable String name) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", name);

        File image = photoService.file(name);
        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(image), headers, HttpStatus.OK);
    }

    @RequestMapping(value = "label", method = {RequestMethod.POST}, produces = "application/json;charset=utf-8")
    @ResponseBody
    public Comment label(int photo_id, int x, int y, String text) {
        Comment p = new Comment();
        p.setCreatedAt(new Date());
        p.setPhotoId(photo_id);
        p.setText(text);
        p.setX(x);
        p.setY(y);
        p = photoService.save(p);
        return p;
    }

}
