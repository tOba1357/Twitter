package models.view;

import javax.annotation.Nonnull;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Tatsuya Oba
 */
public class TweetView {
    public final String author;

    public final String content;

    public final String createDate;

    public TweetView(
            @Nonnull final String author,
            @Nonnull final String content,
            @Nonnull final Date createDate
    ) {
        final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");

        this.author = author;
        this.content = content;
        this.createDate = dateFormat.format(createDate);
    }
}
