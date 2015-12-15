package models.service;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.EbeanServer;
import models.entity.Tweet;
import models.entity.User;
import models.requset.SaveTweetRequest;
import models.view.TweetView;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Tatsuya Oba
 */
public class TweetService {
    private static final UserService USER_SERVICE = new UserService();

    private static final EbeanServer SERVER = Ebean.getServer(null);

    public boolean saveTweet(@Nonnull final SaveTweetRequest request) {
        final User user = USER_SERVICE.getUserById(request.userId);
        if(user == null) {
            return false;
        }

        final Tweet tweet = new Tweet();
        tweet.author = user;
        tweet.content = request.content;
        SERVER.save(tweet);

        return true;
    }

    @Nullable
    public List<TweetView> getAllTweets(@Nonnull final Long userId) {
        final User user = USER_SERVICE.getUserById(userId);
        if(user == null) {
            return  Collections.emptyList();
        }
        final List<TweetView> tweetViewList = user.tweetList.stream()
                .map(tweet -> new TweetView(
                        user.userName,
                        tweet.content,
                        tweet.createDate
                ))
                .collect(Collectors.toList());

        return tweetViewList;
    }
}
