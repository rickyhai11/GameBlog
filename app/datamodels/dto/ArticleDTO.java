package datamodels.dto;

import play.data.validation.Constraints.MinLength;

import java.util.Date;

public class ArticleDTO {

    public Long id;

    @MinLength(value = 10, message = "*Your title must have at least 10 characters")
    public String title;

    public String body;

    public Date createDate;

    public String username;

    public ArticleDTO(Long id, String title, String body, Date createDate, String username) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.createDate = createDate;
        this.username = username;
    }

    public ArticleDTO(){

    }
}
