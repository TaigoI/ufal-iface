package br.ufal.ic.prog2.Bean;

import java.io.Serializable;

public class User implements Serializable {

    private String uid = null;
    private String username = null;
    private String displayName = null;
    private String password = null;

    private User[] friends;
    private User[] friendInvites;

    //TODO: Próxima aula de desenvolvimento adicionar funcionalidades
    /* private String salt;
    *  private Chat[] chats;
    *  private Post[] newsFeed;
    *  private Post[] myPosts;
    * */

    public User(){}

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

    public User[] getFriends() {
        return friends;
    }

    public void setFriends(User[] friends) {
        this.friends = friends;
    }

    public User[] getFriendInvites() {
        return friendInvites;
    }

    public void setFriendInvites(User[] friendInvites) {
        this.friendInvites = friendInvites;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
