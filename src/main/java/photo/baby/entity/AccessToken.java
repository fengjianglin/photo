package photo.baby.entity;

import java.io.Serializable;

/**
 * Created by apple on 16/3/29.
 */
public class AccessToken implements Serializable {

    private int status = 0;
    private String token = "";
    private long expired = 0;
    private String msg = "";
    private String url = "";

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getExpired() {
        return expired;
    }

    public void setExpired(long expired) {
        this.expired = expired;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
