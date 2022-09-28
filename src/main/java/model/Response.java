package model;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class Response {
    private String protocol = "HTTP/1.1";
    private String status;
    private String contentType;
    private Long contentLength = 0L;
    private byte[] body;
    private String location;
    private Map<String, String> cookies;

    public void setBody(byte[] body) {
        this.body = body;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public int getContentLength() {
        return (this.getBody() != null) ? this.getBody().length : 0;
    }

    public byte[] getBody() {
        return body;
    }
    public Map<String, String> getCookies() {
        if (this.cookies == null) {
            this.cookies = new HashMap<>();
        }
        return cookies;
    }
}

