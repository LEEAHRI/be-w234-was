package util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestHandler;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class ResourceUtils {

    private static final String RESOURCE_PREFIX = "./webapp";
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    public static byte[] readFile(String location) {
        try {
            return Files.readAllBytes(new File(RESOURCE_PREFIX + location).toPath());
        } catch (IOException e) {
            logger.error("failed to read file: {}", location);
            throw new RuntimeException(e);
        }
    }

    /**
     * Step3 -> File 확장자로 content-type 처리
     * @param location
     * @return html or css
     */
    public static String getExtension(String location) {
        return location.substring(location.lastIndexOf(".") + 1);
    }
}
