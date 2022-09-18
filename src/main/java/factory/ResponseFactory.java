package factory;

import model.Response;

public class ResponseFactory {

    public static Response create200OkResponse() {
        Response response = new Response();
        response.setStatus("200");
        return response;
    }

    public static Response create500ErrorResponse() {
        Response response = new Response();
        response.setStatus("500");
        return response;
    }
}
