package br.ufal.ic.prog2.Model.DAO;

import br.ufal.ic.prog2.Model.Bean.Community;
import br.ufal.ic.prog2.Model.DAO.ResponseEnums.CreateCommunityResponse;
import br.ufal.ic.prog2.Factory.ControllerFactory;

import java.util.HashMap;
import java.util.Map;

public class CommunityStorage extends BaseStorage{

    private final Map<String, Community> memoryDatabase;
    private final Map<String, String> nameToCidDatabase;
    protected String ID_PREFIX = "C";

    public CommunityStorage(){
        this.memoryDatabase = new HashMap<>();
        this.nameToCidDatabase = new HashMap<>();
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

    public Community getCommunityById(String cid){
        if(memoryDatabase.containsKey(cid)){
            return memoryDatabase.get(cid);
        }
        return null;
    }

    public Community getCommunityByName(String name){
        if(nameToCidDatabase.containsKey(name)){
            return getCommunityById(nameToCidDatabase.get(name));
        }

        return null;
    }

    public CreateCommunityResponse createCommunity(Community community){
        if(community != null){
            String id = null;
            while(id == null || memoryDatabase.containsKey(id)){
                id = generateId();
            }

            community.setId(id);
            if(community.getName() != null && !nameToCidDatabase.containsKey(community.getName())) {
                if(community.getOwner() == null){
                    community.setOwner(ControllerFactory.getUserController().getLoggedUser());
                }

                memoryDatabase.put(community.getId(), community);
                nameToCidDatabase.put(community.getName(), community.getId());
                return CreateCommunityResponse.OK;
            }
            return CreateCommunityResponse.NAME_ALREADY_EXISTS;
        }
        return CreateCommunityResponse.INVALID_COMMUNITY;
    }

    public Map<String, Community> getMemoryDatabase() {
        return memoryDatabase;
    }

    public Map<String, String> getNameToCidDatabase() {
        return nameToCidDatabase;
    }
}
