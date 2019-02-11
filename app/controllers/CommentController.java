package controllers;

import actions.Authenticated;
import datamodels.dto.CommentDTO;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import services.interfaces.CommentServiceInf;
import views.html.comment;

import javax.inject.Inject;

/**
 * Controller with actions related to Comments.
 */
public class CommentController extends Controller {

    private final CommentServiceInf commentServiceInf;
    private final Form<CommentDTO> commentForm;

    @Inject
    public CommentController(CommentServiceInf commentServiceInf, FormFactory formFactory) {
        this.commentServiceInf = commentServiceInf;
        this.commentForm = formFactory.form(CommentDTO.class);
    }

    /**
     * GET new Comment form.
     * Only authenticated users can get this form.
     */
    @Authenticated
    public Result getCommentForm(Long articleId) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.articleId = articleId;
        return ok(comment.render(commentForm.fill(commentDTO), articleId));
    }

    /**
     * POST new Comment.
     * Only authenticated users can article this form.
     */
    @Authenticated
    public Result createComment(Long articleId) {
        Form<CommentDTO> commentForm = this.commentForm.bindFromRequest();
        if (commentForm.hasErrors()) {
            return badRequest(comment.render(commentForm, articleId));
        } else {
            CommentDTO commentDTO = commentForm.get();
            commentDTO.username = session("username");
            commentDTO.articleId = articleId;
            commentServiceInf.saveComment(commentDTO);
            return redirect(routes.ArticleController.getArticle(articleId));
        }
    }
}
