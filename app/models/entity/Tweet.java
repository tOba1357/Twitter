package models.entity;

import com.avaje.ebean.Model;
import com.avaje.ebean.annotation.CreatedTimestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.ArrayList;
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

    @OneToMany(mappedBy = "tweet", cascade = CascadeType.ALL)
    public List<TimeLineTweet> timeLineTweetList;

    @CreatedTimestamp
    public Timestamp createDate;

    public static final Finder<Long, Tweet> find = new Finder<>(Tweet.class);

    public Tweet() {
        this.timeLineTweetList = new ArrayList<>();
    }

}
