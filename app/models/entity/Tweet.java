package models.entity;

import com.avaje.ebean.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.List;

/**
 * @author Tatsuya Oba
 */
@Entity
@Table(name = "tbl_tweet")
public class Tweet extends Model{
    @Id
    public Long id;

    @Column(nullable = false)
    public String content;

    @ManyToOne
    public User author;

    public static final Finder<Long, Tweet> find = new Finder<>(Tweet.class);

    public Tweet() {}
}
