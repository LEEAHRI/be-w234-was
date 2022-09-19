package factory;

import model.Request;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpRequestUtils;

import java.io.*;
import java.net.Socket;
import java.util.Map;

public class RequestFactory {

    private static final Logger logger = LoggerFactory.getLogger(RequestFactory.class);
    private static final String BLANK = " ";

    public static Request createRequest(Socket connection) {

        try {
            InputStream in = connection.getInputStream();
            // 요구사항 step1-1 : Requsest Header 출력
            BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            String firstLine = br.readLine();
            String method = firstLine.split(BLANK)[0];
            String requestTarget = firstLine.split(BLANK)[1];
            String url = requestTarget.split("\\?")[0];
            Map<String, String> queryString = null;
            if (requestTarget.contains("?")) {
                queryString = HttpRequestUtils.parseQueryString(requestTarget.split("\\?")[1]);
            }
            String protocol = firstLine.split(BLANK)[2];
            while (true) {
                String line = br.readLine();
                if (StringUtils.isEmpty(line)) break;
                logger.debug("Line: {}", line);
            }

            if (method.equals("POST")) {
                String line = br.readLine();
                Map<String, String> bodyParam = HttpRequestUtils.parseQueryString(line);
                return new Request(method, url, queryString, protocol, bodyParam);
            }
            return new Request(method, url, queryString, protocol);
        } catch (IOException e) {
            logger.error(e.getMessage());
            return null;
        }
    }
}
