package model;

import lombok.Data;

@Data
public class Response {
    private String protocol = "HTTP/1.1";
    private String status;
    private String contentType = "text/html;charset=utf-8";
    private String contentLength;
    private byte[] body;

    public Response() {}
}
