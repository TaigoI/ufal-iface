package br.ufal.ic.prog2.View;

import br.ufal.ic.prog2.Factory.ControllerFactory;
import br.ufal.ic.prog2.Factory.ViewFactory;
import br.ufal.ic.prog2.Model.Bean.Community;
import br.ufal.ic.prog2.Model.Bean.Post;
import br.ufal.ic.prog2.Model.Bean.User;

import java.util.ArrayList;

public class PagesCLI extends BaseCLI{

    @Override
    public void showHeader(String action){
        super.showHeader();
        System.out.println("+ "+action);
    }

    public int dialogUnloggedOptions(){
        showHeader("Welcome");

        ArrayList<Integer> indices = new ArrayList<>();
        ArrayList<String> descriptions = new ArrayList<>();

        indices.add(1); descriptions.add("New User");
        indices.add(2); descriptions.add("Login");
        indices.add(0); descriptions.add("Close Application");

        return getNextOption("\nChoose: ", indices, descriptions);
    }

    public int dialogMainMenu(){
        showHeader();

        ArrayList<Integer> indices = new ArrayList<>();
        ArrayList<String> descriptions = new ArrayList<>();

        indices.add(1); descriptions.add("Feed");
        indices.add(2); descriptions.add("Communities");
        indices.add(3); descriptions.add("Chat");
        indices.add(4); descriptions.add("My Profile");
        indices.add(5); descriptions.add("Friends");

        return getNextOption("\nChoose: ", indices, descriptions);
    }

    public int dialogMainFeed(Post post){
        showHeader("Main Feed");
        ViewFactory.getPostCLI().showPostAsText(post);

        ArrayList<Integer> indices = new ArrayList<>();
        ArrayList<String> descriptions = new ArrayList<>();

        indices.add(1); descriptions.add("New Post");
        indices.add(2); descriptions.add("Next");
        indices.add(3); descriptions.add("Previous");
        indices.add(0); descriptions.add("Return");
        return getNextOption("\nChoose: ", indices, descriptions);
    }

    public int dialogCommunities(){
        showHeader("Communities");

        ArrayList<Integer> indices = new ArrayList<>();
        ArrayList<String> descriptions = new ArrayList<>();

        indices.add(1); descriptions.add("New Community");
        indices.add(2); descriptions.add("List All");
        indices.add(3); descriptions.add("List Mine");
        indices.add(4); descriptions.add("Open via Search");
        indices.add(0); descriptions.add("Return");
        return getNextOption("\nChoose: ", indices, descriptions);
    }

    public void dialogCommunitiesListAll(){
        showHeader("Communities [List All]");

        String list = ControllerFactory.getCommunityController().listAllCommunities();
        System.out.println();
        System.out.println(list.equals("") ? "There are no communities yet..." : list);
        System.out.println();

        getEnter();
    }

    public void dialogCommunitiesListMine(){
        showHeader("Communities [List All]");

        User loggedUser = ControllerFactory.getUserController().getLoggedUser();

        String list = ControllerFactory.getCommunityController().listCommunities(loggedUser);
        System.out.println();
        System.out.println(list.equals("") ? "You are not a member in any community..." : list);
        System.out.println();

        getEnter();
    }

    public int dialogCommunityFeed(Post post, Community community){
        showHeader("Posts on #"+community.getName());
        ViewFactory.getPostCLI().showPostAsText(post);

        ArrayList<Integer> indices = new ArrayList<>();
        ArrayList<String> descriptions = new ArrayList<>();

        indices.add(1); descriptions.add("New Post");
        indices.add(2); descriptions.add("Next");
        indices.add(3); descriptions.add("Previous");
        indices.add(0); descriptions.add("Return");
        return getNextOption("\nChoose: ", indices, descriptions);
    }

    public int dialogCommunityPage(Community community){
        showHeader("Community Page");

        ArrayList<Integer> indices = new ArrayList<>();
        ArrayList<String> descriptions = new ArrayList<>();

        indices.add(1); descriptions.add("View Feed");
        indices.add(2); descriptions.add("View Members");
        if(community.getOwner().getId().equals(ControllerFactory.getUserController().getLoggedUser().getId())){
            indices.add(3); descriptions.add("Membership Requests");
        }
        indices.add(0); descriptions.add("Return");
        return getNextOption("\nChoose: ", indices, descriptions);
    }

    public int dialogChat(){
        showHeader("Chats");

        ArrayList<Integer> indices = new ArrayList<>();
        ArrayList<String> descriptions = new ArrayList<>();

        indices.add(1); descriptions.add("List All");
        indices.add(2); descriptions.add("Search to Open");
        indices.add(0); descriptions.add("Return");
        indices.add(-1);

        return getNextOption("\nChoose: ", indices, descriptions);
    }

    public int dialogProfile(){
        showHeader("My Profile");

        ArrayList<Integer> indices = new ArrayList<>();
        ArrayList<String> descriptions = new ArrayList<>();

        indices.add(1); descriptions.add("View Profile");
        indices.add(2); descriptions.add("Edit Attributes");
        indices.add(3); descriptions.add("Logout");
        indices.add(0); descriptions.add("Return");
        indices.add(-1);
        indices.add(4); descriptions.add("Delete");

        return getNextOption("\nChoose: ", indices, descriptions);
    }

    public int dialogFriends(){
        showHeader("Friends");

        ArrayList<Integer> indices = new ArrayList<>();
        ArrayList<String> descriptions = new ArrayList<>();

        indices.add(1); descriptions.add("List All Profiles");
        indices.add(2); descriptions.add("List My Friends");
        indices.add(3); descriptions.add("View Friendship Requests");
        indices.add(4); descriptions.add("Search Profiles (Send Request or View Friend)");
        indices.add(5); descriptions.add("Unfriend");
        indices.add(0); descriptions.add("Return");

        return getNextOption("\nChoose: ", indices, descriptions);
    }

    public void dialogFriendsListAll(){
        showHeader("Friends [List All Profiles]");

        String list = ControllerFactory.getFriendsController().listAllProfiles();
        System.out.println();
        System.out.println(list.equals("") ? "There are no other profiles..." : list);
        System.out.println();

        getEnter();
    }

    public void dialogFriendsListMine(){
        showHeader("Friends [List Mine]");

        User loggedUser = ControllerFactory.getUserController().getLoggedUser();

        String list = ControllerFactory.getFriendsController().listMyFriends(loggedUser);
        System.out.println();
        System.out.println(list.equals("") ? "You don't have any friends..." : list);
        System.out.println();

        getEnter();
    }
}
