package br.ufal.ic.prog2.Model.DAO;

import br.ufal.ic.prog2.Controller.FeedController;
import br.ufal.ic.prog2.Factory.ControllerFactory;
import br.ufal.ic.prog2.Model.Bean.Community;
import br.ufal.ic.prog2.Model.Bean.Feed;
import br.ufal.ic.prog2.Model.Bean.Post;
import br.ufal.ic.prog2.Model.Bean.User;
import br.ufal.ic.prog2.Model.DAO.ResponseEnums.CreateCommunityResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class FeedStorage extends BaseStorage<Feed> {

    protected String ID_PREFIX = "F";

    public FeedStorage(){
        memoryDatabase.put("public", FeedController.initializeNewFeed("Public"));
    }

    public Feed getFeedById(String id){
        if(memoryDatabase.containsKey(id)){
            return  memoryDatabase.get(id);
        }

        return null;
    }

    public Feed createFeed(String target){
        String id = null;
        while(id == null || memoryDatabase.containsKey(id)){
            id = generateId(ID_PREFIX);
        }

        Feed f = FeedController.initializeNewFeed(target);
        f.setId(id);
        return f;
    }

    public Post storePublicPost(Post newPost){
        newPost.setTargetName("Public");
        memoryDatabase.get("public");
        return newPost;
    }

    public Post storePost(Feed target, String targetName, Post newPost){
        newPost.setTargetName(targetName);
        target.getPosts().add(newPost);
        return newPost;
    }
}
