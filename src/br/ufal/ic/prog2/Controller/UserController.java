package br.ufal.ic.prog2.Controller;

import br.ufal.ic.prog2.Factory.ViewFactory;
import br.ufal.ic.prog2.Model.Bean.Community;
import br.ufal.ic.prog2.Model.Bean.User;
import br.ufal.ic.prog2.Factory.StorageFactory;
import br.ufal.ic.prog2.Model.DAO.UserStorage;
import br.ufal.ic.prog2.View.UserCLI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class UserController {

    private User loggedUser;

    private UserCLI CLI;

    public UserController(){
        CLI = ViewFactory.getUserCLI();
    }

    public boolean deleteUser(){
        if(CLI.dialogDeleteUser()){
            StorageFactory.getUserStorage().getMemoryDatabase().remove(loggedUser.getId());
            StorageFactory.getUserStorage().getUsernameToUidDatabase().remove(loggedUser.getUsername());

            for(Community community : loggedUser.getCommunities()){
                community.getMembers().remove(loggedUser);

                if(community.getOwner().getId().equals(loggedUser.getId())){
                    //since user has been removed, if it has > 0 members, can get index 0.
                    //otherwise, delete community...
                    if(community.getMembers().size() > 0){
                        community.setOwner(community.getMembers().get(0));
                    } else{
                        StorageFactory.getCommunityStorage().getNameToCidDatabase().remove(community.getName());
                        StorageFactory.getCommunityStorage().getMemoryDatabase().remove(community.getId());
                    }
                }
            }

            StorageFactory.getFeedStorage().getMemoryDatabase().remove(loggedUser.getFeed().getId());

            for(User friend : loggedUser.getFriends()){
                friend.getFriends().remove(loggedUser);
            }

            loggedUser.getFeed().setPosts(new ArrayList<>());
            loggedUser.getFeed().setHistory(new HashMap<>());
            loggedUser.getFeed().setLastSeen(new HashMap<>());

            loggedUser.setUsername("USER_DELETED");
            loggedUser.setDisplayName("");
            loggedUser.setBirthDate("");
            loggedUser.setAboutMe("");
            loggedUser.setCurrentCity("");
            loggedUser.setPassword("");
            loggedUser.setId("");

            loggedUser = null;
            return true;
        }

        return false;
    }

    public User updateUser(){
        UserStorage userStorage = StorageFactory.getUserStorage();
        System.out.println("iFace > Edição de Perfil\n");

        loggedUser.setDisplayName(CLI.dialogUpdateDisplayName(loggedUser.getDisplayName()));
        loggedUser.setBirthDate(CLI.dialogUpdateBirthDate(loggedUser.getBirthDate()));
        loggedUser.setAboutMe(CLI.dialogUpdateAboutMe(loggedUser.getAboutMe()));
        loggedUser.setCurrentCity(CLI.dialogUpdatePhone(loggedUser.getCurrentCity()));

        StorageFactory.getUserStorage().getMemoryDatabase().put(loggedUser.getId(), loggedUser);
        return loggedUser;
    }

    public User createUser(){
        Scanner scanner = new Scanner(System.in);

        String username = CLI.dialogNewUsername();
        String displayName = CLI.dialogNewDisplayName();
        String password = CLI.dialogNewPassword();

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setDisplayName(displayName);
        user.setFeed(StorageFactory.getFeedStorage().createFeed("Friends"));
        user.setFriends(new ArrayList<>());
        user.setFriendInvites(new ArrayList<>());
        user.setCommunities(new ArrayList<>());

        StorageFactory.getUserStorage().createUser(user);
        return user;
    }

    public User login(){
        if(StorageFactory.getUserStorage().isEmpty()){
            User newUser = createUser();
            this.loggedUser = newUser;
            return newUser;
        }

        User user = StorageFactory.getUserStorage().attemptLogin(
                CLI.dialogAskUsername("Login"),
                CLI.dialogAskPassword("Login"));

        while(user == null){
            System.out.println("\nIncorrect username or password...");
            if(!CLI.dialogContinueLogin()) break;

            user = StorageFactory.getUserStorage().attemptLogin(
                    CLI.dialogAskUsername("Login"),
                    CLI.dialogAskPassword("Login"));
        }

        this.loggedUser = user;
        return user;
    }

    public boolean logout(){
        boolean confirmation = CLI.dialogConfirmLogout();

        if(confirmation){
            this.loggedUser = null;
        }

        return confirmation;
    }

    public void showLoggedUser(){
        if(loggedUser == null) System.out.println("Não há usuário logado");
        else System.out.println(CLI.displayUser(loggedUser));
        CLI.getEnter();
    }

    public User getLoggedUser() {
        return loggedUser;
    }
}
