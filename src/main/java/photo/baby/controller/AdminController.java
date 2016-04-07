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
@RequestMapping(value = "admin")
public class AdminController {

    private static final AtomicInteger sequenceGenerator = new AtomicInteger();
    private static final Random random = new Random();

    @Autowired
    private PhotoService photoService;

    @RequestMapping(value = "", method = {RequestMethod.GET}, produces = "text/html;charset=utf-8")
    public String index(Model model) {
        String key = sequenceGenerator.getAndIncrement() + "_" + random.nextInt(Integer.MAX_VALUE);
        model.addAttribute("key", key);
        model.addAttribute("photos", photoService.all());
        return "admin";
    }

    @RequestMapping(value = "photo/{key}", method = {RequestMethod.POST})
    public String photo(HttpSession session,
                        HttpServletRequest req,
                        @PathVariable String key,
                        RedirectAttributes attr) {

        Progress p = (Progress) session.getAttribute(key);
        if (p == null) {
            p = new Progress();
            session.setAttribute(key, p);
        }
        final Progress p2 = p;

        PhotoMultipartResolver resolver = new PhotoMultipartResolver(
                new ProgressListener() {
                    public void update(long pBytesRead, long pContentLength, int pItems) {
                        p2.setStatus(1);
                        p2.setMax(pContentLength);
                        p2.setNow(pBytesRead);
                    }
                });

        if (resolver.isMultipart(req)) {
            MultipartHttpServletRequest multipartRequest = resolver.resolveMultipart(req);
            p2.setStatus(2);
            MultipartFile image = multipartRequest.getFile("image");

            if (!image.isEmpty()) {
                String contentType = image.getContentType();
                if (contentType != null && contentType.startsWith("image/")) {
                    Date now = new Date();
                    String imageFilename = image.getOriginalFilename();
                    imageFilename = now.getTime() + imageFilename;
                    try {
                        Photo photo = photoService.save(image, imageFilename);
                        p2.setSave(image.getSize());
                        p2.setStatus(3);
                        attr.addFlashAttribute("flash", "上传成功");
                    } catch (IOException e) {
                        e.printStackTrace();
                        p2.setStatus(-1);
                        attr.addFlashAttribute("error", "上传失败");
                    }
                } else {
                    p2.setStatus(-1);
                    attr.addFlashAttribute("error", "文件不是图片");
                }
            } else {
                p2.setStatus(-1);
                attr.addFlashAttribute("error", "没有发现文件");
            }
        } else {
            p2.setStatus(-1);
            attr.addFlashAttribute("error", "没有发现文件");
        }

        session.removeAttribute(key);

        return "redirect:/admin";
    }

    @RequestMapping(value = "upload_progress", produces = "application/json;charset=utf-8")
    @ResponseBody
    public Progress upload_progress(HttpSession session, String key) {
        Progress p = (Progress) session.getAttribute(key);
        if (p == null) {
            p = new Progress();
        }
        return p;
    }

    @RequestMapping(value = "photo/{photo_id}", method = {RequestMethod.DELETE})
    public String delete(HttpSession session,
                         HttpServletRequest req,
                         @PathVariable int photo_id,
                         RedirectAttributes attr) {
        Photo photo = photoService.find(photo_id);
        if (photo != null) {
            photoService.delete(photo);
            attr.addFlashAttribute("flash", "删除成功");
        } else {
            attr.addFlashAttribute("error", "没有发现照片");
        }
        return "redirect:/admin";
    }
}
