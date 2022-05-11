package br.ufal.ic.prog2.View;

import br.ufal.ic.prog2.Factory.ViewFactory;
import br.ufal.ic.prog2.Model.Bean.Post;

import java.util.ArrayList;

public class PostCLI  extends BaseCLI {

    @Override
    public void showHeader(String action){
        showHeader();
        System.out.println("+ "+action);
    }

    public int dialogPostViewControl (){
        showHeader("New Post Properties");

        ArrayList<Integer> indices = new ArrayList<>();
        ArrayList<String> descriptions = new ArrayList<>();

        indices.add(0); descriptions.add("Public");
        indices.add(1); descriptions.add("Friends Only");
        return getNextOption("\nChoose Privacy Option: ", indices, descriptions);
    }

    public String getPostAsText(Post post){
        if(post == null){
            return "\nThere are no new posts available.";
        }

        return "\n"
                +("*"+ post.getTitle()+"*\n")
                +("@" + post.getOwner().getUsername()+" ("+ (post.getTargetName()) +")\n")
                +(post.getMessage().replaceAll("(.{1,70})\\s+", "$1\n"));
    }

    public void showPostAsText(Post post){
        System.out.println(getPostAsText(post));
    }
}
