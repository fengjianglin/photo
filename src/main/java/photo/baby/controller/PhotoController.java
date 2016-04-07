package photo.baby.controller;

import org.apache.commons.fileupload.ProgressListener;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import photo.baby.bean.Photo;
import photo.baby.bean.Prompt;
import photo.baby.entity.Progress;
import photo.baby.ext.PhotoMultipartResolver;
import photo.baby.service.PhotoService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by apple on 16/3/25.
 */
@Controller
@RequestMapping(value = "photo")
public class PhotoController {

    private static final AtomicInteger sequenceGenerator = new AtomicInteger();
    private static final Random random = new Random();

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

    @RequestMapping(value = "prompt", method = {RequestMethod.POST}, produces = "application/json;charset=utf-8")
    @ResponseBody
    public Prompt prompt(int photo_id, int x, int y, String text) {
        Prompt p = new Prompt();
        p.setCreatedAt(new Date());
        p.setPhotoId(photo_id);
        p.setText(text);
        p.setX(x);
        p.setY(y);
        p = photoService.save(p);
        return p;
    }

}
