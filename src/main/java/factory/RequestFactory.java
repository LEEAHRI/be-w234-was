
package factory;

import model.Request;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpRequestUtils;
import util.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class RequestFactory {
    private static final Logger logger = LoggerFactory.getLogger(RequestFactory.class);
    private static final String BLANK = " ";

    public static Request createRequest(BufferedReader br) throws IOException {
        try {
            String[] parsedFirstLine = br.readLine().split(BLANK);
            String method = parsedFirstLine[0];
            String requestTarget = parsedFirstLine[1];
            String url = requestTarget.split("\\?")[0];
            Map<String, String> queryString = null;
            if (requestTarget.contains("?")) {
                queryString = HttpRequestUtils.parseQueryString(requestTarget.split("\\?")[1]);
            }
            String protocol = parsedFirstLine[2];

            Map<String, String> headers = new HashMap<>();
            while (true) {
                String line = br.readLine();
                if (StringUtils.isEmpty(line)) break;
                String[] headerTokens = line.split(": ");
                if (headerTokens.length == 2) {
                    headers.put(headerTokens[0], headerTokens[1]);
                }
            }
            if (method.equals("POST")) {
                String body = IOUtils.readData(br, Integer.parseInt(headers.get("Content-Length")));
                body = URLDecoder.decode(body, StandardCharsets.UTF_8);
                Map<String, String> bodyParam = HttpRequestUtils.parseQueryString(body);
                return new Request(method, url, protocol, bodyParam);

            }
            return new Request(method, url, queryString, protocol);
        } catch (IOException e) {
            logger.error("Failed to not-read Method", e);
            return null;
        }
    }
}
