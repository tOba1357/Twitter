package controllers;

import play.mvc.Result;
import play.mvc.Security;
import views.html.index;
import views.html.signin;
import views.html.signup;
import views.html.top;
import views.html.userList;

public class Application extends BaseController {

    @Security.Authenticated(Secured.class)
    public Result index() {
        return ok(index.render(isLogin()));
    }

    public Result top() {
        return ok(top.render());
    }

    @Security.Authenticated(Secured.class)
    public Result logout() {
        response().discardCookie("user");
        return redirect(controllers.routes.Application.index());
    }

    public Result renderSignupForm() {
        if (isLogin()) {
            return redirect(controllers.routes.Application.index());
        }
        return ok(signup.render());
    }

    public Result renderSigninForm() {
        if (isLogin()) {
            return redirect(controllers.routes.Application.index());
        }
        return ok(signin.render());
    }

    @Security.Authenticated(Secured.class)
    public Result renderUserList() {
        return ok(userList.render(isLogin()));
    }
}
