package br.ufal.ic.prog2.View;

import br.ufal.ic.prog2.Factory.ControllerFactory;
import br.ufal.ic.prog2.Factory.StorageFactory;
import br.ufal.ic.prog2.Factory.ViewFactory;
import br.ufal.ic.prog2.Model.Bean.User;

import java.util.ArrayList;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class FriendsCLI extends BaseCLI {

    public void showHeader(){
        super.showHeader();
        System.out.println("> Friends");
    }

    @Override
    protected void showHeader(String action) {
        showHeader();
        System.out.println("+ "+action);
    }

    public String dialogAskUsername(String action) {
        showHeader(action);
        return getNextWord("Username: ");
    }

    public String dialogSearchByUsername(ArrayList<String> sortedSearch, String searchTerm) {
        User loggedUser = ControllerFactory.getUserController().getLoggedUser();
        for (int i = 0; i < sortedSearch.size(); i++) {
            try{
                User searchUser = StorageFactory.getUserStorage().getUserByUsername(sortedSearch.get(i));
                if(loggedUser.getFriends().contains(searchUser)){
                    sortedSearch.set(i, sortedSearch.get(i) + " (Go to Profile)");
                } else {
                    sortedSearch.set(i, sortedSearch.get(i) + " (Request Friendship)");
                }
            } catch (Exception e){
                break;
            }
        }

        String response = getFromPagedMenu("Search by Username", sortedSearch, searchTerm, 5);
        if(response == null){return null;}

        return response.replace(" (Go to Profile)","").replace(" (Request Friendship)","");
    }

    public String dialogFriendshipRequests(ArrayList<User> friends) {
        User loggedUser = ControllerFactory.getUserController().getLoggedUser();
        ArrayList<String> requests = new ArrayList<>();

        for (User f : friends) {
            if(loggedUser.getFriends().contains(f)){
                loggedUser.getFriendInvites().remove(f);
            } else {
                requests.add("Accept request from @"+f.getUsername());
            }
        }

        String response = getFromPagedMenu("Friendship Requests", requests, null, 8);
        if(response == null){return null;}

        return response.replace("Accept request from @","");
    }

    public String dialogRemoveFriends(ArrayList<User> friends) {
        User loggedUser = ControllerFactory.getUserController().getLoggedUser();
        ArrayList<String> requests = new ArrayList<>();

        for (User f : friends) {
            if(loggedUser.getFriends().contains(f)){
                loggedUser.getFriendInvites().remove(f);
            } else {
                requests.add("Unfriend "+f.getUsername());
            }
        }

        String response = getFromPagedMenu("Unfriend", requests, null, 8);
        if(response == null){return null;}

        return response.replace("Unfriend ","");
    }

    public void displaySentInvite(User user){
        showHeader("Friendship Invite");
        System.out.println("\nSent invite to "+ ViewFactory.getUserCLI().displayCompactUser(user));
        getEnter();
    }

    public void displayRemovedFriend(User friend){
        showHeader("Unfriend");
        System.out.println("\n"+ViewFactory.getUserCLI().displayCompactUser(friend) + "was removed from your friends list...");
        getEnter();
    }

    public void displayFriendProfile(User friend){
        showHeader("View Profile");
        System.out.println("\n"+ViewFactory.getUserCLI().displayUser(friend));
        getEnter();
    }

    public void failedToFind(String username){
        System.out.println("\nError: Failed to find friend "+username);
        getEnter();
    }
}
