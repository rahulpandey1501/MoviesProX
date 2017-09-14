package xyz.hollywoodhub.hollywoodhub.model.downoad;

import com.google.gson.annotations.SerializedName;

/**
 * Created by rpandey.ppe on 26/07/17.
 */

public class SubtitleModel {
    @SerializedName("file") private String file;
    @SerializedName("kind") private String kind;
    @SerializedName("label") private String label;
    @SerializedName("default") private boolean isDefault;

    public String getFile() {
        return file;
    }

    public String getLabel() {
        return label;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public String getKind() {
        return kind;
    }
}
