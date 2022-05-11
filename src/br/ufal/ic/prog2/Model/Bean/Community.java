package br.ufal.ic.prog2.Model.Bean;

import java.io.Serializable;
import java.util.ArrayList;

public class Community implements Serializable {

    private User owner;
    private ArrayList<User> members;
    private ArrayList<User> requestedMemberships;
    private Feed feed;

    private String id;
    private String name;
    private String description;



    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public ArrayList<User> getMembers() {
        return members;
    }

    public void setMembers(ArrayList<User> members) {
        this.members = members;
    }

    public ArrayList<User> getRequestedMemberships() {
        return requestedMemberships;
    }

    public void setRequestedMemberships(ArrayList<User> requestedMemberships) {
        this.requestedMemberships = requestedMemberships;
    }

    public Feed getFeed() {
        return feed;
    }

    public void setFeed(Feed feed) {
        this.feed = feed;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
