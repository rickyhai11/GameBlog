package datamodels.entities;

import io.ebean.Finder;
import io.ebean.Model;
import io.ebean.annotation.CreatedTimestamp;
import io.ebean.annotation.NotNull;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Comment extends Model {

    @Id
    public Long id;

    @Column(columnDefinition = "TEXT")
    public String body;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedTimestamp
    @Column(updatable = false)
    public Date createDate;

    @NotNull
    @ManyToOne
    public Article article;

    @NotNull
    @ManyToOne
    public User user;

    public static final Finder<Long, Comment> find = new Finder<>(Comment.class);

    public Comment(String body, Article article, User user) {
        this.body = body;
        this.article = article;
        this.user = user;
    }
}
