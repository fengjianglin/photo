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
import java.util.List;

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

    @RequestMapping(value = "{photo_id}/comments", produces = "application/json;charset=utf-8")
    @ResponseBody
    public List<Comment> comments(@PathVariable int photo_id) {
        return photoService.comments(photo_id);
    }

    @RequestMapping(value = "{photo_id}/comment", method = {RequestMethod.POST}, produces = "application/json;charset=utf-8")
    @ResponseBody
    public Comment comment(@PathVariable int photo_id, String text) {
        Comment p = new Comment();
        p.setCreatedAt(new Date());
        p.setPhotoId(photo_id);
        p.setText(text);
        p = photoService.save(p);
        return p;
    }

}
