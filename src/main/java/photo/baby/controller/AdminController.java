package photo.baby.controller;


import net.sf.json.JSONObject;
import org.apache.commons.fileupload.ProgressListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import photo.baby.bean.Photo;
import photo.baby.entity.Progress;
import photo.baby.ext.PhotoMultipartResolver;
import photo.baby.service.PhotoService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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

    private Photo _photo(HttpSession session,
                         HttpServletRequest req,
                         @PathVariable String key,
                         RedirectAttributes attr) {

        Photo photo = null;

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
                        photo = photoService.save(image, imageFilename);
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

        return photo;
    }

    @RequestMapping(value = "photo/{key}", method = {RequestMethod.POST})
    public String photo(HttpSession session,
                        HttpServletRequest req,
                        @PathVariable String key,
                        RedirectAttributes attr) {
        _photo(session, req, key, attr);
        return "redirect:/admin";
    }

    @RequestMapping(value = "photo_json/{key}", method = {RequestMethod.POST})
    @ResponseBody
    public Photo photo_json(HttpSession session,
                            HttpServletRequest req,
                            @PathVariable String key,
                            RedirectAttributes attr) {
        Photo p = _photo(session, req, key, attr);
        return p;
    }

    @RequestMapping(value = "upload_progress", produces = "application/json;charset=utf-8")
    @ResponseBody
    public String upload_progress(HttpSession session,
                                  String key,
                                  @RequestParam(required = false, defaultValue = "") String callback) {
        Progress p = (Progress) session.getAttribute(key);
        if (p == null) {
            p = new Progress();
        }
        if (StringUtils.hasText(callback)) {
            return String.format("%s(%s);", callback, JSONObject.fromObject(p).toString());
        } else {
            return JSONObject.fromObject(p).toString();
        }
    }

    @RequestMapping(value = "photo/{photo_id}", method = {RequestMethod.DELETE})
    public String delete(@PathVariable int photo_id,
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
