package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;
import views.html.signup;

public class Application extends Controller {

    public Result index() {
        return ok(index.render());
    }

    public Result renderSignupForm() {
        return ok(signup.render());
    }

}
