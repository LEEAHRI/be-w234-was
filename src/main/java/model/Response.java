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

    private String location;

    private byte[] body;

    private Map<String, String> cookies;

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
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
        if (this.getBody() != null) {
            return this.getBody().length;
        }
        return 0;
    }

    public void setContentLength(Long contentLength) {
        this.contentLength = contentLength;
    }

    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    public Response() {
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Map<String, String> getCookies() {
        if (this.cookies == null) {
            this.cookies = new HashMap<>();
        }
        return cookies;
    }
}
