package br.ufal.ic.prog2.DAO;

import br.ufal.ic.prog2.Bean.Post;
import br.ufal.ic.prog2.Bean.User;
import br.ufal.ic.prog2.Factory.ControllerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class FeedStorage {

    private final Map<String, Map<String, Integer>> lastSeen;
    private final ArrayList<Post> publicFeed;
    private final Map<String, ArrayList<Post>> privateFeeds;

    public FeedStorage() {
        this.lastSeen = new HashMap<>();
        this.publicFeed = new ArrayList<>();
        this.privateFeeds = new HashMap<>();
    }

    public Post storePost(Post newPost, boolean isPublic){
        if(isPublic){
            newPost.setTarget("público");
            publicFeed.add(newPost);
        } else {
            newPost.setTarget("amigos");
            if(!privateFeeds.containsKey(newPost.getOwner().getUid())){
                privateFeeds.put(newPost.getOwner().getUid(), new ArrayList<>());
            }
            privateFeeds.get(newPost.getOwner().getUid()).add(newPost);
        }

        return newPost;
    }

    public Post findNextPost(User loggedUser){
        if(!lastSeen.containsKey(loggedUser.getUid())){
            lastSeen.put(loggedUser.getUid(), new HashMap<>());
        }
        Map <String, Integer> lastMap = lastSeen.get(loggedUser.getUid());

        ArrayList<User> friends = (ArrayList<User>) loggedUser.getFriends().clone();
        //friends.add(loggedUser);
        Collections.shuffle(friends);

        for(User friend : friends){
            if(!lastMap.containsKey(friend.getUid())){
                lastMap.put(friend.getUid(), -1);
            }

            Integer last = lastMap.get(friend.getUid());

            if(privateFeeds.containsKey(friend.getUid()) && privateFeeds.get(friend.getUid()).size() > last+1){
                lastMap.put(friend.getUid(), last+1);
                return privateFeeds.get(friend.getUid()).get(last+1);
            }
        }

        //wasn't able to find new post in friendslist
        if(!lastMap.containsKey("public")){
            lastMap.put("public", -1);
        }

        while(publicFeed.size() > lastMap.get("public") + 1) {

            lastMap.put("public", lastMap.get("public") + 1);

            if (!publicFeed.get(lastMap.get("public")).getOwner().getUid().equals(loggedUser.getUid())) {
                return publicFeed.get(lastMap.get("public"));
            }
        }

        return null;
    }
}
