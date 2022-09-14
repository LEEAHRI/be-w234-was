package http;

import org.junit.jupiter.api.Test;
import util.StringUtil;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

class StringUtilTest {
    @Test
    void parseQueryString() {
        Map<String,String> testResult = StringUtil.parseQueryString("a=1&b=2&c=3");
        assertEquals("1",testResult.get("a"));
        assertEquals("2",testResult.get("b"));
        assertEquals("3",testResult.get("c"));
    }

    @Test
    void inputStreamToLines() {
        String inputString = "abc\ndef\nghi\n\njklmnop";
        InputStream inputStream = new ByteArrayInputStream(inputString.getBytes());
        List<String> testResult = StringUtil.inputStreamToLines(inputStream);

        assertEquals( 5,testResult.size());
        assertEquals( "abc",testResult.get(0));
        assertEquals("",testResult.get(3));
    }
}