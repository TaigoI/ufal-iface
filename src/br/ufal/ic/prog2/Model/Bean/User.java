package br.ufal.ic.prog2.Model.Bean;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {

    private String id = null;
    private String username = null;
    private String displayName = "";
    private String aboutMe = "";
    private String birthDate = "";
    private String phone = "";

    private String password = null;

    private ArrayList<User> friends;
    private ArrayList<User> friendInvites;

    private ArrayList<Community> communities;

    private Feed feed;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public ArrayList<Community> getCommunities() {
        return communities;
    }

    public void setCommunities(ArrayList<Community> communities) {
        this.communities = communities;
    }

    public Feed getFeed() {
        return feed;
    }

    public void setFeed(Feed feed) {
        this.feed = feed;
    }
}
