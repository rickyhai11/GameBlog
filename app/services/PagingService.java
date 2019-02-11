package services;

import datamodels.dto.ArticleDTO;
import io.ebean.PagedList;
import datamodels.entities.Article;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Articles pagination: Delegates some calls to it, and implement methods that facilitate
 * implementation of pagination on templates.
 */
public class PagingService {

    private final PagedList<Article> articlePagedList;

    public PagingService(PagedList<Article> articlePagedList) {
        this.articlePagedList = articlePagedList;
    }

    public List<ArticleDTO> getList() {
        return articlePagedList.getList()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public int getPageIndex() {
        return articlePagedList.getPageIndex() + 1;
    }

    public boolean hasNext() {
        return getPageIndex() >= 1 && getPageIndex() < getTotalPageCount();
    }

    public boolean hasPrev() {
        return getPageIndex() > 1 && getPageIndex() <= getTotalPageCount();
    }

    public int getTotalPageCount() {
        return articlePagedList.getTotalPageCount();
    }

    public int getTotalCount() {
        return articlePagedList.getTotalCount();
    }

    public boolean indexOutOfBounds() {
        return getPageIndex() < 0 || getPageIndex() > getTotalCount();
    }

    private ArticleDTO convertToDTO(Article article) {
        return new ArticleDTO(article.id, article.title, article.body, article.createDate, article.user.username);
    }
}
