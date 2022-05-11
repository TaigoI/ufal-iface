package br.ufal.ic.prog2.View;

public class ChatCLI extends BaseCLI {

    @Override
    public void showHeader(String action){
        showHeader();
        System.out.println("> Chat");
        System.out.println("+ "+action);
    }

}
