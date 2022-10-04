package util;

import exception.WebServerErrorException;
import model.User;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class HTMLTableUtils {
    public static String getUserListTable(String htmlPath, List<User> users) throws IOException {
        var document = Jsoup.parse(new File(htmlPath), "UTF-8");
        var table = getTableClassElement(document);
        var tbody = getTableBodyTagElement(table);
        writeUserRows(users, tbody);
        return document.html();
    }

    public static Element getTableBodyTagElement(Element table) {
        return table.getElementsByTag("tbody").stream().findFirst().orElseThrow(WebServerErrorException::new);

    }

    public static Element getTableClassElement(Element table) {
        return table.getElementsByTag("tbody").stream().findFirst().orElseThrow(WebServerErrorException::new);
    }

    public static void writeUserRows(List<User> users, Element tbody) {
        int rowNum = 1;
        StringBuilder stringBuilder = new StringBuilder();
        for (var user : users) {
            stringBuilder.append(String.format(
                    "<tr> <th scope=\"row\">%d</th> <td>%s</td> <td>%s</td> <td>%s</td><td><a href=\"#\" class=\"btn btn-success\" role=\"button\">수정</a></td> </tr>",
                    rowNum++,
                    user.getUserId(),
                    user.getName(),
                    user.getEmail()));
        }
        tbody.html(stringBuilder.toString());
    }
}

