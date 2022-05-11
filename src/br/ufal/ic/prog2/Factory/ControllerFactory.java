package br.ufal.ic.prog2.Factory;

import br.ufal.ic.prog2.Controller.*;

public class ControllerFactory {

    private static final UserController USER_CONTROLLER = new UserController();
    private static final PagesController PAGES_CONTROLLER = new PagesController();
    private static final FeedController FEED_CONTROLLER = new FeedController();
    private static final CommunityController COMMUNITY_CONTROLLER = new CommunityController();
    private static final FriendsController FRIENDS_CONTROLLER = new FriendsController();

    public static UserController getUserController(){
        return USER_CONTROLLER;
    }
    public static PagesController getPagesController() {return PAGES_CONTROLLER;}
    public static FeedController getFeedController() {
        return FEED_CONTROLLER;
    }
    public static CommunityController getCommunityController() {
        return COMMUNITY_CONTROLLER;
    }
    public static FriendsController getFriendsController(){
        return FRIENDS_CONTROLLER;
    }

}
