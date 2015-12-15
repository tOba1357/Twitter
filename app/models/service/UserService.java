package models.service;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.EbeanServer;
import models.entity.User;
import models.requset.SaveUserRequest;
import org.mindrot.jbcrypt.BCrypt;

import javax.annotation.Nonnull;

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

}
