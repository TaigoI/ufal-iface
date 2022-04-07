package br.ufal.ic.prog2.Factory;

import br.ufal.ic.prog2.Controller.CommunityController;
import br.ufal.ic.prog2.Controller.FeedController;
import br.ufal.ic.prog2.Controller.UserController;

public class ControllerFactory {

    private static final UserController USER_CONTROLLER = new UserController();
    private static final FeedController FEED_CONTROLLER = new FeedController();
    private static final CommunityController COMMUNITY_CONTROLLER = new CommunityController();

    public static UserController getUserController(){
        return USER_CONTROLLER;
    }
    public static FeedController getFeedController() {
        return FEED_CONTROLLER;
    }
    public static CommunityController getCommunityController() {
        return COMMUNITY_CONTROLLER;
    }

}
