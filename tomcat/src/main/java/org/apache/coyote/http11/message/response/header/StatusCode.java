package org.apache.coyote.http11.message.response.header;

import org.apache.coyote.http11.message.Regex;

public enum StatusCode {
    OK(200, "OK"),
    FOUND(302, "Found"),
    SEE_OTHER(303, "See Other"),
    BAD_REQUEST(400, "Bad Request"),
    UNAUTHORIZED(401, "Unauthorized"),
    NOT_FOUND(404, "Not Found"),
    METHOD_NOT_ALLOWED(405, "Method Not Allowed"),
    INTERNAL_SERVER_ERROR(500, "Internal Server Error"),
    ;

    private final int code;
    private final String value;

    StatusCode(final int code, final String value) {
        this.code = code;
        this.value = value;
    }

    @Override
    public String toString() {
        return String.join(Regex.BLANK.getValue(),
                Integer.toString(this.code),
                this.value);
    }
}
