package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Results;
import services.interfaces.ArticleServiceInf;
import services.interfaces.UserServiceInf;
import views.html.web;
import views.html.usersWeb;

import javax.inject.Inject;

/**
 * Web Controller that triggers actions to return web for username and home page
 */
public class WebController extends Controller {

    private static final int N_OF_LATEST_ARTICLES = 4;
    private final ArticleServiceInf articleServiceInf;
    private final UserServiceInf userServiceInf;

    @Inject
    public WebController(ArticleServiceInf articleServiceInf, UserServiceInf userServiceInf) {
        this.articleServiceInf = articleServiceInf;
        this.userServiceInf = userServiceInf;
    }

    /**
     * Home page, list of all articles or reviews by all users.
     *
     * @param page - page index
     * @return web template
     */
    public Result web(int page) {
        return ok(web.render(articleServiceInf.findNLatestArticles(N_OF_LATEST_ARTICLES, page)));
    }

    /**
     * User's site is to display all posted articles, ordered by date of creation, paginated.
     *
     * @param username - username of User
     * @param page - page index
     * @return usersWeb template, or notFound if User with username doesn't exists
     */
    public Result usersWeb(String username, int page) {
        return userServiceInf.findUserByUsername(username)
                .map(userDTO ->
                        articleServiceInf.findNLatestArticlesForUsername(N_OF_LATEST_ARTICLES, page, username)
                                .map(articleDTOs -> ok(usersWeb.render(userDTO, articleDTOs)))
                                .orElseGet(Results::notFound))
                .orElseGet(Results::notFound);
    }

}
