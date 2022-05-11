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

    public static Feed initializeNewFeed(String target){
        Feed f = new Feed();
        f.setPosts(new ArrayList<>());
        f.setLastSeen(new HashMap<>());
        f.setHistory(new HashMap<>());
        f.setTargetAudience(target);
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

    public FeedController(){
        this.CLI =  ViewFactory.getFeedCLI();
        this.STORAGE = StorageFactory.getFeedStorage();
    }

    public Post createPublicPost(User u){
        String action = "New Post";

        Feed f = StorageFactory.getFeedStorage().getFeedById("public");

        Post newPost = new Post();
        newPost.setOwner(u);
        String title = CLI.dialogPostTitle(action);
        String text = CLI.dialogPostText(action);

        newPost.setTitle(title);
        newPost.setMessage(text);
        newPost.setTargetName(f.getTargetAudience());

        Map<String, ArrayList<Post>> history = u.getFeed().getHistory();
        Map<String, Integer> lastSeen = u.getFeed().getLastSeen();
        initializeUserInHistory(u.getFeed(), u);

        f.getPosts().add(newPost);
        history.get(u.getId()).add(newPost);
        lastSeen.put(u.getId(), history.get(u.getId()).size()-1);

        return newPost;
    }


    public Post createPost(Feed f, User u){
        String action = "New Post";

        Post newPost = new Post();
        newPost.setOwner(u);
        String title = CLI.dialogPostTitle(action);
        String text = CLI.dialogPostText(action);

        newPost.setTitle(title);
        newPost.setMessage(text);
        newPost.setTargetName(f.getTargetAudience());

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

    public Post getFriendsOrPublicNextPost(User u){
        initializeUserInHistory(u.getFeed(), u);

        Integer lastSeen = u.getFeed().getLastSeen().get(u.getId());
        if(lastSeen != -1 && (lastSeen+1) < u.getFeed().getHistory().get(u.getId()).size()){
            u.getFeed().getLastSeen().put(u.getId(), lastSeen+1);
            return u.getFeed().getHistory().get(u.getId()).get(lastSeen+1);
        }

        ArrayList<Feed> feeds = new ArrayList<>();
        for (User friend : u.getFriends()){
            feeds.add(friend.getFeed());
        }

        Post post = nextPostFromFeeds(feeds, u);
        if(post == null){
            post = nextPostFromPublic(u);
        }

        u.getFeed().getLastSeen().put(u.getId(), u.getFeed().getHistory().get(u.getId()).size());
        if(post == null){
            return null;
        }

        //encontramos um novo post, seja em público ou em amigos
        //jogar no history, que, por convenção, é um dicionário UserId: ArrayList<Post>
        //Assim mantém a compatibidade entre o feed principal e os feeds de comunidades
        u.getFeed().getHistory().get(u.getId()).add(post);
        //u.getFeed().getLastSeen().put(u.getId(), u.getFeed().getHistory().get(u.getId()).size());
        return post;
    }

    public Post nextPostFromFeeds(ArrayList<Feed> feeds, User user){
        Collections.shuffle(feeds);
        for (Feed feed : feeds){
            if(!feed.getLastSeen().containsKey(user.getId()) || !feed.getHistory().containsKey(user.getId())){
                initializeUserInHistory(feed, user);
            }

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
        initializeUserInHistory(feed, user);

        Integer last = feed.getLastSeen().get(user.getId());
        while (feed.getPosts().size() > (last+1)){
            feed.getLastSeen().put(user.getId(), last+1);
            Post p = feed.getPosts().get(last+1);

            if(!p.getOwner().equals(user)){ return p; }
            last++;
        }

        return null;
    }

    public Post previousPost(Feed f, User u) {
        Map<String, ArrayList<Post>> history = f.getHistory();
        Map<String, Integer> lastSeen = f.getLastSeen();
        initializeUserInHistory(f, u);

        if(lastSeen.get(u.getId())-1 <= 0){
            lastSeen.put(u.getId(), 0);
            return history.get(u.getId()).size() > 0 ? history.get(u.getId()).get(0) : null;
        } else {
            lastSeen.put(u.getId(), lastSeen.get(u.getId())-1);
            return history.get(u.getId()).get(lastSeen.get(u.getId()));
        }
    }

    public Post previousPost(User u) {
        return previousPost(u.getFeed(), u);
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

    public Post lastPost(User u) {
        return lastPost(u.getFeed(), u);
    }

    public Post lastPost(String feedId, String userId){
        Feed f =  STORAGE.getFeedById(feedId);
        User u =  StorageFactory.getUserStorage().getUserById(userId);
        return lastPost(f, u);
    }
}
