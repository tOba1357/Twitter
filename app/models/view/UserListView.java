package models.view;

import models.entity.User;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Tatsuya Oba
 */
public class UserListView {
    public final List<UserView> userViewList;
    public final Integer firstIndex;
    public final Integer lastIndex;

    public UserListView(
            @Nonnull final List<UserView> userViewList,
            @Nonnull final Integer firstIndex,
            @Nonnull final Integer lastIndex
    ) {
        this.userViewList = userViewList;
        this.firstIndex = firstIndex;
        this.lastIndex = lastIndex;
    }

    public static UserListView create(
            @Nonnull final List<User> userList,
            @Nonnull final Integer firstIndex,
            @Nonnull final Integer lastIndex
    ) {
        final List<UserView> userViewList = userList.stream()
                .map(UserView::create)
                .collect(Collectors.toList());
        return new UserListView(
                userViewList,
                firstIndex,
                lastIndex
        );
    }
}
