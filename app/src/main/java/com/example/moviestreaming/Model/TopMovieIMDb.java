package com.example.moviestreaming.Model;

public class TopMovieIMDb {

    private String id;
    private String name;
    private String link_img;
    private String rate_imdb;
    private String published;
    private String time;
    private String category_name;
    private String rank;
    private String director;

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    private String genre;

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

    public void setRank(String rank) {
        this.rank = rank;
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

    public String getRank() {
        return rank;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }
}
