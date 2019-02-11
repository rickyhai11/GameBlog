package datamodels.entities;

import io.ebean.Finder;
import io.ebean.Model;
import io.ebean.annotation.CreatedTimestamp;
import io.ebean.annotation.NotNull;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Article extends Model {

    @Id
    public Long id;

    @NotNull
    public String title;

    @Column(columnDefinition = "TEXT")
    public String body;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedTimestamp
    @Column(updatable = false)
    public Date createDate;

    @NotNull
    @ManyToOne
    public User user;

    @OneToMany(mappedBy = "article", cascade = CascadeType.REMOVE)
    public List<Comment> comments;

    public static final Finder<Long, Article> find = new Finder<>(Article.class);

    public Article(String title, String body, User user) {
        this.title = title;
        this.body = body;
        this.user = user;
    }

}
