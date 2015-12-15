package controllers;

import models.entity.Tweet;
import models.form.TweetForm;
import models.requset.SaveTweetRequest;
import models.service.TweetService;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Tatsuya Oba
 */
public class APIController extends Controller {
    private final TweetService tweetService = new TweetService();

    public Result getTweetList() {
        return ok(Json.toJson(tweetService.getAllTweets((long) 1)));
    }

    public Result tweet() {
        final Form<TweetForm> form = Form.form(TweetForm.class)
                .bindFromRequest();
        if (form.hasErrors()) {
            return badRequest(form.errorsAsJson());
        }

        final TweetForm tweetForm = form.get();
        final SaveTweetRequest request = SaveTweetRequest.makeRequest(tweetForm);
        if (request == null) {
            return badRequest();
        }
        if (!tweetService.saveTweet(request)) {
            final Map<String, List<String>> error = new HashMap<>();
            error.put("general", Collections.singletonList("ユーザが見つかりませんでした。"));
            return badRequest(Json.toJson(error));
        }

        return ok(Json.toJson(tweetForm));
    }
}
