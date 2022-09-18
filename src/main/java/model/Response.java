package model;

import lombok.Data;

@Data
public class Response {
    private String protocol = "HTTP/1.1";
    private String status;
    private String contentType;
    private String contentLength;
    private byte[] body;

    public String getContentType(){
        return contentType;
    }
    public byte[] getBody() {
        return body;
    }

    public void setStatus(String status){
        this.status = status;
    }
    public void setBody(byte[] body){
        this.body = body;
    }


    public Response() {}
}
