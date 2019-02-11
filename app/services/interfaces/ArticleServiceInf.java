package services.interfaces;

import datamodels.dto.ArticleDTO;
import datamodels.entities.Article;
import services.PagingService;

import java.util.Optional;

public interface ArticleServiceInf {

    PagingService findNLatestArticles(int n, int page);

    Optional<PagingService> findNLatestArticlesForUsername(int n, int page, String username);

    Optional<ArticleDTO> getArticle(Long postId);

    Optional<Article> getArticleEntity(Long postId);

    ArticleDTO saveArticle(ArticleDTO articleDTO);

    Optional<ArticleDTO> editArticle(ArticleDTO articleDTO);

    void delete(Long postId);
}
