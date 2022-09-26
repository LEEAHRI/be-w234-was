package model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Response {
    private String protocol = "HTTP/1.1";
    private String status;
    private String contentType;
    private Long contentLength = 0L;
    private byte[] body;

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
        int getContentLength = (this.getBody() != null) ? this.getBody().length : 0;
        return getContentLength;
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
}
