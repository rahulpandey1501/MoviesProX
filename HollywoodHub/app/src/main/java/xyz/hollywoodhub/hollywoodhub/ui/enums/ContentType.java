package xyz.hollywoodhub.hollywoodhub.ui.enums;

/**
 * Created by rpandey.ppe on 03/09/17.
 */

public enum ContentType {

    MOVIE(0),
    TV_SERIES(1),
    MIXED(2);

    int value;

    ContentType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
