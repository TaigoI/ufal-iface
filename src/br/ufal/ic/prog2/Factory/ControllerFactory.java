package br.ufal.ic.prog2.Factory;

import br.ufal.ic.prog2.Controller.FeedController;
import br.ufal.ic.prog2.Controller.UserController;

public class ControllerFactory {

    private static final UserController userController = new UserController();
    private static final FeedController feedController = new FeedController();

    public static UserController getUserController(){
        return userController;
    }
    public static FeedController getFeedController() {
        return feedController;
    }

}
