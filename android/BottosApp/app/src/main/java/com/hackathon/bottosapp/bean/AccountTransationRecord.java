package com.hackathon.bottosapp.bean;

public class AccountTransationRecord {

    private String name;
    private int    type;        //0 content 1 index
    private String content;     //内容本身或者ipfs地址
    private int    status;      //0 上传 1下载 2 是否出售
    private String uploadTime;  //时间

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(String uploadTime) {
        this.uploadTime = uploadTime;
    }
}
