package br.ufal.ic.prog2.Model.DAO;

import br.ufal.ic.prog2.Model.Bean.User;
import br.ufal.ic.prog2.Model.DAO.ResponseEnums.CreateUserResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class UserStorage extends BaseStorage<User>{

    private final Map<String, String> usernameToUidDatabase;

    protected String ID_PREFIX = "U";

    public UserStorage(){
        this.usernameToUidDatabase = new HashMap<>();
    }

    public boolean isEmpty(){
        return (memoryDatabase.size() <= 0);
    }

    public boolean uidAlreadyExists(String uid){
        return memoryDatabase.containsKey(uid);
    }

    public boolean usernameDoesntExists(String name){
        return !usernameToUidDatabase.containsKey(name);
    }

    public User getUserById(String uid){
        if(memoryDatabase.containsKey(uid)) return memoryDatabase.get(uid);
        return null;
    }

    public User getUserByUsername(String uname){
        if(usernameToUidDatabase.containsKey(uname)) return memoryDatabase.get(usernameToUidDatabase.get(uname));
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

    public CreateUserResponse createUser(User user){
        if(user != null){
            String id = null;
            while(id == null || memoryDatabase.containsKey(id)){
                id = generateId(ID_PREFIX);
            }

            user.setId(id);
            if(user.getUsername() != null && !usernameToUidDatabase.containsKey(user.getUsername())) {
                if(user.getFriends() == null){user.setFriends(new ArrayList<>());}
                if(user.getFriends() == null){user.setFriendInvites(new ArrayList<>());}

                memoryDatabase.put(user.getId(), user);
                usernameToUidDatabase.put(user.getUsername(), user.getId());
                return CreateUserResponse.OK;
            }
            return CreateUserResponse.NAME_ALREADY_EXISTS;
        }
        return CreateUserResponse.INVALID_USER;
    }

    public Map<String, String> getUsernameToUidDatabase() {
        return usernameToUidDatabase;
    }
}
