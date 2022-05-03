package br.ufal.ic.prog2.Model.DAO;

import br.ufal.ic.prog2.Model.Bean.Community;
import br.ufal.ic.prog2.Model.Bean.Post;
import br.ufal.ic.prog2.Model.Bean.User;
import br.ufal.ic.prog2.Model.DAO.ResponseEnums.CreateCommunityResponse;
import br.ufal.ic.prog2.Factory.ControllerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CommunityStorage {

    private final Map<String, Community> memoryDatabase;
    private final Map<String, String> nameToCidDatabase;

    private final Map<String, Map<String, Integer>> communityLastSeens;
    private final Map<String, ArrayList<Post>> communityFeeds;

    public CommunityStorage(){
        this.memoryDatabase = new HashMap<>();
        this.nameToCidDatabase = new HashMap<>();
        this.communityLastSeens = new HashMap<>();
        this.communityFeeds = new HashMap<>();
    }

    public boolean isEmpty(){
        return (memoryDatabase.size() <= 0);
    }

    public boolean cidAlreadyExists(String cid){
        return memoryDatabase.containsKey(cid);
    }

    public boolean nameAlreadyExists(String name){
        return nameToCidDatabase.containsKey(name);
    }

    public Community getCommunityByCid(String cid){
        if(memoryDatabase.containsKey(cid)){
            return memoryDatabase.get(cid);
        }
        return null;
    }

    public Community getCommunityByName(String name){
        if(nameToCidDatabase.containsKey(name)){
            return getCommunityByCid(nameToCidDatabase.get(name));
        }

        return null;
    }

    public CreateCommunityResponse storeCommunity(Community community){
        if(community != null){
            if(community.getCid() != null && !memoryDatabase.containsKey(community.getCid())){
                if(community.getName() != null && !nameToCidDatabase.containsKey(community.getName())) {
                    if(community.getOwner() == null){
                        community.setOwner(ControllerFactory.getUserController().getLoggedUser());
                    }

                    memoryDatabase.put(community.getCid(), community);
                    nameToCidDatabase.put(community.getName(), community.getCid());
                    return CreateCommunityResponse.OK;
                }
                return CreateCommunityResponse.NAME_ALREADY_EXISTS;
            }
            return CreateCommunityResponse.CID_ALREADY_EXISTS;
        }
        return CreateCommunityResponse.INVALID_COMMUNITY;
    }

    public Map<String, Community> getMemoryDatabase() {
        return memoryDatabase;
    }

    public Map<String, String> getNameToCidDatabase() {
        return nameToCidDatabase;
    }

    public Post storePost(String cid, Post newPost){
        newPost.setTarget(memoryDatabase.get(cid).getName());
        communityFeeds.get(cid).add(newPost);
        return newPost;
    }

    public Post findNextPost(String cid, User loggedUser){
        String uid = loggedUser.getUid();

        if(!communityLastSeens.containsKey(cid)){
            communityLastSeens.put(cid, new HashMap<>());
        }
        Integer last = communityLastSeens.get(cid).get(uid);

        while(communityFeeds.get(cid).size() > communityLastSeens.get(cid).get(uid) + 1) {

            communityLastSeens.get(cid).put(uid, communityLastSeens.get(cid).get(uid) + 1);

            if (!communityFeeds.get(cid).get(communityLastSeens.get(cid).get(uid)).getOwner().getUid().equals(uid)) {
                return communityFeeds.get(cid).get(communityLastSeens.get(cid).get(uid));
            }
        }

        return null;
    }

}
