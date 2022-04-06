package br.ufal.ic.prog2.Bean;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {

    private String uid = null;
    private String username = null;
    private String displayName = null;
    private String password = null;

    private ArrayList<User> friends;
    private ArrayList<User> friendInvites;

    //TODO: Próxima aula de desenvolvimento adicionar funcionalidades
    /* private String salt;
    *  private Chat[] chats;
    * */

    public User(){}

    public User protectPersonalData(){
        this.displayName = "";
        this.password = null;
        this.friendInvites = null;

        return this;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<User> getFriends() {
        return friends;
    }

    public void setFriends(ArrayList<User> friends) {
        this.friends = friends;
    }

    public ArrayList<User> getFriendInvites() {
        return friendInvites;
    }

    public void setFriendInvites(ArrayList<User> friendInvites) {
        this.friendInvites = friendInvites;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
