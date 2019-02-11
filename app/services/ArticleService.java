package services;

import datamodels.dto.ArticleDTO;
import datamodels.entities.Article;
import datamodels.entities.User;
import services.interfaces.ArticleServiceInf;
import services.interfaces.UserServiceInf;

import javax.inject.Inject;
import java.util.Optional;

public class ArticleService implements ArticleServiceInf {

    private final UserServiceInf userServiceInf;

    @Inject
    public ArticleService(UserServiceInf userServiceInf) {
        this.userServiceInf = userServiceInf;
    }

    @Override
    public PagingService findNLatestArticles(int n, int page) {
        return new PagingService(Article.find.query().orderBy("create_date desc").setFirstRow(n * (page - 1)).setMaxRows(n).findPagedList());
    }

    @Override
    public Optional<PagingService> findNLatestArticlesForUsername(int n, int page, String username) {
        return User.find.query().where().eq("username", username).findOneOrEmpty()
                .map(user -> new PagingService(
                        Article.find.query()
                                .where().eq("user_id", user.id)
                                .orderBy("create_date desc")
                                .setFirstRow(n * (page - 1))
                                .setMaxRows(n)
                                .findPagedList()));
    }

    @Override
    public Optional<ArticleDTO> getArticle(Long articleId) {
        return getArticleEntity(articleId)
                .map(this::convertToDTO);
    }

    @Override
    public Optional<Article> getArticleEntity(Long articleId) {
        return Optional.ofNullable(Article.find.byId(articleId));
    }

    @Override
    public ArticleDTO saveArticle(ArticleDTO articleDTO) {
        Article article = convertToEntity(articleDTO);
        article.save();
        return convertToDTO(article);
    }

    @Override
    public Optional<ArticleDTO> editArticle(ArticleDTO articleDTO) {
        return getArticleEntity(articleDTO.id)
                .map(article -> {
                    article.body = articleDTO.body;
                    article.title = articleDTO.title;
                    article.save();
                    return convertToDTO(article);
                });
    }

    @Override
    public void delete(Long articleId) {
        Article.find.deleteById(articleId);
    }

    private ArticleDTO convertToDTO(Article article) {
        return new ArticleDTO(article.id, article.title, article.body, article.createDate, article.user.username);
    }

    private Article convertToEntity(ArticleDTO articleDTO) {
        User user = userServiceInf.findUserEntityByUsername(articleDTO.username).orElse(null);
        return new Article(articleDTO.title, articleDTO.body, user);
    }

}
