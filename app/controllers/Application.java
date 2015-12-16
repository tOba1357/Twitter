package controllers;

import play.mvc.Result;
import views.html.index;
import views.html.signin;
import views.html.signup;

public class Application extends BaseController {

    public Result index() {
        return ok(index.render(isLogin()));
    }

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
}
