package org.tensorflow.demo.bookmark;

public class Bookmark {
    long id;
    String code;
    String name;
    String imgidfy_code;
    int state;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getImgidfy_code() {
        return imgidfy_code;
    }

    public void setImgidfy_code(String imgidfy_code) {
        this.imgidfy_code= imgidfy_code;
    }
}
