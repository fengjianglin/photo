package photo.baby.service;

import org.springframework.web.multipart.MultipartFile;
import photo.baby.bean.Label;
import photo.baby.bean.Photo;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

public interface PhotoService {

    public File file(String name);

    public Photo save(MultipartFile multipartFile, String name) throws IOException;

    public Photo save(String base64, String name) throws IOException;

    public boolean delete(Photo photo);

    public List<Photo> latestPhotos(int page, int size);

    public List<Photo> latestPhotos(int page);

    public Label save(Label p);

    public Iterable<Photo> all();

    public Photo find(int id);

    public long count();

}
