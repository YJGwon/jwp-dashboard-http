package org.apache.coyote.http11.message.response;

import java.util.Map;
import org.apache.coyote.http11.message.Regex;
import org.apache.coyote.http11.message.header.Header;
import org.apache.coyote.http11.message.request.header.Cookie;
import org.apache.coyote.http11.message.response.header.ContentType;
import org.apache.coyote.http11.message.response.header.Headers;
import org.apache.coyote.http11.message.response.header.StatusCode;

public class HttpResponse {

    private static final String HTTP_VERSION = "HTTP/1.1 ";

    private final StatusCode statusCode;
    private final Headers headers;
    private final String body;

    private HttpResponse(final ContentType contentType,
                         final StatusCode statusCode,
                         final Map<Header, String> headers,
                         final String body) {
        this.statusCode = statusCode;
        this.headers = Headers.of(contentType, body);
        this.headers.putAll(headers);
        this.body = body;
    }

    public HttpResponse(final ContentType contentType, final StatusCode statusCode, final String body) {
        this(contentType, statusCode, Map.of(), body);
    }

    public HttpResponse(final StatusCode statusCode, final String body) {
        this(ContentType.HTML, statusCode, Map.of(), body);
    }

    public static HttpResponse ofOk(final String body) {
        return new HttpResponse(StatusCode.OK, body);
    }

    public static HttpResponse ofOk(final ContentType contentType, final String body) {
        return new HttpResponse(contentType, StatusCode.OK, body);
    }

    public static HttpResponse ofRedirection(final StatusCode statusCode, final String location) {
        return new HttpResponse(ContentType.HTML, statusCode, Map.of(Header.LOCATION, location), "");
    }

    public void setCookie(final Cookie cookie) {
        headers.put(Header.SET_COOKIE, cookie.toText());
    }

    public String toText() {
        return String.join("\r\n",
                HTTP_VERSION + statusCode.toString() + Regex.BLANK.getValue(),
                headers.toText(),
                "",
                body);
    }
}
