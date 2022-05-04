package br.ufal.ic.prog2.Controller;

import br.ufal.ic.prog2.Factory.ViewFactory;
import br.ufal.ic.prog2.Model.Bean.Community;
import br.ufal.ic.prog2.Model.Bean.Feed;
import br.ufal.ic.prog2.Model.Bean.Post;
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

    private static final Map<String, Map<String, ArrayList<Post>>> communitiesHistory = new HashMap<>();
    private static final Map<String, Map<String, Integer>> communitiesPosition = new HashMap<>();
    private CommunityCLI CLI;
    private CommunityStorage STORAGE;

    CommunityController(){
        this.CLI =  ViewFactory.getCommunitiesCLI();
        this.STORAGE = StorageFactory.getCommunityStorage();
    }

    public Community createCommunityDialog(){
        String action = "Nova Comunidade";
        CommunityStorage communityStorage = StorageFactory.getCommunityStorage();

        CLI.showHeader();

        String name = CLI.dialogCommunityName(action);
        while(communityStorage.nameAlreadyExists(name)){
            System.out.println("\n O nome escolhido \""+name+"\" já existe...\n");
            name = CLI.dialogCommunityName(action);
        }

        System.out.println("Informe a Descrição: ");
        String description = CLI.dialogCommunityDescription(action);

        Community community = new Community();
        community.setName(name);
        community.setOwner(ControllerFactory.getUserController().getLoggedUser());
        community.setDescription(description);

        Feed feed = new Feed();
        feed.setPosts(new ArrayList<>());
        feed.setHistory(new HashMap<>());
        feed.setLastSeen(new HashMap<>());
        community.setFeed(feed);

        ArrayList<User> members = new ArrayList<>();
        members.add(community.getOwner());
        community.setMembers(members);

        communityStorage.createCommunity(community);

        System.out.println("Comunidade criada com sucesso!");
        return community;
    }

    public String listCommunities(){
        String result = "";
        for (String cid : StorageFactory.getCommunityStorage().getMemoryDatabase().keySet()){
            Community community = StorageFactory.getCommunityStorage().getCommunityById(cid);
            result = result.concat(CLI.getCommunityAsText(community)+"\n");
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

        distances.sort(new Comparator<>() {
            @Override
            public int compare(CommunityDistance C1, CommunityDistance C2) {
                return Double.compare(C1.distance, C2.distance);
            }
        });

        ArrayList<String> response = new ArrayList<>();
        for (CommunityDistance d : distances){
            response.add(d.name);
        }

        return response;
    }

    public String searchCommunitiesByName() {
        String name = CLI.dialogCommunityName("Busca");
        ArrayList<String> sortedSearch = sortTitlesBySearchName(name);
        return CLI.dialogSearchByName(sortedSearch);
    }
}
