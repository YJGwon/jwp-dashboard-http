package nextstep.jwp.controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Optional;
import nextstep.jwp.exception.WrongInputException;
import nextstep.jwp.model.User;
import nextstep.jwp.service.UserService;
import org.apache.catalina.session.Session;
import org.apache.coyote.http11.message.request.HttpRequest;
import org.apache.coyote.http11.message.request.QueryParams;
import org.apache.coyote.http11.message.request.header.Cookie;
import org.apache.coyote.http11.message.response.HttpResponse;
import org.apache.coyote.http11.message.response.header.StatusCode;

public class LoginController extends AbstractController {

    private static final LoginController INSTANCE = new LoginController();
    private static final String KEY_ACCOUNT = "account";
    private static final String KEY_PASSWORD = "password";

    private final UserService userService;

    public static LoginController getINSTANCE() {
        return INSTANCE;
    }

    @Override
    protected HttpResponse doGet(final HttpRequest httpRequest) throws IOException, URISyntaxException {
        if (hasLoggedIn(httpRequest)) {
            return HttpResponse.ofRedirection(StatusCode.FOUND, View.INDEX.getPath());
        }
        return HttpResponse.ofOk(View.LOGIN.getResource());
    }

    @Override
    protected HttpResponse doPost(final HttpRequest httpRequest) {
        final QueryParams requestParams = QueryParams.from(httpRequest.getBody());
        checkParams(requestParams);

        final User user = userService.login(requestParams.get(KEY_ACCOUNT), requestParams.get(KEY_PASSWORD));
        final Session session = createSession(httpRequest, user);

        final HttpResponse httpResponse = HttpResponse.ofRedirection(StatusCode.FOUND, View.INDEX.getPath());
        httpResponse.setCookie(Cookie.fromJSessionId(session.getId()));
        return httpResponse;
    }

    private boolean hasLoggedIn(final HttpRequest httpRequest) {
        final Optional<Object> user = httpRequest.getSessionAttribute("user");
        return user.isPresent();
    }

    private void checkParams(final QueryParams queryParams) {
        if (!queryParams.containsKey(KEY_ACCOUNT) || !queryParams.containsKey(KEY_PASSWORD)) {
            throw new WrongInputException("계정과 비밀번호를 입력하세요.");
        }
    }

    private Session createSession(final HttpRequest httpRequest, final User user) {
        final Session session = httpRequest.createSession();
        session.setAttribute("user", user);
        return session;
    }

    @Override
    public boolean canHandle(final HttpRequest httpRequest) {
        return httpRequest.isPath("/login");
    }

    private LoginController() {
        this.userService = UserService.getINSTANCE();
    }
}
