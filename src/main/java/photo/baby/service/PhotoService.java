package photo.baby.service;

import org.springframework.web.multipart.MultipartFile;
import photo.baby.bean.Photo;
import photo.baby.bean.Prompt;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface PhotoService {

    public File file(String name);

    public Photo save(MultipartFile multipartFile, String name) throws IOException;

    public List<Photo> latestPhotos();

    public Prompt save(Prompt p);

    public Iterable<Photo> all();

}
