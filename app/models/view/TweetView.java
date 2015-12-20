package models.view;

import models.entity.TimeLineTweet;
import models.entity.Tweet;

import javax.annotation.Nonnull;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Tatsuya Oba
 */
public class TweetView {
    public final Long id;

    public final String author;

    public final String content;

    public final String createDate;

    public final Boolean doRetweet;

    public TweetView(
            @Nonnull final Long id,
            @Nonnull final String author,
            @Nonnull final String content,
            @Nonnull final Date createDate,
            @Nonnull final Boolean doRetweet
    ) {
        final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");

        this.id = id;
        this.author = author;
        this.content = content;
        this.createDate = dateFormat.format(createDate);
        this.doRetweet = doRetweet;
    }

    public static TweetView create(
            @Nonnull final Tweet tweet,
            @Nonnull final Boolean doRetweet
    ) {
        return new TweetView(
                tweet.id,
                tweet.author.userName,
                tweet.content,
                tweet.createDate,
                doRetweet
        );
    }
}
