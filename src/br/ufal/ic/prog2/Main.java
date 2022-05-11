package br.ufal.ic.prog2;

import br.ufal.ic.prog2.Factory.ViewFactory;
import br.ufal.ic.prog2.Model.Bean.Post;
import br.ufal.ic.prog2.Model.Bean.User;
import br.ufal.ic.prog2.Factory.ControllerFactory;
import br.ufal.ic.prog2.Controller.UserController;
import br.ufal.ic.prog2.Model.DAO.ResponseEnums.CreateUserResponse;
import br.ufal.ic.prog2.Factory.StorageFactory;
import br.ufal.ic.prog2.Model.DAO.UserStorage;

import java.util.Scanner;

public class Main {

    public static void clearScreen() {
        System.out.println(System.lineSeparator().repeat(100));
        System.out.flush();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while(true){
            if(ControllerFactory.getUserController().getLoggedUser() == null){
                if (ControllerFactory.getPagesController().publicMainPage()) return;
            } else {
                ControllerFactory.getPagesController().privateMainPage();
            }
        }
    }



    public static void testDisplayPost(){
        Post post = new Post();
        User me = new User();
        me.setUsername("taigopedrosa");
        post.setOwner(me);
        post.setTitle("This is how you Lorem Ipsum");
        post.setMessage("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Morbi iaculis vitae lectus vitae aliquet. Nam at bibendum tortor. Quisque egestas lorem id mauris vestibulum vehicula. Nullam suscipit volutpat tempor. Curabitur vitae metus elit. Nulla aliquam volutpat dapibus. Aenean non pulvinar nisi. Aliquam erat volutpat.");

        ViewFactory.getPostCLI().showPostAsText(post);
    }

    public static void testUserController() {
        UserController controller = new UserController(); //TODO: Jogar num factory com flyweight
        controller.createUser();
        controller.login();
        controller.updateUser();
        controller.showLoggedUser();
        controller.logout();
        controller.createUser();
        controller.login();
        controller.showLoggedUser();
        controller.deleteUser();
        controller.login();
    }

    public static void testUserStorage(){
        UserStorage uStorage = StorageFactory.getUserStorage();

        User user1 = new User();
        user1.setDisplayName("taigopedrosa");
        user1.setId("uid1");

        User user2 = new User();
        user2.setDisplayName("taigopedrosa");
        user2.setId("uid2");

        CreateUserResponse response1 = uStorage.createUser(user1);
        CreateUserResponse response2 = uStorage.createUser(user1);
        CreateUserResponse response3 = uStorage.createUser(user2);
        user2.setDisplayName("emilybrito");
        CreateUserResponse response4 = uStorage.createUser(user2);

        System.out.println(response1);
        System.out.println(response2);
        System.out.println(response3);
        System.out.println(response4);
    }
}
