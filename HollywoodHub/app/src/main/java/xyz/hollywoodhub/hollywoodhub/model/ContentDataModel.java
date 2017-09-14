package xyz.hollywoodhub.hollywoodhub.model;

import xyz.hollywoodhub.hollywoodhub.ui.enums.ContentType;

/**
 * Created by rpandey.ppe on 23/07/17.
 */

public class ContentDataModel {
    private String contentUrl;
    private String imageUrl;
    private String movieName;
    private String movieQuality = "HD";
    private String noOfEpisodes;
    private String movieDetailsUrl;
    private ContentType contentType;

    public String getContentUrl() {
        return contentUrl;
    }

    public void setContentUrl(String contentUrl) {
        this.contentUrl = contentUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getMovieQuality() {
        return movieQuality;
    }

    public void setMovieQuality(String movieQuality) {
        this.movieQuality = movieQuality;
    }

    public String getMovieDetailsUrl() {
        return movieDetailsUrl;
    }

    public void setMovieDetailsUrl(String movieDetailsUrl) {
        this.movieDetailsUrl = movieDetailsUrl;
    }

    public String getNoOfEpisodes() {
        return noOfEpisodes;
    }

    public void setNoOfEpisodes(String noOfEpisodes) {
        this.noOfEpisodes = noOfEpisodes;
    }

    public ContentType getContentType() {
        return contentType;
    }

    public void setContentType(ContentType contentType) {
        this.contentType = contentType;
    }
}
