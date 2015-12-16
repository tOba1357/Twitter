package controllers;

import models.entity.Tweet;
import models.form.SigninForm;
import models.form.SignupForm;
import models.form.TweetForm;
import models.requset.SaveTweetRequest;
import models.requset.SaveUserRequest;
import models.service.TweetService;
import models.service.UserService;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Tatsuya Oba
 */
public class APIController extends BaseController {

    public Result getTweetList() {
        if(isLogin()) {
           return ok(Json.toJson(tweetService.getAllTweets(getUserId())));
        }
        return ok(Json.toJson(tweetService.getAllTweets((long) 1)));
    }

    @Security.Authenticated(Secured.class)
    public Result tweet() {
        final Form<TweetForm> form = Form.form(TweetForm.class)
                .bindFromRequest();
        if (form.hasErrors()) {
            return badRequest(form.errorsAsJson());
        }

        final TweetForm tweetForm = form.get();
        final SaveTweetRequest request = SaveTweetRequest.makeRequest(getUserId() ,tweetForm);
        tweetService.saveTweet(request);

        return ok(Json.toJson("ok"));
    }

    public Result singup() {
        final Form<SignupForm> form = Form.form(SignupForm.class)
                .bindFromRequest();
        if(form.hasErrors()) {
            return badRequest(form.errorsAsJson());
        }

        final SaveUserRequest request = SaveUserRequest.getRequest(form.get());
        userService.saveUser(request);

        return ok(Json.toJson("ok"));
    }

    public Result signin() {
        final Http.Cookie cookie = request().cookie("user");
        if(cookie != null) {
            if(session().get(cookie.value()) != null) {
                return ok(Json.toJson("ok"));
            }
            response().discardCookie("user");
        }

        final Form<SigninForm> form = Form.form(SigninForm.class)
                .bindFromRequest();
        if(form.hasErrors()) {
            return badRequest(form.errorsAsJson());
        }

        final String uuid = java.util.UUID.randomUUID().toString();
        final Long userId = userService.getUserIdFromEmail(form.get().getEmail());
        session().put(uuid, String.valueOf(userId));
        response().setCookie("user", uuid);

        return ok(Json.toJson("ok"));
    }
}
