package br.ufal.ic.prog2.DAO;

import br.ufal.ic.prog2.Bean.Post;
import br.ufal.ic.prog2.Bean.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CommunityFeedStorage {

    private final Map<String, Integer> lastSeen;
    private final ArrayList<Post> publicFeed;

    public CommunityFeedStorage() {
        this.lastSeen = new HashMap<>();
        this.publicFeed = new ArrayList<>();
    }

    public Post storePost(Post newPost){
        newPost.setPublic(true);
        publicFeed.add(newPost);
        return newPost;
    }

    public Post findNextPost(User loggedUser){
        String uid = loggedUser.getUid();

        if(!lastSeen.containsKey(uid)){
            lastSeen.put(uid, -1);
        }
        Integer last = lastSeen.get(uid);

        while(publicFeed.size() > lastSeen.get(uid) + 1) {

            lastSeen.put(uid, lastSeen.get(uid) + 1);

            if (!publicFeed.get(lastSeen.get(uid)).getOwner().getUid().equals(uid)) {
                return publicFeed.get(lastSeen.get(uid));
            }
        }

        return null;
    }
}
