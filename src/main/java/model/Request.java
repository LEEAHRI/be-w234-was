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

    public Request() {

    }

    public Request(String method, String url, String protocol, Map<String, String> body, Integer contentLength) {
        this.method = method;
        this.url = url;
        this.protocol = protocol;
        this.body = body;
        this.contentLength = contentLength;
    }

    public Request(String method, String url, Map<String, String>queryString, String protocol) {
        this.method = method;
        this.url = url;
        this.queryString = queryString;
        this.protocol = protocol;
    }

    public Request(String method, String url, String protocol, Map<String, String> body) {
        this.method = method;
        this.url = url;
        this.protocol = protocol;
        this.body = body;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Map<String, String> getQueryString() {
        return queryString;
    }

    public void setQueryString(Map<String, String> queryString) {
        this.queryString = queryString;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getConnection() {
        return connection;
    }

    public void setConnection(String connection) {
        this.connection = connection;
    }

    public String getAllowOrigins() {
        return allowOrigins;
    }

    public void setAllowOrigins(String allowOrigins) {
        this.allowOrigins = allowOrigins;
    }

    public Map<String, String> getBody() {
        return body;
    }

    public void setBody(Map<String, String> body) {
        this.body = body;
    }
}
