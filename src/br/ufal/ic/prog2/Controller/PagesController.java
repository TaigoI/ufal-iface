package br.ufal.ic.prog2.Controller;

import br.ufal.ic.prog2.Factory.ControllerFactory;
import br.ufal.ic.prog2.Factory.StorageFactory;
import br.ufal.ic.prog2.Factory.ViewFactory;
import br.ufal.ic.prog2.Model.Bean.Community;
import br.ufal.ic.prog2.Model.Bean.Feed;
import br.ufal.ic.prog2.Model.Bean.Post;
import br.ufal.ic.prog2.Model.Bean.User;
import br.ufal.ic.prog2.View.PagesCLI;

import java.util.ArrayList;

public class PagesController {

    private final PagesCLI CLI;

    public PagesController(){
        this.CLI = ViewFactory.getPagesCLI();
    }

    public boolean publicMainPage(){
        switch (CLI.dialogUnloggedOptions()) {
            case 0 -> {return true;}
            case 1 -> ControllerFactory.getUserController().createUser();
            case 2 -> ControllerFactory.getUserController().login();
        }
        return false;
    }

    public void privateMainPage(){
        switch (CLI.dialogMainMenu()) {
            case 1 -> feedPage();
            case 2 -> communitiesPage();
            case 3 -> chatsPage();
            case 4 -> profilePage();
            case 5 -> friendsPage();
        }
    }

    public void feedPage(){
        User loggedUser = ControllerFactory.getUserController().getLoggedUser();
        Post post = ControllerFactory.getFeedController().getFriendsOrPublicNextPost(loggedUser);

        while(true){
            switch (CLI.dialogMainFeed(post)) {
                case 0 -> {return;}
                case 1 -> {
                    switch (ViewFactory.getPostCLI().dialogPostViewControl()){
                        case 0 -> post = ControllerFactory.getFeedController().createPublicPost(loggedUser);
                        case 1 -> post = ControllerFactory.getFeedController().createPost(loggedUser.getFeed(), loggedUser);
                    }
                }
                case 2 -> post = ControllerFactory.getFeedController().getFriendsOrPublicNextPost(loggedUser);
                case 3 -> post = ControllerFactory.getFeedController().previousPost(loggedUser);
            }
        }
    }

    public void communitiesPage(){
        while(true){
            switch (CLI.dialogCommunities()) {
                case 0 -> {return;}
                case 1 -> ControllerFactory.getCommunityController().createCommunityDialog();
                case 2 -> CLI.dialogCommunitiesListAll();
                case 3 -> CLI.dialogCommunitiesListMine();
                case 4 -> {
                    User loggedUser = ControllerFactory.getUserController().getLoggedUser();
                    String cName = ControllerFactory.getCommunityController().searchCommunitiesByName();
                    if(cName == null) break;

                    if(StorageFactory.getCommunityStorage().nameDoesntExists(cName)){
                        ViewFactory.getCommunitiesCLI().failedToFind(cName);
                        break;
                    }

                    Community c = StorageFactory.getCommunityStorage().getCommunityByName(cName);
                    if(c.getMembers().contains(loggedUser)){
                        communityPage(c);
                    } else {
                        ControllerFactory.getCommunityController().sendRequest(c);
                        ViewFactory.getCommunitiesCLI().displayRequestSent(c);
                    }
                }
            }
        }
    }

    public void communityPage(Community community){
        while(true){
            switch (CLI.dialogCommunityPage(community)) {
                case 0 -> {return;}
                case 1 -> communityFeed(community);
                case 2 -> ViewFactory.getCommunitiesCLI().displayAllMembers(community);
                case 3 -> ControllerFactory.getCommunityController().approveMember(community);
            }
        }
    }

    public void communityFeed(Community community){
        User loggedUser = ControllerFactory.getUserController().getLoggedUser();
        ArrayList<Feed> feed = new ArrayList<>();
        feed.add(community.getFeed());

        Post post = ControllerFactory.getFeedController().nextPostFromFeeds(feed, loggedUser);

        while(true){
            switch (CLI.dialogCommunityFeed(post, community)) {
                case 0 -> {return;}
                case 1 -> post = ControllerFactory.getFeedController().createPost(community.getFeed(), loggedUser);
                case 2 -> post = ControllerFactory.getFeedController().nextPostFromFeeds(feed, loggedUser);
                case 3 -> post = ControllerFactory.getFeedController().previousPost(community.getFeed(), loggedUser);
            }
        }
    }

    public void chatsPage(){

    }

    public void profilePage(){
        while(true){
            switch (CLI.dialogProfile()) {
                case 0 -> {return;}
                case 1 -> ControllerFactory.getUserController().showLoggedUser();
                case 2 -> ControllerFactory.getUserController().updateUser();
                case 3 -> {
                    if(ControllerFactory.getUserController().logout()) return;
                }
                case 4 -> {
                    if(ControllerFactory.getUserController().deleteUser()) return;
                }
            }
        }
    }

    public void friendsPage(){
        while(true){
            switch (CLI.dialogFriends()) {
                case 0 -> {return;}
                case 1 -> CLI.dialogFriendsListAll();
                case 2 -> CLI.dialogFriendsListMine();
                case 3 -> {
                    User loggedUser = ControllerFactory.getUserController().getLoggedUser();
                    String username = ControllerFactory.getFriendsController().showFriendshipRequests(loggedUser);

                    if(username == null) break;
                    if(StorageFactory.getUserStorage().usernameDoesntExists(username)){
                        ViewFactory.getUserCLI().failedToFind(username);
                        ControllerFactory.getFriendsController().removeInviteByUsernames(username, loggedUser.getUsername());
                        break;
                    }

                    User f = StorageFactory.getUserStorage().getUserByUsername(username);
                    ControllerFactory.getFriendsController().addFriend(f);
                }
                case 4 -> {
                    String username = ControllerFactory.getFriendsController().searchProfilesByUsername();
                    if(username == null) break;
                    if(StorageFactory.getUserStorage().usernameDoesntExists(username)){
                        ViewFactory.getUserCLI().failedToFind(username);
                        break;
                    }

                    User loggedUser = ControllerFactory.getUserController().getLoggedUser();
                    User u = StorageFactory.getUserStorage().getUserByUsername(username);
                    if(loggedUser.getFriends().contains(u)){
                        ViewFactory.getFriendsCLI().displayFriendProfile(u);
                    } else {
                        ControllerFactory.getFriendsController().sendInvite(u);
                        ViewFactory.getFriendsCLI().displaySentInvite(u);
                    }
                }
                case 5 -> {
                    User loggedUser = ControllerFactory.getUserController().getLoggedUser();

                    String username = ControllerFactory.getFriendsController().searchToUnfriend(loggedUser);
                    if(username == null) break;
                    if(StorageFactory.getUserStorage().usernameDoesntExists(username)){
                        ViewFactory.getUserCLI().failedToFind(username);
                        break;
                    }
                    User u = StorageFactory.getUserStorage().getUserByUsername(username);


                    if(loggedUser.getFriends().contains(u)){
                        ControllerFactory.getFriendsController().removeFriend(u);
                        ViewFactory.getFriendsCLI().displayRemovedFriend(u);
                    } else {
                        ViewFactory.getFriendsCLI().failedToFind(username);
                    }
                }
            }
        }
    }
}
