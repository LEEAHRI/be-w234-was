package model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

public class Request {
    private String method;
    private String url;
    private String queryString;
    private String protocol;
    private String host;
    private String connection;
    private String allowOrigins;

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

    public String getQueryString() {
        return queryString;
    }

    public void setQueryString(String queryString) {
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

    public Request(String method, String url, String queryString, String protocol) {
        this.method = method;
        this.url = url;
        this.queryString = queryString;
        this.protocol = protocol;
    }
}
