package models.entity;

import com.avaje.ebean.Model;
import com.avaje.ebean.annotation.CreatedTimestamp;
import com.avaje.ebean.annotation.UpdatedTimestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Tatsuya Oba
 */

@Entity
@Table(name = "tbl_user")
public class User extends Model {
    @Id
    public Long id;

    @Column(nullable = false)
    public String email;

    @Column(nullable = false)
    public String hashedPassword;

    @Column(nullable = false)
    public String userName;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    public List<Tweet> tweetList;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    public List<TimeLineTweet> timeLien;

    @OneToMany(mappedBy = "retweetUser", cascade = CascadeType.ALL)
    public List<TimeLineTweet> retweet;

    @ManyToMany
    @JoinColumn(name = "id")
    public List<User> followList;

    @ManyToMany
    @JoinColumn(name = "id")
    public List<User> followerList;

    @CreatedTimestamp
    public Timestamp createDate;

    @UpdatedTimestamp
    public Timestamp updateDate;

    public static final Finder<Long, User> find = new Finder<>(User.class);

    public User() {
        this.tweetList = new ArrayList<>();
        this.timeLien = new ArrayList<>();
        this.retweet = new ArrayList<>();
        this.followList = new ArrayList<>();
        this.followerList = new ArrayList<>();
    }
}
