package com.bb.radioetzion.BL;

public class Show {
    private String name;
    private int lengthMinutes;
    private String url;
    private String createdTime;

    public Show(String name, int lengthMinutes, String url, String createdTime) {
        this.name = name;
        this.lengthMinutes = lengthMinutes;
        this.url = url;
        this.createdTime = createdTime;
    }

    public String getName() {
        return name;
    }

    public int getLengthMinutes() {
        return lengthMinutes;
    }

    public String getUrl() {
        return url;
    }

    public String getCreatedTime() {
        return createdTime;
    }
}
