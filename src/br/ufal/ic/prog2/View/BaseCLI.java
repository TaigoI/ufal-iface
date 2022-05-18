package br.ufal.ic.prog2.View;

import br.ufal.ic.prog2.Factory.ControllerFactory;
import br.ufal.ic.prog2.Model.Bean.User;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import static java.lang.Math.max;
import static java.lang.Math.min;

public abstract class BaseCLI {

    public void clearScreen() {
        System.out.println(System.lineSeparator().repeat(100));
        System.out.flush();
    }

    protected void showHeader(){
        clearScreen();
        User loggedUser = ControllerFactory.getUserController().getLoggedUser();

        if(loggedUser != null){
            System.out.println("iFace <@"+loggedUser.getUsername()+">");
        } else {
            System.out.println("iFace");
        }
    }

    abstract protected void showHeader(String action);



    public void getEnter(){
        getNextSentence("Press Enter to Continue...");
    }



    protected String getNextWord(){
        String s = null;

        while (s == null){
            Scanner scanner = new Scanner(System.in);
            try{
                s = scanner.next();
            } catch (InputMismatchException ime){
                System.out.println("Invalid Input (String without spaces expected)\nTry again:");
            } catch (Exception e){
                System.out.println("Invalid Input\nTry again:");
            } finally {
                scanner.reset();
            }
        }

        return s;
    }

    protected String getNextWord(String description){
        System.out.println();
        System.out.println(description);
        return getNextWord();
    }



    protected String getNextSentence(){
        String s = null;

        while (s == null){
            Scanner scanner = new Scanner(System.in);
            try{
                s = scanner.nextLine();
            } catch (InputMismatchException ime){
                System.out.println("Invalid Input (String expected)\nTry again:");
            } catch (Exception e){
                System.out.println("Invalid Input\nTry again:");
            } finally {
                scanner.reset();
            }
        }

        return s;
    }

    protected String getNextSentence(String description){
        System.out.println();
        System.out.println(description);
        return getNextSentence();
    }



    protected Integer getNextInteger(){
        Integer i = null;
        while(i == null){
            Scanner scanner = new Scanner(System.in);
            try{
                i = scanner.nextInt();
            } catch (InputMismatchException ime){
                System.out.println("Invalid Input (Integer number expected)\nTry again:");
            } catch (Exception e){
                System.out.println("Invalid Input\nTry again:");
            } finally {
                scanner.reset();
            }
        }
        return i;
    }

    protected Integer getNextInteger(String description){
        System.out.println();
        System.out.println(description);
        return getNextInteger();
    }

    protected Integer getNextInteger(String description, int min, int max){
        while(true){
            Integer option = getNextInteger(description);
            if(option <= max && option >= min) return option;
            System.out.println("\nInvalid option... Try Again");
        }
    }

    protected Integer getNextInteger(String description, ArrayList<Integer> selection){
        while(true){
            Integer option = getNextInteger(description);
            if(selection.contains(option)) return option;
            System.out.print("\nInvalid option...\nTry Again choosing from the set ");

            String s = "";
            for (Integer i : selection){
                s = s.concat(s.equals("") ? "" : ", ");
                s = s.concat(i.toString());
            }
            System.out.println("( "+s+" )");
        }
    }



    protected Integer getNextOption(String description, ArrayList<Integer> indices, ArrayList<String> descriptions){
        int option;

        System.out.println();
        for(int i = 0; i < indices.size(); i++){
            try{
                if(indices.get(i) == -1){
                    indices.remove(i--);
                    System.out.println();
                } else {
                    System.out.println("["+indices.get(i)+"] - "+descriptions.get(i));
                }
            } catch (Exception e){
                break;
            }
        }

        indices.removeIf(i -> i < 0);

        option = getNextInteger(description, indices);
        return option;
    }



    public <T> T getFromPagedMenu(String action, ArrayList<T> searchItems, String searchTerm, Integer amountOfItemsPerPage) {
        showHeader(action);
        if(searchTerm != null){
            System.out.println("Term: \""+searchTerm+"\"");
        }

        int pI = 0;
        int pF = min(amountOfItemsPerPage,searchItems.size()-1);
        while(true){
            int j = 1;
            ArrayList<Integer> indices = new ArrayList<>();
            ArrayList<String> descriptions = new ArrayList<>();

            for(int i = pI; i <= pF; i++, j++){
                if(j>amountOfItemsPerPage) break;
                try{
                    indices.add(j); descriptions.add(searchItems.get(i).toString());
                } catch (Exception e){
                    break;
                }
            }

            indices.add(-1);
            indices.add(0); descriptions.add("Return");
            indices.add(amountOfItemsPerPage+1); descriptions.add("Previous Page");
            indices.add(amountOfItemsPerPage+2); descriptions.add("Next Page");

            int option = getNextOption("\nChoose:", indices, descriptions);

            if(option == 0){
                return null;
            } else if (option == amountOfItemsPerPage+1){
                pI = max(0,pI-amountOfItemsPerPage); pF = min(pF-amountOfItemsPerPage,searchItems.size()-1);
            } else if (option == amountOfItemsPerPage+2){
                pI = max(0,pI+amountOfItemsPerPage); pF = min(pF+amountOfItemsPerPage,searchItems.size()-1);
            } else if (option >= 1 && option <= amountOfItemsPerPage){
                return searchItems.get(option-1);
            }
        }
    }

}
