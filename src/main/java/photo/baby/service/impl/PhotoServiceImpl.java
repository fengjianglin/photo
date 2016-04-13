package photo.baby.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import photo.baby.bean.Photo;
import photo.baby.bean.Prompt;
import photo.baby.repository.PhotoRepository;
import photo.baby.repository.PromptRepository;
import photo.baby.service.AlbumService;
import photo.baby.service.PhotoService;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by apple on 16/3/29.
 */

@Service
public class PhotoServiceImpl implements PhotoService, AlbumService {

    @Autowired
    private PhotoRepository photoRepository;

    @Autowired
    private PromptRepository promptRepository;

    @Value("#{props['fileDir']}")
    private String fileDir;

    @Value("#{props['host']}")
    private String host;

    public File file(String name) {
        return new File(fileDir, name);
    }

    @Override
    public Photo save(MultipartFile multipartFile, String name) throws IOException {
        File image = new File(fileDir, name);
        multipartFile.transferTo(image);
        BufferedImage bufferedImage = ImageIO.read(image);
        Photo photo = new Photo();
        photo.setName(name);
        photo.setWidth(bufferedImage.getWidth());
        photo.setHeight(bufferedImage.getHeight());
        photo.setSize(multipartFile.getSize());
        photo.setCreatedAt(new Date());
        return photoRepository.save(photo);
    }

    public boolean delete(Photo photo) {
        photoRepository.delete(photo);
        new File(fileDir, photo.getName()).delete();
        return true;
    }


    @Override
    public List<Photo> latestPhotos(int page) {
        PageRequest pageRequest = new PageRequest(page, 10, Sort.Direction.DESC, "id");
        List<Photo> list = new ArrayList<Photo>();
        Page<Photo> photos = photoRepository.pageablePhotos(pageRequest);
        for (Photo p : photos) {
            p.setUrl(host + "/photo/" + p.getName());
            list.add(p);
        }
        return list;
    }

    public Prompt save(Prompt p) {
        return promptRepository.save(p);
    }


    public Iterable<Photo> all() {
        List<Photo> list = new ArrayList<Photo>();
        Iterable<Photo> photos = photoRepository.findAllByOrderByIdDesc();
        for (Photo p : photos) {
            p.setUrl(host + "/photo/" + p.getName());
            list.add(p);
        }
        return list;
    }

    public Photo find(int id) {
        Photo p = photoRepository.findOne(id);
        if (p != null) {
            p.setUrl(host + "/photo/" + p.getName());
        }
        return p;
    }
}
