package br.ufal.ic.prog2.Controller;

import br.ufal.ic.prog2.Factory.ViewFactory;
import br.ufal.ic.prog2.Model.Bean.Community;
import br.ufal.ic.prog2.Model.Bean.Feed;
import br.ufal.ic.prog2.Model.Bean.User;
import br.ufal.ic.prog2.Model.DAO.CommunityStorage;
import br.ufal.ic.prog2.Factory.ControllerFactory;
import br.ufal.ic.prog2.Factory.StorageFactory;
import br.ufal.ic.prog2.View.CommunityCLI;
import org.apache.commons.text.similarity.LevenshteinDistance;

import java.util.*;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class CommunityController {

    private CommunityCLI CLI;
    private CommunityStorage STORAGE;

    public CommunityController(){
        this.CLI =  ViewFactory.getCommunitiesCLI();
        this.STORAGE = StorageFactory.getCommunityStorage();
    }

    public void sendRequest(Community community){
        User loggedUser = ControllerFactory.getUserController().getLoggedUser();
        community.getRequestedMemberships().add(loggedUser);
    }

    public void removeRequest(Community community, String username){
        community.getRequestedMemberships().removeIf(u -> u.getUsername().equals(username));
    }

    public Community createCommunityDialog(){
        User loggedUser = ControllerFactory.getUserController().getLoggedUser();
        String action = "Create Community";
        CommunityStorage communityStorage = StorageFactory.getCommunityStorage();

        CLI.showHeader();

        String name = CLI.dialogNewCommunityName(action);
        String description = CLI.dialogNewCommunityDescription(action);

        Community community = new Community();
        community.setName(name);
        community.setOwner(loggedUser);
        community.setDescription(description);

        Feed feed = FeedController.initializeNewFeed("Members of #"+name);
        community.setFeed(feed);

        ArrayList<User> members = new ArrayList<>();
        members.add(community.getOwner());
        community.setMembers(members);

        loggedUser.getCommunities().add(community);
        communityStorage.createCommunity(community);
        return community;
    }

    public String listAllCommunities(){
        String result = "";
        for (String cid : StorageFactory.getCommunityStorage().getMemoryDatabase().keySet()){
            Community community = StorageFactory.getCommunityStorage().getCommunityById(cid);
            result = result.concat(CLI.getCommunityAsText(community)+"\n");
        }
        return result;
    }

    public String listCommunities(User u){
        String result = "";
        for (Community community : u.getCommunities()){
            result = result.concat(CLI.getCommunityAsText(community)+"\n");
        }
        return result;
    }

    public String listAllMembers(Community community){
        String result = "Owner: "+ViewFactory.getUserCLI().displayCompactUser(community.getOwner())+"\n";


        ArrayList<User> members = new ArrayList<>(community.getMembers());
        members.remove(community.getOwner());

        for (User member : members){
            result = result.concat(ViewFactory.getUserCLI().displayCompactUser(member)+"\n");
        }
        return result;
    }

    private static class CommunityDistance{
        public Double distance;
        public String name;
    }

    public ArrayList<String> sortTitlesBySearchName(String searchName){
        LevenshteinDistance distanceCalculator = new LevenshteinDistance();
        Set<String> names = StorageFactory.getCommunityStorage().getNameToCidDatabase().keySet();
        ArrayList<CommunityDistance> distances = new ArrayList<>();

        for (String communityName : names){
            Integer distance = distanceCalculator.apply(communityName, searchName);
            CommunityDistance d = new CommunityDistance();
            d.distance = Double.valueOf(distance) / (max(communityName.length(), searchName.length()));
            if(communityName.contains(searchName)){
                d.distance = d.distance - 0.5;
            }

            d.name = communityName;
            distances.add(d);
        }

        distances.sort(Comparator.comparingDouble(c -> c.distance));

        ArrayList<String> response = new ArrayList<>();
        for (CommunityDistance d : distances){
            response.add(d.name);
        }

        return response;
    }

    public String searchCommunitiesByName() {
        String name = CLI.dialogAskCommunityName("Open via Search");
        ArrayList<String> sortedSearch = sortTitlesBySearchName(name);


        for(int i = 0; i < sortedSearch.size(); i++){
            Community c = StorageFactory.getCommunityStorage().getCommunityByName(sortedSearch.get(i));
            if(c.getMembers().contains(ControllerFactory.getUserController().getLoggedUser())){
                sortedSearch.set(i, sortedSearch.get(i)+" (View)");
            } else {
                sortedSearch.set(i, sortedSearch.get(i)+" (Request Membership)");
            }
        }

        String response = CLI.dialogSearchByName(sortedSearch, name);
        if(response == null) return null;

        return response.replace(" (View)","").replace(" (Request Membership)","");
    }

    public void approveMember(Community community){
        String username = ViewFactory.getCommunitiesCLI().dialogMembershipRequests(community);

        if(username == null) return;

        removeRequest(community, username);

        if(StorageFactory.getUserStorage().usernameDoesntExists(username)){
            ViewFactory.getUserCLI().failedToFind(username);
            return;
        }

        User m = StorageFactory.getUserStorage().getUserByUsername(username);
        community.getMembers().add(m);
        m.getCommunities().add(community);
    }

}
