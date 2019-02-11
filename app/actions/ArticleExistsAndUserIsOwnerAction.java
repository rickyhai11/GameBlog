package actions;

import datamodels.dto.LoginDTO;
import datamodels.dto.ArticleDTO;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Result;
import services.interfaces.ArticleServiceInf;

import javax.inject.Inject;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 * Implement annotation to check if an article exists and if logged in user is the owner of article.
 * Returns notFound if article doesn't exists or unauthorized if user is not the owner of article.
 *  *
 */
public class ArticleExistsAndUserIsOwnerAction extends Action<ArticleExistsAndUserIsOwner> {

    private final ArticleServiceInf articleServiceInf;
    private final Form<LoginDTO> loginDTOForm;

    @Inject
    public ArticleExistsAndUserIsOwnerAction(ArticleServiceInf articleServiceInf, FormFactory formFactory) {
        this.articleServiceInf = articleServiceInf;
        this.loginDTOForm = formFactory.form(LoginDTO.class);
    }

    public CompletionStage<Result> call(final Http.Context ctx) {
        String username = ctx.session().get("username");
        Long articleId = Long.parseLong(ctx.request().getQueryString("id"));
        Optional<ArticleDTO> optionalArticle = articleServiceInf.getArticle(articleId);
        if (!optionalArticle.isPresent()) {
            // Article doesn't exists, return notFound
            return CompletableFuture.completedFuture(notFound());
        } else if (!optionalArticle.get().username.equals(username)) {
            // User is not the owner of Article, show him Login form
            Result login = unauthorized(views.html.login.render(
                    loginDTOForm.withGlobalError("Please login with proper credentials to modify this article")));
            return CompletableFuture.completedFuture(login);
        } else {
            // Article exists and User is the owner of Article, call delegate
            return delegate.call(ctx);
        }
    }

}
