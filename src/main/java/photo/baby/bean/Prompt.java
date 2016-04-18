package photo.baby.bean;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by apple on 16/3/28.
 */
@Entity
@Table(name = "Prompt")
public class Prompt {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @Column(name = "photo_id")
    private int photoId;

    @Column(name = "text")
    private String text;

    @Column(name = "x")
    private int x;

    @Column(name = "y")
    private int y;


    @Column(name = "created_at")
    private Date createdAt;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPhotoId() {
        return photoId;
    }

    public void setPhotoId(int photoId) {
        this.photoId = photoId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
