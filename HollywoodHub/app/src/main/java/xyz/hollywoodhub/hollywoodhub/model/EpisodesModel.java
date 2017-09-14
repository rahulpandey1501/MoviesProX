package xyz.hollywoodhub.hollywoodhub.model;

/**
 * Created by rpandey.ppe on 05/09/17.
 */

public class EpisodesModel {
    private String title;
    private String id;
    private String data_id;

    public EpisodesModel(String title, String id, String data_id) {
        this.title = title;
        this.id = id;
        this.data_id = data_id;
    }

    public String getTitle() {
        return title;
    }

    public String getId() {
        return id;
    }

    public String getData_id() {
        return data_id;
    }
}
