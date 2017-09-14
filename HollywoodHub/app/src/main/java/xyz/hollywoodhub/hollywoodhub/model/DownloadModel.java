package xyz.hollywoodhub.hollywoodhub.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import xyz.hollywoodhub.hollywoodhub.model.downoad.DownloadDataModel;

/**
 * Created by rpandey.ppe on 04/09/17.
 */


public class DownloadModel {
    @SerializedName("playlist") private List<DownloadDataModel> playlist;

    public List<DownloadDataModel> getPlaylist() {
        return playlist;
    }
}