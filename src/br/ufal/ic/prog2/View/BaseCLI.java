package br.ufal.ic.prog2.View;

import br.ufal.ic.prog2.Factory.ControllerFactory;
import br.ufal.ic.prog2.Model.Bean.User;

import java.util.ArrayList;
import java.util.Scanner;

public class BaseCLI {

    protected static void clearScreen() {
        System.out.println(System.lineSeparator().repeat(100));
        System.out.flush();
    }

    protected void showHeader(){
        clearScreen();
        User loggedUser = ControllerFactory.getUserController().getLoggedUser();

        if(loggedUser != null){
            System.out.println("iFace <@"+loggedUser.getUsername()+">");
        } else {
            System.out.print("iFace");
        }
    }

    protected String getNextWord(){
        Scanner scanner = new Scanner(System.in);
        return scanner.next();
    }

    protected String getNextWord(String description){
        System.out.println(description);
        return getNextWord();
    }

    protected String getNextSentence(){
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    protected String getNextSentence(String description){
        System.out.println(description);
        return getNextSentence();
    }

    protected Integer getNextInteger(){
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }

    protected Integer getNextInteger(String description){
        System.out.println(description);
        return getNextInteger();
    }

    protected Integer getNextInteger(String description, int min, int max){
        while(true){
            Integer option = getNextInteger(description);
            if(option <= max && option >= min) return option;
            System.out.println("\nValor Inválido... Tente novamente abaixo");
        }
    }

    protected Integer getNextInteger(String description, ArrayList<Integer> selection){
        while(true){
            Integer option = getNextInteger(description);
            if(selection.contains(option)) return option;
            System.out.println("\nValor Inválido... Tente novamente abaixo");
        }
    }

    protected Integer getNextOption(String description, ArrayList<Integer> indices, ArrayList<String> descriptions){
        int option;

        int k_spaces = 0;
        for(int i = 0; i < indices.size(); i++){
            if(indices.get(i) == -1){
                indices.remove(i--);
                System.out.println();
            } else {
                System.out.println("["+indices.get(i)+"] - "+descriptions.get(i));
            }
        }

        option = getNextInteger("\nEscolha a opção:", indices);
        return option;
    }

}
