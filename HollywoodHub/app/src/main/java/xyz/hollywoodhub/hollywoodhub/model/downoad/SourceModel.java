package xyz.hollywoodhub.hollywoodhub.model.downoad;

import com.google.gson.annotations.SerializedName;

/**
 * Created by rpandey.ppe on 26/07/17.
 */

public class SourceModel {
    @SerializedName("file") private String file;
    @SerializedName("type") private String type;
    @SerializedName("label") private String label;
    @SerializedName("default") private boolean isDefault;

    public String getFile() {
        return file;
    }

    public String getType() {
        return type;
    }

    public String getLabel() {
        return label;
    }

    public boolean isDefault() {
        return isDefault;
    }
}
