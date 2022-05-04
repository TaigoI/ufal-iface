package br.ufal.ic.prog2.Controller;

import br.ufal.ic.prog2.Factory.ViewFactory;
import br.ufal.ic.prog2.Model.Bean.Community;
import br.ufal.ic.prog2.Model.Bean.Feed;
import br.ufal.ic.prog2.Model.Bean.Post;
import br.ufal.ic.prog2.Model.Bean.User;
import br.ufal.ic.prog2.Factory.StorageFactory;
import br.ufal.ic.prog2.Model.DAO.FeedStorage;
import br.ufal.ic.prog2.View.FeedCLI;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class FeedController {

    public static Feed initializeNewFeed(){
        Feed f = new Feed();
        f.setPosts(new ArrayList<>());
        f.setLastSeen(new HashMap<>());
        f.setHistory(new HashMap<>());
        return f;
    }

    private static Feed initializeUserInHistory(Feed feed, User u){
        if(!feed.getHistory().containsKey(u.getId())){
            feed.getHistory().put(u.getId(), new ArrayList<>());
            feed.getLastSeen().put(u.getId(), -1);
        }

        return feed;
    }

    private FeedCLI CLI;
    private FeedStorage STORAGE;

    FeedController(){
        this.CLI =  ViewFactory.getFeedCLI();
        this.STORAGE = StorageFactory.getFeedStorage();
    }

    public Post createPost(Feed f, User u){
        String action = "Novo Post";

        Post newPost = new Post();
        newPost.setOwner(u);
        String title = CLI.dialogPostTitle(action);
        String text = CLI.dialogPostText(action);

        newPost.setTitle(title);
        newPost.setMessage(text);

        Map<String, ArrayList<Post>> history = f.getHistory();
        Map<String, Integer> lastSeen = f.getLastSeen();
        initializeUserInHistory(f, u);

        f.getPosts().add(newPost);
        history.get(u.getId()).add(newPost);
        lastSeen.put(u.getId(), history.get(u.getId()).size()-1);

        return newPost;
    }

    public Post createPost(String feedId, String userId) {
        Feed f =  STORAGE.getFeedById(feedId);
        User u =  StorageFactory.getUserStorage().getUserById(userId);
        return createPost(f, u);
    }

    public Post friendsOrPublicNextPost(User u){
        ArrayList<Feed> feeds = new ArrayList<>();
        feeds.add(u.getFeed());

        for (User friend : u.getFriends()){
            feeds.add(friend.getFeed());
        }

        Post post = nextPostFromFeeds(feeds, u);
        if(post == null){
            post = nextPostFromPublic(u);
        }

        //ainda pode ser null, mas isso significaria que não há mais posts para ver...
        return post;
    }

    public Post nextPostFromFeeds(ArrayList<Feed> feeds, User user){
        Collections.shuffle(feeds);
        for (Feed feed : feeds){
            Integer last = feed.getLastSeen().get(user.getId());
            if(feed.getPosts().size() > (last+1)){
                feed.getLastSeen().put(user.getId(), last+1);
                return feed.getPosts().get(last+1);
            }
        }

        return null;
    }

    public Post nextPostFromPublic(User user){
        Feed feed = STORAGE.getFeedById("public");
        Integer last = feed.getLastSeen().get(user.getId());
        if(feed.getPosts().size() > (last+1)){
            feed.getLastSeen().put(user.getId(), last+1);
            return feed.getPosts().get(last+1);
        }

        return null;
    }

    public Post previousPost(Feed f, User u) {
        Map<String, ArrayList<Post>> history = f.getHistory();
        Map<String, Integer> lastSeen = f.getLastSeen();
        initializeUserInHistory(f, u);

        if(lastSeen.get(u.getId()).equals(0)){
            lastSeen.put(u.getId(), -1);
            return history.get(u.getId()).get(0);
        }
        else if(lastSeen.get(u.getId())-1 <= 0){
            lastSeen.put(u.getId(), -1);
            return null;
        } else {
            lastSeen.put(u.getId(), lastSeen.get(u.getId())-1);
            return history.get(u.getId()).get(lastSeen.get(u.getId()));
        }
    }

    public Post previousPost(String feedId, String userId) {
        Feed f =  STORAGE.getFeedById(feedId);
        User u =  StorageFactory.getUserStorage().getUserById(userId);
        return previousPost(f, u);
    }

    public Post lastPost(Feed f, User u) {
        Map<String, ArrayList<Post>> history = f.getHistory();
        Map<String, Integer> lastSeen = f.getLastSeen();
        initializeUserInHistory(f, u);

        lastSeen.put(u.getId(), history.get(u.getId()).size()-1);
        return history.get(u.getId()).get(lastSeen.get(u.getId()));
    }

    public Post lastPost(String feedId, String userId){
        Feed f =  STORAGE.getFeedById(feedId);
        User u =  StorageFactory.getUserStorage().getUserById(userId);
        return lastPost(f, u);
    }
}
