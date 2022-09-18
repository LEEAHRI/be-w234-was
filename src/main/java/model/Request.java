package model;

import lombok.Data;

@Data
public class Request {
    private String method;
    private String url;
    private String queryString;
    private String protocol;
    private String host;
    private String connection;
    private String allowOrigins;

    public String getUrl() {return url;}

    public Request(String method, String url, String queryString, String protocol) {
        this.method = method;
        this.url = url;
        this.queryString = queryString;
        this.protocol = protocol;
    }
}
