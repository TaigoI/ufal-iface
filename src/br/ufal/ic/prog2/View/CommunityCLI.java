package br.ufal.ic.prog2.View;

import br.ufal.ic.prog2.Factory.ControllerFactory;
import br.ufal.ic.prog2.Factory.StorageFactory;
import br.ufal.ic.prog2.Model.Bean.Community;
import br.ufal.ic.prog2.Model.Bean.User;

import java.util.ArrayList;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class CommunityCLI extends BaseCLI {

    public void showHeader(){
        super.showHeader();
        System.out.println("> Communities");
    }

    @Override
    public void showHeader(String action){
        showHeader();
        System.out.println("+ "+action);
    }

    public String dialogNewCommunityName(String action) {
        showHeader(action);
        String name = getNextSentence("Community Name: ");
        name = name.replace(" ","_");
        while(true){
            if(StorageFactory.getCommunityStorage().nameDoesntExists(name)) return name;

            name = getNextWord("The chosen name already exists... try again\nCommunity Name (no spaces): ");
        }
    }

    public String dialogNewCommunityDescription(String action) {
        showHeader(action);
        return getNextSentence("Community Description: ");
    }

    public String dialogAskCommunityName(String action) {
        showHeader(action);
        return getNextWord("Community name: ");
    }

    public String dialogSearchByName(ArrayList<String> sortedSearch, String searchTerm) {


        return getFromPagedMenu("Search by Name", sortedSearch, searchTerm, 5);
    }

    public String getCommunityAsText(Community community){
        if(community == null){
            return "Undefined Community.\n";
        }

        return ""
                + community.getId() +": {\n"
                + ("name:        \"" + community.getName() + "\"\n").indent(4)
                + ("description: \"" + community.getDescription().replaceAll("(.{0,70})\\s+", "$1\n") + "\"\n").indent(4)
                +"}\n";
    }

    public String getCompactCommunity(Community community){
        return "#"+community.getName()+" ("+community.getId()+")";
    }

    public void showCommunityAsText(Community community){
        System.out.println(getCommunityAsText(community));
    }

    public void displayRequestSent(Community community){
        showHeader("Membership");
        System.out.println("\nSent request to "+ getCompactCommunity(community));
        getEnter();
    }

    public void failedToFind(String name){
        System.out.println("Error: Failed to find community "+name);
        getEnter();
    }

    public String dialogMembershipRequests(Community community) {
        User loggedUser = ControllerFactory.getUserController().getLoggedUser();
        ArrayList<String> requests = new ArrayList<>();

        for (User m : new ArrayList<>(community.getRequestedMemberships())) {
            if(community.getMembers().contains(m)){
                community.getMembers().remove(m);
            } else {
                requests.add("Accept request from @"+m.getUsername());
            }
        }

        String response = getFromPagedMenu("Membership Requests", requests, null, 5);
        if(response == null){return null;}

        return response.replace("Accept request from @","");
    }

    public void displayAllMembers(Community community){
        System.out.println("\n"+ControllerFactory.getCommunityController().listAllMembers(community));
        getEnter();
    }
}
