package models.form;

import play.data.validation.Constraints;

/**
 * @author Tatsuya Oba
 */
public class TweetForm {
    @Constraints.Required
    protected String content;

    public void setContent(final String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}
