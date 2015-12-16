package models.requset;

import models.form.TweetForm;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author Tatsuya Oba
 */
public class SaveTweetRequest {
    public final Long userId;

    public final String content;

    public SaveTweetRequest(
            @Nonnull final Long userId,
            @Nonnull final String content
    ) {
        this.userId = userId;
        this.content = content;
    }

    @Nullable
    public static SaveTweetRequest makeRequest(@Nonnull final TweetForm tweetForm) {
        return new SaveTweetRequest(
                tweetForm.getUserId(),
                tweetForm.getContent()
        );
    }
}
