package model;

import java.util.Map;

public class Request {
    private String method;
    private String url;
    private Map<String, String> queryString;
    private String protocol;
    private String host;
    private String connection;
    private String allowOrigins;
    private Map<String, String> body;

    private int contentLength;

    public Request(String method, String url, String protocol) {
        this.method = method;
        this.url = url;
        this.protocol = protocol;
    }

    public Request(String method, String url, Map<String, String> queryString, String protocol) {
        this(method, url, protocol);
        this.queryString = queryString;
    }

    public Request(String method, String url, String protocol, Map<String, String> body) {
        this(method, url, protocol);
        this.body = body;
    }

    public String getMethod() {
        return method;
    }


    public String getUrl() {
        return url;
    }

    public Map<String, String> getQueryString() {
        return queryString;
    }


    public Map<String, String> getBody() {
        return body;
    }
}
