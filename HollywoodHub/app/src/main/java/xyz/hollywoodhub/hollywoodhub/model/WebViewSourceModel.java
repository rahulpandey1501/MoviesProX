package xyz.hollywoodhub.hollywoodhub.model;

/**
 * Created by rpandey.ppe on 11/09/17.
 */

public class WebViewSourceModel {

    public static final String CONTENT_LIST = "content_list";

    public static class Request {

        private String contextId;
        private String url;

        public Request(String contextId, String url) {
            this.contextId = contextId;
            this.url = url;
        }

        public String getContextId() {
            return contextId;
        }

        public String getURL() {
            return url;
        }
    }


    public static class Response {

        private String contextId;
        private String html;

        public Response(String contextId, String html) {
            this.contextId = contextId;
            this.html = html;
        }

        public String getContextId() {
            return contextId;
        }

        public String getHtml() {
            return html;
        }
    }
}
