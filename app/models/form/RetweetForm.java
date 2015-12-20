package models.form;

import models.service.TweetService;
import play.data.validation.Constraints;
import play.data.validation.ValidationError;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Tatsuya Oba
 */
public class RetweetForm {
    @Constraints.Required(message = "指定してください。")
    protected Long tweetId;

    final TweetService tweetService = new TweetService();

    public List<ValidationError> validate() {
        final List<ValidationError> errors = new ArrayList<>();
        if (!tweetService.registered(getTweetId())) {
            errors.add(new ValidationError("", "登録されていません。"));
        }
        return errors.isEmpty() ? null : errors;
    }

    public void setTweetId(final Long tweetId) {
        this.tweetId = tweetId;
    }

    public Long getTweetId() {
        return tweetId;
    }
}
