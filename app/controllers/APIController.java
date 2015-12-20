package controllers;

import models.form.GetTweetsForm;
import models.form.SigninForm;
import models.form.SignupForm;
import models.form.TweetForm;
import models.requset.SaveTweetRequest;
import models.requset.SaveUserRequest;
import models.view.TweetListView;
import models.view.TweetView;
import play.data.DynamicForm;
import play.data.Form;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;

import java.util.List;
import java.util.Optional;

/**
 * @author Tatsuya Oba
 */
public class APIController extends BaseController {

    @Security.Authenticated(Secured.class)
    public Result getTweetList() {
        final Form<GetTweetsForm> form = Form.form(GetTweetsForm.class)
                .bindFromRequest();
        final GetTweetsForm getTweetsForm = form.get();

        final Integer size = getTweetsForm.size != null ? getTweetsForm.size : 10;
        final Integer from;
        if (getTweetsForm.from != null) {
            from = getTweetsForm.from;
        } else {
            final Integer allTweetsSize = userService.getTweetsSize(getUserId());
            from = allTweetsSize < size ? 0 : allTweetsSize - size;
        }

        final TweetListView view = userService.getTimeLines(
                getUserId(),
                from,
                size
        );
        return ok(Json.toJson(view));
    }

    @Security.Authenticated(Secured.class)
    public Result tweet() {
        final Form<TweetForm> form = Form.form(TweetForm.class)
                .bindFromRequest();
        if (form.hasErrors()) {
            return badRequest(form.errorsAsJson());
        }

        final TweetForm tweetForm = form.get();
        final SaveTweetRequest request = SaveTweetRequest.makeRequest(getUserId(), tweetForm);
        tweetService.saveTweet(request);

        return ok(Json.toJson("ok"));
    }

    public Result singup() {
        final Form<SignupForm> form = Form.form(SignupForm.class)
                .bindFromRequest();
        if (form.hasErrors()) {
            return badRequest(form.errorsAsJson());
        }

        final SaveUserRequest request = SaveUserRequest.getRequest(form.get());
        userService.saveUser(request);

        return ok(Json.toJson("ok"));
    }

    public Result signin() {
        final Http.Cookie cookie = request().cookie("user");
        if (cookie != null) {
            if (session().get(cookie.value()) != null) {
                return ok(Json.toJson("ok"));
            }
            response().discardCookie("user");
        }

        final Form<SigninForm> form = Form.form(SigninForm.class)
                .bindFromRequest();
        if (form.hasErrors()) {
            return badRequest(form.errorsAsJson());
        }

        final String uuid = java.util.UUID.randomUUID().toString();
        final Long userId = userService.getUserIdFromEmail(form.get().getEmail());
        session().put(uuid, String.valueOf(userId));
        response().setCookie("user", uuid);

        return ok(Json.toJson("ok"));
    }
}
