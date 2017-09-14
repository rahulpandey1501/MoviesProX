package xyz.hollywoodhub.hollywoodhub.model;

/**
 * Created by rpandey.ppe on 23/07/17.
 */

public class FilterModel<T> {

    private String tag;
    private String key;
    private String value;
    private String name;

    public FilterModel() {

    }

    public FilterModel(String tag, String key, String value, String name) {
        this.tag = tag;
        this.key = key;
        this.value = value;
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public String getTag() {
        return tag;
    }
}
