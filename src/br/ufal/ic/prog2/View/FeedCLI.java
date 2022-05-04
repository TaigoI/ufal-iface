package br.ufal.ic.prog2.View;

public class FeedCLI extends BaseCLI {

    public void showHeader(){
        super.showHeader();
        System.out.println(" > Communities");
    }

    public void showHeader(String action){
        showHeader();
        System.out.println(" + "+action);
    }

    public String dialogPostTitle(String action) {
        showHeader(action);
        return getNextSentence("Título: ");
    }

    public String dialogPostText(String action) {
        showHeader(action);
        return getNextSentence("Texto: ");
    }
}
