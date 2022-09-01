package org.apache.coyote.header;

public enum StatusCode {
    OK(200, "OK"),
    FOUND(302, "Found"),
    BAD_REQUEST(400, "Bad Request"),
    UNAUTHORIZED(401, "Unauthorized"),
    NOT_FOUND(404, "Not Found");

    private final int code;
    private final String value;

    StatusCode(final int code, final String value) {
        this.code = code;
        this.value = value;
    }

    @Override
    public String toString() {
        return String.join(" ",
                Integer.toString(this.code),
                this.value);
    }
}
