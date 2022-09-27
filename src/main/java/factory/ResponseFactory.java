package factory;

import model.Response;
public class ResponseFactory {
    public static Response createResponse(String status) {
        Response response = new Response();
        response.setStatus(status);
        return response;
    }
}
