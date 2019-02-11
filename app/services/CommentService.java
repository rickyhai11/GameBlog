package services;

import datamodels.dto.CommentDTO;
import datamodels.entities.Comment;
import datamodels.entities.Article;
import datamodels.entities.User;
import services.interfaces.CommentServiceInf;
import services.interfaces.ArticleServiceInf;
import services.interfaces.UserServiceInf;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

public class CommentService implements CommentServiceInf {

    private final UserServiceInf userServiceInf;
    private final ArticleServiceInf articleServiceInf;

    @Inject
    public CommentService(UserServiceInf userServiceInf, ArticleServiceInf articleServiceInf) {
        this.userServiceInf = userServiceInf;
        this.articleServiceInf = articleServiceInf;
    }

    @Override
    public CommentDTO saveComment(CommentDTO commentDTO) {
        Comment comment = convertToEntity(commentDTO);
        comment.save();
        return convertToDTO(comment);
    }

    @Override
    public Optional<List<CommentDTO>> findCommentsForArticle(Long articleId) {
        return Optional.ofNullable(Article.find.byId(articleId))
                .map(article -> article.comments
                        .stream()
                        .map(this::convertToDTO)
                        .collect(toList()));
    }

    private CommentDTO convertToDTO(Comment comment) {
        return new CommentDTO(comment.body, comment.user.username, comment.article.id, comment.createDate);
    }

    private Comment convertToEntity(CommentDTO commentDTO) {
        User user = userServiceInf.findUserEntityByUsername(commentDTO.username).orElse(null);
        Article article = articleServiceInf.getArticleEntity(commentDTO.articleId).orElse(null);
        return new Comment(commentDTO.body, article, user);
    }
}
