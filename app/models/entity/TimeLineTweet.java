package models.entity;

import com.avaje.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author Tatsuya Oba
 */
@Entity
@Table(name = "tbl_timeline")
public class TimeLineTweet {
    @Id
    public Long id;

    @ManyToOne
    public User user;

    @ManyToOne
    public User retweetUser;

    @ManyToOne
    public Tweet tweet;

    public static final Model.Finder<Long, TimeLineTweet> find = new Model.Finder<>(TimeLineTweet.class);
}
