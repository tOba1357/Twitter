package models.view;

import models.entity.User;

import javax.annotation.Nonnull;

/**
 * @author Tatsuya Oba
 */
public class UserView {
    public final Long id;

    public final String email;

    public final String userName;

    public final Boolean follow;

    public UserView(
            @Nonnull final Long id,
            @Nonnull final String email,
            @Nonnull final String userName,
            @Nonnull final Boolean follow
    ) {
        this.id = id;
        this.email = email;
        this.userName = userName;
        this.follow = follow;
    }

    public static UserView create(@Nonnull final User user) {
        return new UserView(
                user.id,
                user.email,
                user.userName,
                user.followList.contains(user)
        );
    }
}
