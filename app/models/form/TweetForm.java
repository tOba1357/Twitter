package models.form;

import play.data.validation.Constraints;

/**
 * @author Tatsuya Oba
 */
public class TweetForm {
    @Constraints.Required
    protected Long userId;

    @Constraints.Required
    protected String content;

    public void setUserId(final Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setContent(final String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}
