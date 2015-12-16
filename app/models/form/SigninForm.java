package models.form;

import models.entity.User;
import models.service.UserService;
import org.mindrot.jbcrypt.BCrypt;
import play.data.validation.Constraints;
import play.data.validation.ValidationError;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Tatsuya Oba
 */
public class SigninForm {
    @Constraints.Required(message = "入力してください")
    protected String email;

    @Constraints.Required(message = "入力してください")
    protected String password;

    final UserService userService = new UserService();
    public List<ValidationError> validate() {
        final List<ValidationError> errors = new ArrayList<>();
        if (!userService.authenticate(getEmail(), getPassword())) {
            errors.add(new ValidationError("", "メールアドレスまたはパスワードが間違ってます。"));
        }
        return errors.isEmpty() ? null : errors;
    }


    public void setEmail(final String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
