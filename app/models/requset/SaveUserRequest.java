package models.requset;

import javax.annotation.Nonnull;

/**
 * @author Tatsuya Oba
 */
public class SaveUserRequest {
    public final String email;
    public final String password;
    public final String userName;

    public SaveUserRequest(
            @Nonnull final String email,
            @Nonnull final String password,
            @Nonnull final String userName
    ) {
        this.email = email;
        this.password = password;
        this.userName = userName;
    }
}
