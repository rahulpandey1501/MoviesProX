package xyz.hollywoodhub.hollywoodhub.model;

/**
 * Created by rpandey.ppe on 26/07/17.
 */

public class MovieInfoModel {
    private final String movieName; //required
    private final String director;
    private final String producer;
    private final String genre;
    private final String country;
    private final String episode;
    private final String duration;
    private final String quality;
    private final String rating;  //required
    private final String release;
    private final String story;
    private final String cast;

    private MovieInfoModel(Builder builder){
        this.movieName = builder.movieName;
        this.director = builder.director;
        this.producer = builder.producer;
        this.genre = builder.genre;
        this.country = builder.country;
        this.episode = builder.episode;
        this.duration = builder.duration;
        this.quality = builder.quality;
        this.rating = builder.rating;
        this.release = builder.release;
        this.story = builder.story;
        this.cast = builder.cast;
    }

    public String getMovieName() {
        return movieName;
    }

    public String getDirector() {
        return director;
    }

    public String getProducer() {
        return producer;
    }

    public String getGenre() {
        return genre;
    }

    public String getCountry() {
        return country;
    }

    public String getEpisode() {
        return episode;
    }

    public String getDuration() {
        return duration;
    }

    public String getQuality() {
        return quality;
    }

    public String getRating() {
        return rating;
    }

    public String getRelease() {
        return release;
    }

    public String getStory() {
        return story;
    }

    public String getCast() {
        return cast;
    }

    public static class Builder {
        private final String movieName; //required
        private String rating;  //required
        private String director;
        private String producer;
        private String genre;
        private String country;
        private String episode;
        private String duration;
        private String quality;
        private String release;
        private String story;
        private String cast;

        public Builder(String movieName) {
            this.movieName = movieName;
        }

        public Builder setDirector(String director) {
            this.director = director;
            return this;
        }

        public Builder setProducer(String producer) {
            this.producer = producer;
            return this;
        }

        public Builder setGenre(String genre) {
            this.genre = genre;
            return this;
        }

        public Builder setCountry(String country) {
            this.country = country;
            return this;
        }

        public Builder setEpisode(String episode) {
            this.episode = episode;
            return this;
        }

        public Builder setDuration(String duration) {
            this.duration = duration;
            return this;
        }

        public Builder setQuality(String quality) {
            this.quality = quality;
            return this;
        }

        public Builder setRelease(String release) {
            this.release = release;
            return this;
        }

        public Builder setStory(String story) {
            this.story = story;
            return this;
        }

        public Builder setCast(String cast) {
            this.cast = cast;
            return this;
        }

        public Builder setRating(String rating) {
            this.rating = rating;
            return this;
        }

        public MovieInfoModel build() {
            return new MovieInfoModel(this);
        }
    }
}
