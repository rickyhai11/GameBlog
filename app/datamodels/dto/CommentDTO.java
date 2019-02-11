package datamodels.dto;

import play.data.validation.Constraints;
import java.util.Date;

public class CommentDTO {

    @Constraints.MinLength(value = 10, message = "*Your comment must have at least 10 characters")
    public String body;

    public String username;

    public Long articleId;

    public Date createDate;

    public CommentDTO() {
    }

    public CommentDTO(String body, String username, Long articleId, Date createDate) {
        this.body = body;
        this.username = username;
        this.articleId = articleId;
        this.createDate = createDate;
    }
}
