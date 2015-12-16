package controllers;

import models.service.TweetService;
import models.service.UserService;
import play.mvc.Controller;
import play.mvc.Http;

/**
 * @author Tatsuya Oba
 */
public class BaseController extends Controller {
    protected final TweetService tweetService = new TweetService();
    protected final UserService userService = new UserService();

    protected Long getUserId() {
        final Http.Cookie cookie = request().cookie("user");
        if(cookie == null) {
            return null;
        }
        return Long.valueOf(session().get(cookie.value()));
    }

    protected boolean isLogin() {
        return getUserId() != null;
    }
}
