package photo.baby.service.impl;

import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifIFD0Directory;
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
import sun.awt.image.ImageAccessException;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.IIOByteBuffer;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
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


        File tempImage = new File(fileDir, "tmp_" + name);
        File image = new File(fileDir, name);


        InputStream inputStream = multipartFile.getInputStream();

        OutputStream outputStream = new FileOutputStream(image);

        compressImage(inputStream, outputStream);

        BufferedImage bufferedImage = ImageIO.read(image);


        int orientation = 1;
        try {
            Metadata metadata = ImageMetadataReader.readMetadata(image);
            ExifIFD0Directory exifIFD0Directory = metadata.getFirstDirectoryOfType(ExifIFD0Directory.class);
            orientation = exifIFD0Directory.getInt(ExifIFD0Directory.TAG_ORIENTATION);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Photo photo = new Photo();
        photo.setName(name);
        if (orientation > 4) {
            photo.setWidth(bufferedImage.getHeight());
            photo.setHeight(bufferedImage.getWidth());
        } else {
            photo.setWidth(bufferedImage.getWidth());
            photo.setHeight(bufferedImage.getHeight());
        }

        photo.setSize(multipartFile.getSize());
        photo.setCreatedAt(new Date());
        return photoRepository.save(photo);
    }

    private void compressImage(InputStream inputStream, OutputStream outputStream) throws IOException {
        float imageQuality = 0.3f;
        BufferedImage bufferedImage = ImageIO.read(inputStream);
        Iterator<ImageWriter> imageWriters = ImageIO.getImageWritersByFormatName("jpg");
        if (!imageWriters.hasNext()) {
            throw new IllegalStateException("Writers Not Found!!");
        }
        ImageWriter imageWriter = imageWriters.next();
        ImageOutputStream imageOutputStream = ImageIO.createImageOutputStream(outputStream);
        imageWriter.setOutput(imageOutputStream);

        ImageWriteParam imageWriteParam = imageWriter.getDefaultWriteParam();

        imageWriteParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        imageWriteParam.setCompressionQuality(imageQuality);

        //Created image
        imageWriter.write(null, new IIOImage(bufferedImage, null, null), imageWriteParam);
        inputStream.close();
        outputStream.close();
        imageOutputStream.close();
        imageWriter.dispose();
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
