package ir.javadsh.challenge.model;

public class Model {
    private String imgUrl;
    private String BrowserName;
    private String url;
    private String createdDate;

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

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }
}
