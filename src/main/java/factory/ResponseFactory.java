package factory;

import model.Response;
public class ResponseFactory {
    public static Response createResponse(int status) {
        Response response = new Response();
        response.setStatus(String.valueOf(status));
        return response;
    }
}
