package br.ufal.ic.prog2.View;

import br.ufal.ic.prog2.Factory.ControllerFactory;

import java.util.ArrayList;

public class BaseCLI {

    protected static void clearScreen() {
        System.out.println(System.lineSeparator().repeat(100));
        System.out.flush();
    }

    protected void showHeader(){
        System.out.println("iFace");
    }

    protected String getNextWord(){

        return null;
    }

    protected String getNextSentence(){

        return null;
    }

    protected Integer getNextOption(String description, ArrayList<String> options){

        return null;
    }

}
