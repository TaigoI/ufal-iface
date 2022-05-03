package br.ufal.ic.prog2.Model.Bean;

import java.io.Serializable;
import java.util.ArrayList;

public class Community implements Serializable {

    User owner;
    ArrayList<User> members;

    String cid;
    String name;
    String description;

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

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
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
