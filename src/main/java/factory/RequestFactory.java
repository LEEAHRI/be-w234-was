package factory;

import model.Request;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;

public class RequestFactory {

    private static final Logger logger = LoggerFactory.getLogger(RequestFactory.class);

    public static Request createRequest(Socket connection) {
        try (InputStream in = connection.getInputStream()) {
            // 요구사항 step1-1 : Requsest Header 출력
            BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            String methodAndUrl = br.readLine();
            String method = methodAndUrl.split(" ")[0];
            String url = methodAndUrl.split(" ")[1];
            String queryString = br.readLine();
            String protocol = br.readLine();
            while (true) {
                String line = br.readLine();
                if (StringUtils.isEmpty(line)) break;
                logger.debug("Line: {}", line);
            }
            return new Request(method, url, queryString, protocol);
        } catch (IOException e) {
            logger.error(e.getMessage());
            return null;
        }
    }
}
