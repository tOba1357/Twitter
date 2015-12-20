package models.view;

import models.entity.Tweet;

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
            @Nonnull final List<Tweet> tweetList,
            @Nonnull final Integer firstIndex,
            @Nonnull final Integer lastIndex
    ) {
        final List<TweetView> tweetViewList = tweetList.stream()
                .map(TweetView::create)
                .collect(Collectors.toList());

        return new TweetListView(
                tweetViewList,
                firstIndex,
                lastIndex
        );
    }
}
