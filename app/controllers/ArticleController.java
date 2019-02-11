package controllers;

import actions.Authenticated;
import actions.ArticleExistsAndUserIsOwner;
import datamodels.dto.ArticleDTO;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Results;
import services.interfaces.CommentServiceInf;
import services.interfaces.ArticleServiceInf;
import views.html.editArticle;
import views.html.newArticle;
import views.html.article;

import javax.inject.Inject;

/**
 * Controller with actions related to articles.
 */
public class ArticleController extends Controller {

    private final ArticleServiceInf articleServiceInf;
    private final CommentServiceInf commentServiceInf;
    private final Form<ArticleDTO> articleForm;

    @Inject
    public ArticleController(ArticleServiceInf articleServiceInf, CommentServiceInf commentServiceInf, FormFactory formFactory) {
        this.articleServiceInf = articleServiceInf;
        this.commentServiceInf = commentServiceInf;
        this.articleForm = formFactory.form(ArticleDTO.class);
    }

    /**
     * GET Article page for article with id = article ID.
     */
    public Result getArticle(Long articleId) {
        return commentServiceInf.findCommentsForArticle(articleId)
                .map(commentDTOs ->
                        articleServiceInf.getArticle(articleId)
                                .map(articleDTO -> ok(article.render(articleDTO, commentDTOs)))
                                .orElseGet(Results::notFound))
                .orElseGet(Results::notFound);
    }

    /**
     * GET new Article form.
     * Only authenticated users can get this form.
     */
    @Authenticated
    public Result getNewArticleForm() {
        return ok(newArticle.render(articleForm));
    }

    /**
     * GET edit Article form.
     * Only authenticated users and users who are the owners of article can get this form.
     */
    @Authenticated
    @ArticleExistsAndUserIsOwner
    public Result getEditArticleForm(Long articleId) {
        return articleServiceInf.getArticle(articleId)
                .map(articleDTO -> ok(editArticle.render(articleForm.fill(articleDTO), articleId)))
                .orElseGet(Results::notFound);
    }

    /**
     * POST new Article form.
     * Only authenticated users can article this form.
     */
    @Authenticated
    public Result createArticle() {
        Form<ArticleDTO> articleForm = this.articleForm.bindFromRequest();
        if (articleForm.hasErrors()) {
            return badRequest(newArticle.render(articleForm));
        } else {
            ArticleDTO articleDTO = articleForm.get();
            articleDTO.username = session("username");
            articleDTO = articleServiceInf.saveArticle(articleDTO);
            return redirect(routes.ArticleController.getArticle(articleDTO.id));
        }
    }

    /**
     * POST edit Article form.
     * Only authenticated users and users who are the owners of article can access this form.
     */
    @Authenticated
    @ArticleExistsAndUserIsOwner
    public Result editArticle(Long articleId) {
        Form<ArticleDTO> articleForm = this.articleForm.bindFromRequest();
        if (articleForm.hasErrors()) {
            return badRequest(editArticle.render(articleForm, articleId));
        } else {
            ArticleDTO articleDTO = articleForm.get();
            articleDTO.id = articleId;
            return articleServiceInf.editArticle(articleDTO)
                    .map(x -> redirect(routes.ArticleController.getArticle(articleId)))
                    .orElseGet(Results::notFound);
        }
    }

    /**
     * DELETE Article.
     * Only authenticated users and users who are the owners of article can delete.
     */
    @Authenticated
    @ArticleExistsAndUserIsOwner
    public Result deleteArticle(Long articleId) {
        articleServiceInf.delete(articleId);
        return redirect(routes.WebController.usersWeb(session("username"), 1));
    }

}
