package com.example.moviestreaming.Model;

public class Series {

    private String id, name, link_img, rate_imdb, published, time, category_name, director;

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLink_img(String link_img) {
        this.link_img = link_img;
    }

    public void setRate_imdb(String rate_imdb) {
        this.rate_imdb = rate_imdb;
    }

    public void setPublished(String published) {
        this.published = published;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLink_img() {
        return link_img;
    }

    public String getRate_imdb() {
        return rate_imdb;
    }

    public String getPublished() {
        return published;
    }

    public String getTime() {
        return time;
    }

    public String getCategory_name() {
        return category_name;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }
}
