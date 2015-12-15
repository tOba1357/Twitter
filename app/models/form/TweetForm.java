package models.form;

import play.data.validation.Constraints;

/**
 * @author Tatsuya Oba
 */
public class TweetForm {
    @Constraints.Required
    public Long userId;

    @Constraints.Required
    public String content;

}
