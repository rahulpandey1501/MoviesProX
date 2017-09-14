package xyz.hollywoodhub.hollywoodhub.constants;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import xyz.hollywoodhub.hollywoodhub.utilities.webviewUtils.JSInterfaceHandler;

/**
 * Created by rpandey.ppe on 23/07/17.
 */

public class Constants {

    public static final String PROCESS_HTML_JS = "javascript:window." +
            JSInterfaceHandler.TAG +
            ".processHTML('%s', document.getElementsByTagName('body')[0].innerHTML);";

    public static final String CLICK_TRAILER_LINK = "javascript:$('[data-target=#pop-trailer]').click();";

    public static final TreeMap<String, String> categorizationIdMapping;
    public static final List<String> categorizationIdList;

    private static final String LATEST = "Latest";
    private static final String VIEW = "Most Viewed";
    private static final String IMDB = "IMDB";
    private static final String RATING = "Rating";
    private static final String FAVORITE = "Most Favorite";
    public static final String PARSE_FAILED_MESSAGE = "Some error occurred";

    static {
        categorizationIdMapping = new TreeMap<>();
        categorizationIdMapping.put("latest", LATEST);
        categorizationIdMapping.put("view", VIEW);
        categorizationIdMapping.put("imdb_mark", IMDB);
        categorizationIdMapping.put("rating", RATING);
        categorizationIdMapping.put("favorite", FAVORITE);

        categorizationIdList = new ArrayList<>(categorizationIdMapping.keySet());
    }

    public interface WebView {
        String URL_POST_APPEND = "watching.html";
    }
}
