package org.apache.coyote.http11.message.request;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.coyote.http11.message.Regex;

public class QueryParams {

    private static final int INDEX_KEY = 0;
    private static final int INDEX_VALUE = 1;

    private final Map<String, String> values;

    private QueryParams(final Map<String, String> values) {
        this.values = values;
    }

    public static QueryParams from(final String queryString) {
        final String decodedQueryString = URLDecoder.decode(queryString, StandardCharsets.UTF_8);
        final Map<String, String> values = Arrays.stream(decodedQueryString.split(Regex.QUERY_PARAM.getValue()))
                .map(param -> param.split(Regex.KEY_VALUE.getValue(), 2))
                .filter(param -> param.length == 2)
                .collect(Collectors.toMap(
                        param -> param[INDEX_KEY],
                        param -> param[INDEX_VALUE]
                ));
        return new QueryParams(values);
    }

    public static QueryParams ofEmpty() {
        return new QueryParams(Map.of());
    }

    public String get(String key) {
        return values.get(key);
    }

    public boolean containsKey(String key) {
        return values.containsKey(key);
    }
}
