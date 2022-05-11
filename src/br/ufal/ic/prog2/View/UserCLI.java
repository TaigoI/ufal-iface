package br.ufal.ic.prog2.View;

import br.ufal.ic.prog2.Factory.StorageFactory;
import br.ufal.ic.prog2.Model.Bean.User;

import java.util.ArrayList;

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
        showHeader("Update Profile [Birthdate]");
        System.out.println("Current Value: "+(current.equals("") ? "EMPTY" : current));
        String s = getNextSentence("""
                Type your new Birthdate below...
                If you do not wish to change your Birthdate,
                just press Enter without typing anything else...

                New Date:""");

        if(s.equals("")){return current;}

        return s;
    }

    public String dialogUpdateBirthCity(String current){
        showHeader("Update Profile [Birthplace]");
        System.out.println("Current Value: "+(current.equals("") ? "EMPTY" : current));
        String s = getNextSentence("""
                Type the new Name below...
                If you do not wish to change your Birthplace,
                just press Enter without typing anything else...

                New City:""");

        if(s.equals("")){return current;}

        return s;
    }

    public String dialogUpdateCurrentCity(String current){
        showHeader("Update Profile [City of Residency]");
        System.out.println("Current Value: "+(current.equals("") ? "EMPTY" : current));
        String s = getNextSentence("""
                Type the new Name below...
                If you do not wish to change your City of Residency,
                just press Enter without typing anything else...

                New City:""");

        if(s.equals("")){return current;}

        return s;
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

    public boolean dialogConfirmLogout(){
        showHeader("Logout");

        ArrayList<Integer> indices = new ArrayList<>();
        ArrayList<String> descriptions = new ArrayList<>();

        indices.add(0); descriptions.add("No, go back!");
        indices.add(1); descriptions.add("Yes, bye...");

        int option = getNextOption("\nAre you sure? ", indices, descriptions);
        return option != 0;
    }

    public String displayUser(User user){
        return user.getId()+" {\n"
                +("Username: @"+(user.getUsername())).indent(4)
                +("DisplayName: "+(user.getDisplayName().equals("") ? "EMPTY" : user.getDisplayName())).indent(4)
                +("BirthDate:   "+(user.getBirthDate().equals("") ? "EMPTY" : user.getBirthDate())).indent(4)
                +("BirthPlace:  "+(user.getBirthPlace().equals("") ? "EMPTY" : user.getBirthPlace())).indent(4)
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
