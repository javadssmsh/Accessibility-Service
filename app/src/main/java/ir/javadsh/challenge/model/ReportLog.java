package ir.javadsh.challenge.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ReportLog {
    @PrimaryKey
    private int id;
    private String imgUrl;
    private String BrowserName;
    private String url;
    private Long createdDate;

    public ReportLog(String imgUrl, String browserName, String url, Long createdDate) {
        this.imgUrl = imgUrl;
        BrowserName = browserName;
        this.url = url;
        this.createdDate = createdDate;
    }

    public ReportLog() {
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getBrowserName() {
        return BrowserName;
    }

    public void setBrowserName(String browserName) {
        BrowserName = browserName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Long createdDate) {
        this.createdDate = createdDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
