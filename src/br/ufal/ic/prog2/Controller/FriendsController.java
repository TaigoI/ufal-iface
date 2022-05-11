package br.ufal.ic.prog2.Controller;

import br.ufal.ic.prog2.Factory.ControllerFactory;
import br.ufal.ic.prog2.Factory.StorageFactory;
import br.ufal.ic.prog2.Factory.ViewFactory;
import br.ufal.ic.prog2.Model.Bean.User;
import br.ufal.ic.prog2.View.FriendsCLI;
import org.apache.commons.text.similarity.LevenshteinDistance;

import java.util.*;

import static java.lang.Math.max;

public class FriendsController {

    private final FriendsCLI CLI;

    public FriendsController(){
        this.CLI =  ViewFactory.getFriendsCLI();
    }

    public boolean removeInviteByUsernames(String username1, String username2){
        User u1 = StorageFactory.getUserStorage().getUserByUsername(username1);
        User u2 = StorageFactory.getUserStorage().getUserByUsername(username2);

        if(u1 != null){
            u1.getFriendInvites().removeIf(u -> u.getUsername().equals(username2));
        }

        if(u2 != null){
            u2.getFriendInvites().removeIf(u -> u.getUsername().equals(username1));
        }

        return true;
    }

    public boolean sendInvite(User newFriend){
        User loggedUser = ControllerFactory.getUserController().getLoggedUser();

        if(!newFriend.getFriendInvites().contains(loggedUser)){
            newFriend.getFriendInvites().add(loggedUser);
        }

        return true;
    }

    public boolean addFriend(User newFriend){
        User loggedUser = ControllerFactory.getUserController().getLoggedUser();

        //add to friends
        if(!loggedUser.getFriends().contains(newFriend)){
            loggedUser.getFriends().add(newFriend);
        }
        if(!newFriend.getFriends().contains(loggedUser)){
            newFriend.getFriends().add(loggedUser);
        }

        //remove from friend invites
        loggedUser.getFriendInvites().remove(newFriend);
        newFriend.getFriendInvites().remove(loggedUser);

        return true;
    }

    public boolean removeFriend(User oldFriend){
        User loggedUser = ControllerFactory.getUserController().getLoggedUser();

        //remove from friends
        loggedUser.getFriends().remove(oldFriend);
        oldFriend.getFriends().remove(loggedUser);

        //remove from friend invites
        loggedUser.getFriendInvites().remove(oldFriend);
        oldFriend.getFriendInvites().remove(loggedUser);

        return true;
    }

    public String listAllProfiles(){
        String result = "";

        Map<String, User> users = new HashMap<>(StorageFactory.getUserStorage().getMemoryDatabase());
        users.remove(ControllerFactory.getUserController().getLoggedUser().getId());

        for (User user : users.values()){
            result = result.concat(ViewFactory.getUserCLI().displayCompactUser(user)+"\n");
        }
        return result;
    }

    public String listMyFriends(User u){
        String result = "";
        for (User user : ControllerFactory.getUserController().getLoggedUser().getFriends()){
            result = result.concat(ViewFactory.getUserCLI().displayCompactUser(user)+"\n");
        }
        return result;
    }

    public String showFriendshipRequests(User u){
        ArrayList<User> invites = u.getFriendInvites();
        invites.sort(Comparator.comparing(User::getUsername));
        return CLI.dialogFriendshipRequests(invites);
    }

    private static class Distance {
        public Double distance;
        public String name;
    }

    public ArrayList<String> sortTitlesByName(String searchName, Set<String> names){
        LevenshteinDistance distanceCalculator = new LevenshteinDistance();
        ArrayList<Distance> distances = new ArrayList<>();

        for (String communityName : names){
            Integer distance = distanceCalculator.apply(communityName, searchName);
            Distance d = new Distance();
            d.distance = Double.valueOf(distance) / (max(communityName.length(), searchName.length()));
            if(communityName.contains(searchName)){
                d.distance = d.distance - 0.5;
            }

            d.name = communityName;
            distances.add(d);
        }

        distances.sort(Comparator.comparingDouble(d -> d.distance));

        ArrayList<String> response = new ArrayList<>();
        for (Distance d : distances){
            response.add(d.name);
        }

        return response;
    }

    public String searchProfilesByUsername(){
        User loggedUser = ControllerFactory.getUserController().getLoggedUser();
        String username = CLI.dialogAskUsername("Search Profile");

        Set<String> usersSet = new HashSet<>(StorageFactory.getUserStorage().getUsernameToUidDatabase().keySet());
        usersSet.remove(loggedUser.getUsername());

        for(String uname : usersSet){
            User u = StorageFactory.getUserStorage().getUserByUsername(uname);
            if(u.getFriendInvites().contains(loggedUser)){
                usersSet.remove(uname);
            }
        }

        ArrayList<String> sortedSearch = sortTitlesByName(username, usersSet);
        return CLI.dialogSearchByUsername(sortedSearch, username);
    }

    public String searchToUnfriend(User loggedUser){
        ArrayList<User> friends = loggedUser.getFriends();
        friends.sort(Comparator.comparing(User::getUsername));

        return CLI.dialogRemoveFriends(friends);
    }

}
