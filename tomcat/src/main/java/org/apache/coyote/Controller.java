package org.apache.coyote;

import org.apache.coyote.http11.message.request.HttpRequest;
import org.apache.coyote.http11.message.response.HttpResponse;

public interface Controller {

    HttpResponse service(final HttpRequest httpRequest) throws Exception;

    boolean canHandle(final HttpRequest httpRequest);
}
