package br.ufal.ic.prog2.View;

import java.util.ArrayList;

public class FeedCLI extends BaseCLI {

    public void showHeader(){
        super.showHeader();
        System.out.println("> Feed");
    }

    @Override
    public void showHeader(String action){
        showHeader();
        System.out.println("+ "+action);
    }

    public String dialogPostTitle(String action) {
        showHeader(action);
        return getNextSentence("Post Title: ");
    }

    public String dialogPostText(String action) {
        showHeader(action);
        return getNextSentence("Post Text: ");
    }
}
