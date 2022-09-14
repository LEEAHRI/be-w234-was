package http.response;

import java.io.DataOutputStream;

public interface HttpResponse {
    public HttpResponse status(int statusCode);
    public HttpResponse addHeader(ResponseHeader header);
    public HttpResponse body(ResponseBody body);
    public void send(DataOutputStream dos);
}
