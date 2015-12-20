package models.service;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.EbeanServer;
import models.entity.Tweet;
import models.entity.User;
import models.requset.SaveUserRequest;
import models.view.TweetListView;
import models.view.TweetView;
import org.mindrot.jbcrypt.BCrypt;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;
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

    public TweetListView getTimeLines(
            @Nonnull final Long userId,
            @Nonnull final Integer from,
            @Nonnull final Integer size
    ) {
        final List<Tweet> tweetList = Tweet.find.fetch("author").where()
                .eq("author.id", userId)
                .orderBy("id")
                .setFirstRow(from)
                .setMaxRows(size)
                .findList();

        return TweetListView.create(
                tweetList,
                from,
                from + tweetList.size()
        );
    }

    public TweetListView getTimeLines(
            @Nonnull final Long userId,
            @Nonnull final Integer size
    ) {
        final Integer allTweetsSize = getTweetsSize(userId);
        final Integer from = allTweetsSize < size ? 0 : allTweetsSize - size;
        return getTimeLines(
                userId,
                from,
                size
        );
    }

    public Integer getTweetsSize(@Nonnull final Long userId){
        return Tweet.find.fetch("author").where()
                .eq("author.id", userId)
                .findRowCount();
    }
}

