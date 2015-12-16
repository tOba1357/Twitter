package controllers;

import models.service.UserService;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;

/**
 * @author Tatsuya Oba
 */
public class Secured extends Security.Authenticator {
    private final UserService userService = new UserService();
    @Override
    public String getUsername(final Http.Context context) {
        final Http.Cookie cookie = context.request().cookie("user");
        if(cookie == null) {
            return null;
        }
        final String uuid = cookie.value();
        return context.session().get(uuid);
    }

    @Override
    public Result onUnauthorized(final Http.Context context) {
        return redirect(controllers.routes.Application.renderSigninForm());
    }
}
