package models.view;

import models.entity.TimeLineTweet;
import models.entity.Tweet;
import models.entity.User;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Tatsuya Oba
 */
public class TweetListView {
    public final List<TweetView> tweetViewList;

    public final Integer firstIndex;

    public final Integer lastIndex;

    public TweetListView(
            @Nonnull final List<TweetView> tweetViewList,
            @Nonnull final Integer firstIndex,
            @Nonnull final Integer lastIndex
    ) {
        this.tweetViewList = tweetViewList;
        this.firstIndex = firstIndex;
        this.lastIndex = lastIndex;
    }

    public static TweetListView create(
            @Nonnull final User user,
            @Nonnull final List<TimeLineTweet> tweetList,
            @Nonnull final Integer firstIndex,
            @Nonnull final Integer lastIndex
    ) {
        final List<TweetView> tweetViewList = tweetList.stream()
                .map(tweet -> TweetView.create(
                                tweet.tweet,
                                getDoRetweet(user, tweet)
                        )
                ).collect(Collectors.toList());

        return new TweetListView(
                tweetViewList,
                firstIndex,
                lastIndex
        );
    }

    private static Boolean getDoRetweet(
            @Nonnull final User user,
            @Nonnull final TimeLineTweet tweet
    ) {
        if(tweet.retweetUser == null) {
            return !tweet.tweet.author.equals(user);
        }
        return !tweet.retweetUser.equals(user) && !tweet.tweet.author.equals(user);
    }
}
