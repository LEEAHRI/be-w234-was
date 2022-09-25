package factory;

import model.Request;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpRequestUtils;
import util.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class RequestFactory {
    private static final Logger logger = LoggerFactory.getLogger(RequestFactory.class);
    private static final String BLANK = " ";

    public static Request createRequest(Socket connection) {

        try {
            InputStream in = connection.getInputStream();
            // 요구사항 step1-1 : Requsest Header 출력
            BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            String[] firstLineParser = br.readLine().split(BLANK);
            //리뷰반영 2
            String method = firstLineParser[0];
            String requestTarget = firstLineParser[1];
            String url = requestTarget.split("\\?")[0];
            Map<String, String> queryString = null;
            if (requestTarget.contains("?")) {
                queryString = HttpRequestUtils.parseQueryString(requestTarget.split("\\?")[1]);
            }
            System.out.println(queryString);
            String protocol = firstLineParser[2];

            Map<String, String> headers = new HashMap<>();
            logger.debug("start");
            while (true) {
                String line = br.readLine();
                if (StringUtils.isEmpty(line)) break;
                String[] headerTokens = line.split(": ");
                if (headerTokens.length == 2) {
                    headers.put(headerTokens[0], headerTokens[1]);
                }
            }
            if (method.equals("POST")) {
                logger.debug("Content-Length:{}", headers.get("Content-Length"));
                String body = IOUtils.readData(br, Integer.parseInt(headers.get("Content-Length")));
                logger.debug("Request Body : {}", body);
                logger.debug("end");
                Map<String, String> bodyParam = HttpRequestUtils.parseQueryString(body);
                System.out.println(bodyParam);
                return new Request(method, url, protocol, bodyParam);
            }
            return new Request(method, url, queryString, protocol);
        } catch (IOException e) {
            logger.error(e.getMessage());
            return null;
        }
    }
}
