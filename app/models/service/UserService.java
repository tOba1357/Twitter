package models.service;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.EbeanServer;
import models.entity.TimeLineTweet;
import models.entity.Tweet;
import models.entity.User;
import models.requset.SaveTweetRequest;
import models.requset.SaveUserRequest;
import models.view.TweetListView;
import models.view.TweetView;
import models.view.UserListView;
import models.view.UserView;
import org.mindrot.jbcrypt.BCrypt;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Tatsuya Oba
 */
public class UserService {
    private static final EbeanServer SERVER = Ebean.getServer(null);

    public User getUserById(@Nonnull final Long userId) {
        return User.find.byId(userId);
    }

    public User getUserByEmail(@Nonnull final String email) {
        return User.find.where()
                .eq("email", email)
                .findUnique();
    }

    public Boolean registered(@Nonnull final String email) {
        return getUserByEmail(email) != null;
    }

    public Boolean registered(@Nonnull final Long id) {
        return getUserById(id) != null;
    }

    public Boolean authenticate(
            @Nonnull final String email,
            @Nonnull final String password
    ) {
        final User user = getUserByEmail(email);
        return user != null && BCrypt.checkpw(password, user.hashedPassword);
    }

    public boolean saveUser(@Nonnull final SaveUserRequest request) {
        if (getUserByEmail(request.email) != null) {
            return false;
        }
        final User user = new User();
        user.email = request.email;
        user.hashedPassword = BCrypt.hashpw(request.password, BCrypt.gensalt());
        user.userName = request.userName;

        SERVER.save(user);
        return true;
    }

    public Long getUserIdFromEmail(@Nonnull final String email) {
        final User user = getUserByEmail(email);
        if (user == null) {
            return null;
        }
        return user.id;
    }

    public TweetListView getAllTimeLines(@Nonnull final Long userId) {
        final List<TimeLineTweet> tweetList = TimeLineTweet.find.fetch("user").fetch("tweet").where()
                .eq("user.id", userId)
                .orderBy().desc("tweet.createDate")
                .setFirstRow(0)
                .setMaxRows(100)
                .findList();

        final User user = getUserById(userId);
        return TweetListView.create(
                user,
                tweetList,
                0,
                tweetList.size()
        );
    }

//    public TweetListView getTimeLines(
//            @Nonnull final Long userId,
//            @Nonnull final Integer from,
//            @Nonnull final Integer size
//    ) {
//        final List<Tweet> tweetList = Tweet.find.fetch("viewUserList").where()
//                .eq("timeLineTweetList.id", userId)
//                .orderBy().desc("createDate")
//                .setFirstRow(from)
//                .setMaxRows(size)
//                .findList();
//
//        return TweetListView.create(
//                tweetList,
//                from,
//                from + tweetList.size()
//        );
//    }

//    public TweetListView getTimeLines(
//            @Nonnull final Long userId,
//            @Nonnull final Integer size
//    ) {
//        final Integer allTweetsSize = getTweetsSize(userId);
//        final Integer from = allTweetsSize < size ? 0 : allTweetsSize - size;
//        return getTimeLines(
//                userId,
//                from,
//                size
//        );
//    }

    public Integer getTweetsSize(@Nonnull final Long userId) {
        return Tweet.find.fetch("viewUserList").where()
                .eq("viewUserList.id", userId)
                .findRowCount();
    }

    public UserListView getAllUserWithoutLoginUser(@Nonnull final Long userId) {
        final User loginUser = getUserById(userId);
        final List<UserView> userViewList = User.find.all().stream()
                .filter(user -> !user.id.equals(userId))
                .map(user -> new UserView(
                        user.id,
                        user.email,
                        user.userName,
                        loginUser.followList.contains(user)
                )).collect(Collectors.toList());

        return new UserListView(
                userViewList,
                0,
                userViewList.size() - 1
        );
    }

    public boolean follow(
            @Nonnull final Long followUserId,
            @Nonnull final Long followerUserId
    ) {
        final User followUser = getUserById(followUserId);
        final User followerUser = getUserById(followerUserId);
        if (followUser == null || followerUser == null) {
            return false;
        }
        followUser.followList.add(followerUser);
        SERVER.update(followUser);

        followerUser.tweetList.forEach(tweet -> {
            final TimeLineTweet timeLineTweet = new TimeLineTweet();
            timeLineTweet.tweet = tweet;
            timeLineTweet.user = followUser;
            SERVER.save(timeLineTweet);
        });

        return true;
    }

    public boolean releaseFollow(
            @Nonnull final Long followUserId,
            @Nonnull final Long followerUserId
    ) {
        final User followUser = getUserById(followUserId);
        final User followerUser = getUserById(followerUserId);
        if (followUser == null || followerUser == null) {
            return false;
        }
        followUser.followList.remove(followerUser);
        SERVER.update(followUser);

        followUser.timeLien.stream()
                .filter(timeLineTweet -> timeLineTweet.tweet.author.equals(followerUser))
                .forEach(SERVER::delete);

        return true;
    }

    public boolean saveTweet(@Nonnull final SaveTweetRequest request) {
        final User user = getUserById(request.userId);
        if (user == null) {
            return false;
        }

        final Tweet tweet = new Tweet();
        tweet.author = user;
        tweet.content = request.content;
        SERVER.save(tweet);

        final TimeLineTweet myTimeLineTweet = new TimeLineTweet();
        myTimeLineTweet.tweet = tweet;
        myTimeLineTweet.user = user;
        SERVER.save(myTimeLineTweet);
        user.followerList.forEach(follower -> {
            final TimeLineTweet timeLineTweet = new TimeLineTweet();
            timeLineTweet.tweet = tweet;
            timeLineTweet.user = follower;
            SERVER.save(timeLineTweet);
        });

        return true;
    }

    public boolean retweet(
            @Nonnull final Long userId,
            @Nonnull final Long tweetId
    ) {
        final User user = getUserById(userId);
        final Tweet tweet = Tweet.find.byId(tweetId);
        if (user == null || tweet == null || tweet.author.equals(user)) {
            return false;
        }

        user.followerList.forEach(follower -> {
            final TimeLineTweet timeLineTweet = new TimeLineTweet();
            timeLineTweet.retweetUser = user;
            timeLineTweet.tweet = tweet;
            timeLineTweet.user = follower;
            SERVER.save(timeLineTweet);
        });
        return true;
    }
}

