package br.ufal.ic.prog2.View;

import br.ufal.ic.prog2.Factory.StorageFactory;
import br.ufal.ic.prog2.Model.Bean.User;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class UserCLI extends BaseCLI {

    public void showHeader(){
        super.showHeader();
        System.out.println("> Profile");
    }

    @Override
    public void showHeader(String action){
        showHeader();
        System.out.println("+ "+action);
    }

    public boolean dialogDeleteUser(){
        showHeader("Delete All Profile Data");

        ArrayList<Integer> indices = new ArrayList<>();
        ArrayList<String> descriptions = new ArrayList<>();

        indices.add(0); descriptions.add("No, go back!");
        indices.add(1); descriptions.add("Yes, delete it...");

        int option = getNextOption("\nAre you sure? ", indices, descriptions);
        return option != 0;
    }

    public String dialogUpdateDisplayName(String current){
        showHeader("Update Profile [Display Name]");
        System.out.println("Current Value: " + (current.equals("") ? "EMPTY" : current));
        String s = getNextSentence("""
                Type your new Display Name below...
                If you do not wish to change the Display name,
                just press Enter without typing anything else.

                New Name:""");

        if(s.equals("")){return current;}

        return s;
    }

    public String dialogUpdateBirthDate(String current){
        while(true){
            showHeader("Update Profile [Birthdate]");
            System.out.println("Current Value: "+(current.equals("") ? "EMPTY" : current));
            String s = getNextSentence("""
                Type your new Birthdate below, formatted as DD/MM/YYYY...
                If you do not wish to change your Birthdate, just
                press Enter without typing anything else...

                New Date:""");

            if(s.equals("")){return current;}
            if(s.matches("(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/([0-9]{4})")){
                SimpleDateFormat sdf =  new SimpleDateFormat("dd/MM/yyyy");
                try{
                    Date data = sdf.parse(s);
                    return sdf.format(data);
                }  catch (Exception e){
                    invalidDate();
                }
            } else {
                invalidDate();
            }
        }
    }

    protected void invalidDate(){
        System.out.println("The date informed is invalid or improperly formatted... Try again");
        getEnter();
        clearScreen();
    }

    public String dialogUpdateAboutMe(String current){
        showHeader("Update Profile [About Me]");
        System.out.println("Current Value: "+(current.equals("") ? "EMPTY" : current));
        String s = getNextSentence("""
                Type the new About Me below...
                If you do not wish to change your About Me,
                just press Enter without typing anything else...

                About Me:""");

        if(s.equals("")){return current;}

        return s;
    }

    public String dialogUpdatePhone(String current){
        while(true){
            showHeader("Update Profile [Phone Number (BR)]");
            System.out.println("Current Value: "+(current.equals("") ? "EMPTY" : current));
            String s = getNextSentence("""
                    Type the new Brazilian Phone Number below, formatted as (0XX) 9XXXX-XXXX
                    where X is a digit... If you do not wish to change your Brazilian Phone Number,
                    just press Enter without typing anything else...
    
                    Phone:""");

            if(s.equals("")){return current;}
            if(s.matches("\\(?(0?[1-9]{2})\\)?\\s?9\\s?([1-9]{4})(\\-|\\s)?([0-9]{4})")){
                return s;
            } else {
                invalidPhone();
            }
        }
    }

    protected void invalidPhone(){
        System.out.println("The phone informed is improperly formatted... Try again");
        getEnter();
        clearScreen();
    }

    public String dialogNewUsername() {
        showHeader("Create Profile");
        String name = getNextWord("Will be used to login...\nUsername (no spaces): ");
        while(true){
            if(StorageFactory.getUserStorage().usernameDoesntExists(name)) return name;

            name = getNextWord("The chosen name already exists... try again\nUsername (no spaces): ");
        }
    }

    public String dialogNewPassword() {
        showHeader("Create Profile");
        return getNextWord("Password (no spaces): ");
    }

    public String dialogAskUsername(String action) {
        showHeader(action);
        return getNextWord("Username (no spaces): ");
    }

    public String dialogAskPassword(String action) {
        showHeader(action);
        return getNextWord("Password (no spaces): ");
    }

    public String dialogNewDisplayName(){
        showHeader("Create Profile");
        String s = getNextSentence("""
                Type your actual name (Display Name) below...
               
                Name:""");

        return s;
    }

    public boolean dialogConfirmLogout(){
        showHeader("Logout");

        ArrayList<Integer> indices = new ArrayList<>();
        ArrayList<String> descriptions = new ArrayList<>();

        indices.add(0); descriptions.add("No, go back!");
        indices.add(1); descriptions.add("Yes, bye...");

        int option = getNextOption("\nAre you sure? ", indices, descriptions);
        return option != 0;
    }

    public boolean dialogContinueLogin(){
        showHeader("Login (Incorrect)");

        ArrayList<Integer> indices = new ArrayList<>();
        ArrayList<String> descriptions = new ArrayList<>();

        indices.add(1); descriptions.add("Try Again");
        indices.add(0); descriptions.add("Return to Main Page");
        return getNextOption("\nChoose: ", indices, descriptions) != 0;
    }

    public String displayUser(User user){
        return user.getId()+" {\n"
                +("Username: @"+(user.getUsername())).indent(4)
                +("DisplayName: "+(user.getDisplayName().equals("") ? "EMPTY" : user.getDisplayName())).indent(4)
                +("BirthDate:   "+(user.getBirthDate().equals("") ? "EMPTY" : user.getBirthDate())).indent(4)
                +("BirthPlace:  "+(user.getAboutMe().equals("") ? "EMPTY" : user.getAboutMe())).indent(4)
                +("CurrentCity: "+(user.getCurrentCity().equals("") ? "EMPTY" : user.getCurrentCity())).indent(4)
                +"}";
    }

    public String displayCompactUser(User user){
        return "@" + user.getUsername() + " ("+user.getId()+")";
    }

    public void showUser(User user){
        System.out.println(displayUser(user));
    }

    public void failedToFind(String username){
        System.out.println("Error: Failed to find username "+username);
        getEnter();
    }
}
