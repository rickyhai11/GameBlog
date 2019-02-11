package controllers;

import actions.Authenticated;
import actions.NotAuthenticated;
import datamodels.dto.LoginDTO;
import datamodels.dto.UserDTO;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import services.interfaces.UserServiceInf;
import views.html.login;
import views.html.register;

import javax.inject.Inject;

/**
 * Controller with actions related to Users, including login and register actions.
 */
public class UserController extends Controller {

    private final UserServiceInf userServiceInf;
    private final Form<LoginDTO> loginForm;
    private final Form<UserDTO> registrationForm;

    @Inject
    public UserController(UserServiceInf userServiceInf, FormFactory formFactory) {
        this.userServiceInf = userServiceInf;
        this.loginForm = formFactory.form(LoginDTO.class);
        this.registrationForm = formFactory.form(UserDTO.class);
    }

    /**
     * GET login form.
     */
    public Result getLoginForm() {
        return ok(login.render(loginForm));
    }

    /**
     * POST login form.
     */
    public Result login() {
        Form<LoginDTO> loginDTOForm = loginForm.bindFromRequest();
        if (loginDTOForm.hasErrors()) {
            return badRequest(login.render(loginDTOForm));
        } else {
            return userServiceInf.authenticateUser(loginDTOForm.get())
                    .map(loginDTO -> {
                        session("username", loginDTO.username);
                        return redirect(routes.WebController.web(1));
                    })
                    .orElse(badRequest(login.render(loginDTOForm.withGlobalError(
                            "Your username and password didn't match. Please try again."))));
        }
    }

    /**
     * Logout action.
     * Only authenticated users can logout.
     */
    @Authenticated
    public Result logout() {
        session().clear();
        return redirect(routes.WebController.web(1));
    }

    /**
     * GET registration Form.
     * Only unauthenticated users can see this page.
     */
    @NotAuthenticated
    public Result getRegistrationForm() {
        return ok(register.render(registrationForm));
    }

    /**
     * POST registration Form.
     * Only unauthenticated users can use this.
     */
    @NotAuthenticated
    public Result register() {
        Form<UserDTO> registrationForm = this.registrationForm.bindFromRequest();
        if (registrationForm.hasErrors()) {
            return badRequest(register.render(registrationForm));
        } else {
            return userServiceInf.saveUser(registrationForm.get())
                    .map(userDTO -> redirect(routes.UserController.login()))
                    .orElse(badRequest(register.render(registrationForm.withGlobalError(
                            "Username with those credentials already exists. Please try again"))));
        }
    }
}
