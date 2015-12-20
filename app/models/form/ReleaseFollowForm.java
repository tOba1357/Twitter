package models.form;

import models.service.UserService;
import play.data.validation.Constraints;
import play.data.validation.ValidationError;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Tatsuya Oba
 */
public class ReleaseFollowForm {
    @Constraints.Required(message = "指定してください。")
    protected Long id;

    final UserService userService = new UserService();
    public List<ValidationError> validate() {
        final List<ValidationError> errors = new ArrayList<>();
        if (!userService.registered(getId())) {
            errors.add(new ValidationError("", "登録されていません。"));
        }
        return errors.isEmpty() ? null : errors;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
