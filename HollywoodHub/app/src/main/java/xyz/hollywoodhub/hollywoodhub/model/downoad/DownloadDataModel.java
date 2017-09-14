package xyz.hollywoodhub.hollywoodhub.model.downoad;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;
import java.util.List;

/**
 * Created by rpandey.ppe on 26/07/17.
 */

public class DownloadDataModel {
    @SerializedName("ip") private String ip;
    @SerializedName("tracks") private Object tracks;
    @SerializedName("sources") private Object sources;

    public String getIp() {
        return ip;
    }

    public List<SubtitleModel> getTracks() {
        if (tracks instanceof List) {
            return (List<SubtitleModel>) tracks;
        }
        return Arrays.asList((SubtitleModel)tracks);
    }

    public List<SourceModel> getSources() {
        if (tracks instanceof List) {
            return (List<SourceModel>) tracks;
        }
        return Arrays.asList((SourceModel)tracks);
    }
}
