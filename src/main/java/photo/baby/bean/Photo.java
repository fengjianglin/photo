package photo.baby.bean;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by apple on 16/3/25.
 */

@Entity
@Table(name = "Photo")
public class Photo implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    // 图片宽度 px
    @Column(name = "width")
    private int width;

    // 图片高度 px
    @Column(name = "height")
    private int height;

    // 图片大小 byte
    @Column(name = "size")
    private long size;

    @Column(name = "created_at")
    private Date createdAt;

    @Transient
    private String url;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
