package xyz.hollywoodhub.hollywoodhub.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by rpandey.ppe on 04/09/17.
 */

public class EpisodeIdsModel {

    private String html;
    private int status;

    public int getStatus() {
        return status;
    }

    public String getHTML() {
        return html.replace("\n", "");
    }
}
