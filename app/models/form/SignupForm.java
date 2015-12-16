package models.form;

import models.service.UserService;
import play.data.validation.Constraints;
import play.data.validation.ValidationError;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Tatsuya Oba
 */
public class SignupForm {
    final UserService userService = new UserService();

    @Constraints.Required(message = "必須の入力項目です。")
    protected String email;

    @Constraints.Required(message = "必須の入力項目です。")
    protected String password;

    @Constraints.Required(message = "必須の入力項目です。")
    protected String userName;

    public List<ValidationError> validate() {
        List<ValidationError> errors = new ArrayList<>();
        if (userService.getUserByEmail(getEmail()) != null) {
            errors.add(new ValidationError("email", "既に登録されているemailアドレスです。"));
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

    public void setUserName(final String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

}
