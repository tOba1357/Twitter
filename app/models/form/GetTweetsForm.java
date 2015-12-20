package models.form;

/**
 * @author Tatsuya Oba
 */
public class GetTweetsForm {
    protected Integer from;

    protected Integer size;

    public Integer getFrom() {
        return from;
    }

    public Integer getSize() {
        return size;
    }

    public void setFrom(final Integer from) {
        this.from = from;
    }

    public void setSize(final Integer size) {
        this.size = size;
    }
}
