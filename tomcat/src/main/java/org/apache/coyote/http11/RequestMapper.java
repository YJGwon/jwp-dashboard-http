package org.apache.coyote.http11;

import java.util.ArrayList;
import java.util.List;
import nextstep.jwp.exception.NotFoundException;
import org.apache.coyote.Controller;
import org.apache.coyote.http11.message.request.HttpRequest;

public class RequestMapper {

    private static final List<Controller> controllers = new ArrayList<>();

    public static void addController(Controller controller) {
        controllers.add(controller);
    }

    public static Controller getController(HttpRequest httpRequest) {
        return controllers.stream()
                .filter(controller -> controller.canHandle(httpRequest))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("페이지를 찾을 수 없습니다."));
    }
}
