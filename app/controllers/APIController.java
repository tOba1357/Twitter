package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.form.FollowForm;
import models.form.GetTweetsForm;
import models.form.ReleaseFollowForm;
import models.form.SigninForm;
import models.form.SignupForm;
import models.form.TweetForm;
import models.requset.SaveTweetRequest;
import models.requset.SaveUserRequest;
import models.view.TweetListView;
import models.view.TweetView;
import models.view.UserListView;
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
//        final Form<GetTweetsForm> form = Form.form(GetTweetsForm.class)
//                .bindFromRequest();
//        final GetTweetsForm getTweetsForm = form.get();
//
//        final Integer size = getTweetsForm.getSize() != null ? getTweetsForm.getSize() : 10;
//        final Integer from;
//        if (getTweetsForm.getFrom() != null) {
//            from = getTweetsForm.getFrom();
//        } else {
//            final Integer allTweetsSize = userService.getTweetsSize(getUserId());
//            from = allTweetsSize < size ? 0 : allTweetsSize - size;
//        }

        final TweetListView view = userService.getAllTimeLines(getUserId());
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
        userService.saveTweet(request);

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

        final String uuid = java.util.UUID.randomUUID().toString();
        final Long userId = userService.getUserIdFromEmail(form.get().getEmail());
        session().put(uuid, String.valueOf(userId));
        response().setCookie("user", uuid);

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

    @Security.Authenticated(Secured.class)
    public Result getUserList() {
        return ok(Json.toJson(userService.getAllUserWithoutLoginUser(getUserId())));
    }

    @Security.Authenticated(Secured.class)
    public Result follow() {
        final Form<FollowForm> form = Form.form(FollowForm.class)
                .bindFromRequest();
        if(form.hasErrors()) {
            return badRequest(form.errorsAsJson());
        }

        final FollowForm followForm = form.get();
        userService.follow(
                getUserId(),
                followForm.getId()
        );
        return ok(Json.toJson("ok"));
    }

    @Security.Authenticated(Secured.class)
    public Result releaseFollow() {
        final Form<ReleaseFollowForm> form = Form.form(ReleaseFollowForm.class)
                .bindFromRequest();
        if(form.hasErrors()) {
            return badRequest(form.errorsAsJson());
        }

        final ReleaseFollowForm releaseFollowForm = form.get();
        userService.releaseFollow(
                getUserId(),
                releaseFollowForm.getId()
        );
        return ok(Json.toJson("ok"));
    }

}
