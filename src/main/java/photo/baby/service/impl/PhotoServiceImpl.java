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
import photo.baby.bean.Comment;
import photo.baby.bean.Photo;
import photo.baby.repository.CommentRepository;
import photo.baby.repository.PhotoRepository;
import photo.baby.service.AlbumService;
import photo.baby.service.PhotoService;
import sun.misc.BASE64Decoder;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
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
    private CommentRepository commentRepository;

    @Value("#{props['fileDir']}")
    private String fileDir;

    @Value("#{props['host']}")
    private String host;

    public File file(String name) {
        return new File(fileDir, name);
    }


    public Photo save(String base64, String name) throws IOException {
        File image = new File(fileDir, name);
        OutputStream outputStream = new FileOutputStream(image);

        byte[] bytes = new BASE64Decoder().decodeBuffer(base64.split(",")[1]);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);

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

        photo.setSize(bytes.length);
        photo.setCreatedAt(new Date());
        return photoRepository.save(photo);
    }

    @Override
    public Photo save(MultipartFile multipartFile, String name) throws IOException {

        File image = new File(fileDir, name);

        InputStream inputStream = multipartFile.getInputStream();

        OutputStream outputStream = new FileOutputStream(image);

        compressImage(inputStream, outputStream);

        BufferedImage bufferedImage = ImageIO.read(image);

        Photo photo = new Photo();

        photo.setName(name);
        photo.setWidth(bufferedImage.getWidth());
        photo.setHeight(bufferedImage.getHeight());

        photo.setSize(image.length());
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

    public List<Photo> latestPhotos(int page, int size) {
        PageRequest pageRequest = new PageRequest(page, size, Sort.Direction.DESC, "id");
        List<Photo> list = new ArrayList<Photo>();
        Page<Photo> photos = photoRepository.pageablePhotos(pageRequest);
        for (Photo p : photos) {
            p.setUrl(host + "/photo/" + p.getName());
            list.add(p);
        }
        return list;
    }

    @Override
    public List<Photo> latestPhotos(int page) {
        return latestPhotos(page, 10);
    }

    public Comment save(Comment p) {
        return commentRepository.save(p);
    }


    public List<Comment> comments(int photoId){
        return commentRepository.findAllByPhotoId(photoId);
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

    public long count() {
        return photoRepository.count();
    }
}
