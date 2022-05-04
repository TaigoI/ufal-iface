package br.ufal.ic.prog2.Model.Bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

public class Feed implements Serializable {

    private String id;
    private ArrayList<Post> Posts;
    private Map<String, Integer> LastSeen;
    private Map<String, ArrayList<Post>> History;



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<Post> getPosts() {
        return Posts;
    }

    public void setPosts(ArrayList<Post> posts) {
        Posts = posts;
    }

    public Map<String, Integer> getLastSeen() {
        return LastSeen;
    }

    public void setLastSeen(Map<String, Integer> lastSeen) {
        LastSeen = lastSeen;
    }

    public Map<String, ArrayList<Post>> getHistory() {
        return History;
    }

    public void setHistory(Map<String, ArrayList<Post>> history) {
        History = history;
    }
}
