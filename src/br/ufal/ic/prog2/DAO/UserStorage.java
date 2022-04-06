package br.ufal.ic.prog2.DAO;

import br.ufal.ic.prog2.Bean.User;
import br.ufal.ic.prog2.DAO.ResponseEnums.CreateUserResponse;

import java.util.HashMap;
import java.util.Map;


public class UserStorage {

    private final Map<String, User> memoryDatabase;
    private final Map<String, String> usernameToUidDatabase;

    public UserStorage(){
        this.memoryDatabase = new HashMap<>();
        this.usernameToUidDatabase = new HashMap<>();
    }

    public boolean isEmpty(){
        return (memoryDatabase.size() <= 0);
    }

    public boolean uidAlreadyExists(String uid){
        return memoryDatabase.containsKey(uid);
    }

    public boolean usernameAlreadyExists(String name){
        return usernameToUidDatabase.containsKey(name);
    }

    public User getUserByUid(String uid){
        if(memoryDatabase.containsKey(uid)){
           return  memoryDatabase.get(uid);
        }

        return null;
    }

    public User attemptLogin(String username, String password){
        if(username != null && usernameToUidDatabase.containsKey(username)) {
            String uid = usernameToUidDatabase.get(username);
            User user = memoryDatabase.get(uid);
            if(user.getPassword().equals(password)){
                return user;
            }
        }
        return null;
    }

    public User getUserByDisplayName(String name){
        if(usernameToUidDatabase.containsKey(name)){
            return getUserByUid(usernameToUidDatabase.get(name));
        }

        return null;
    }

    public CreateUserResponse storeUser(User user){
        if(user != null){
            if(user.getUid() != null && !memoryDatabase.containsKey(user.getUid())){
                if(user.getUsername() != null && !usernameToUidDatabase.containsKey(user.getUsername())) {
                    memoryDatabase.put(user.getUid(), user);
                    usernameToUidDatabase.put(user.getUsername(), user.getUid());
                    return CreateUserResponse.OK;
                }
                return CreateUserResponse.NAME_ALREADY_EXISTS;
            }
            return CreateUserResponse.UID_ALREADY_EXISTS;
        }
        return CreateUserResponse.INVALID_USER;
    }

    public Map<String, User> getMemoryDatabase() {
        return memoryDatabase;
    }

    public Map<String, String> getUsernameToUidDatabase() {
        return usernameToUidDatabase;
    }
}
