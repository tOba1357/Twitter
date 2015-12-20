package models.view;

import models.entity.User;

import javax.annotation.Nonnull;

/**
 * @author Tatsuya Oba
 */
public class UserView {
    public final String email;

    public final String userName;

    public UserView(
            @Nonnull final String email,
            @Nonnull final String userName
    ) {
        this.email = email;
        this.userName = userName;
    }

    public static UserView create(@Nonnull final User user) {
        return new UserView(
                user.email,
                user.userName
        );
    }
}
