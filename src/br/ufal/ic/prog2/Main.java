package br.ufal.ic.prog2;

import br.ufal.ic.prog2.Bean.User;
import br.ufal.ic.prog2.Controller.UserController;
import br.ufal.ic.prog2.DAO.ResponseEnums.CreateUserResponse;
import br.ufal.ic.prog2.DAO.StorageFactory;
import br.ufal.ic.prog2.DAO.UserStorage;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UserController controller = new UserController(); //TODO: Jogar num factory com flyweight
        boolean flag = true;
        int entrada = 0;

        while(flag){
            System.out.println("iFace\n");

            System.out.println("Opções:"
                    +"\n    0 - Fechar Aplicação"
                    +"\n    1 - Criar Novo Usuário"
                    +"\n    2 - Login"
                    +"\n    3 - Logout"
                    +"\n    4 - Atualizar Próprio Perfil"
                    +"\n    5 - Apagar Próprio Usuário"
                    +"\n    6 - Ver Próprio Perfil");

            System.out.println("\nEscolha: ");
            entrada = scanner.nextInt();
            if(entrada == 0){
                flag = false;
            } else if (entrada == 1){
                controller.createUserDialog();
            } else if (entrada == 2){
                controller.loginDialog();
            } else if (entrada == 3){
                controller.logoutDialog();
            } else if (entrada == 4){
                controller.updateUserDialog();
            } else if (entrada == 5){
                controller.deleteUserDialog();
            } else if (entrada == 6){
                System.out.println(controller.displayUser());
            } else {
                System.out.println("Opção inválida...");
            }
        }


    }

    public static void testUserController() {
        UserController controller = new UserController(); //TODO: Jogar num factory com flyweight
        controller.createUserDialog();
        controller.loginDialog();
        controller.updateUserDialog();
        System.out.println(controller.displayUser());
        controller.logoutDialog();
        controller.createUserDialog();
        controller.loginDialog();
        System.out.println(controller.displayUser());
        controller.deleteUserDialog();
        controller.loginDialog();
    }

    public static void testUserStorage(){
        UserStorage uStorage = StorageFactory.getUserStorageObject();

        User user1 = new User();
        user1.setDisplayName("taigopedrosa");
        user1.setUid("uid1");

        User user2 = new User();
        user2.setDisplayName("taigopedrosa");
        user2.setUid("uid2");

        CreateUserResponse response1 = uStorage.storeUser(user1);
        CreateUserResponse response2 = uStorage.storeUser(user1);
        CreateUserResponse response3 = uStorage.storeUser(user2);
        user2.setDisplayName("emilybrito");
        CreateUserResponse response4 = uStorage.storeUser(user2);

        System.out.println(response1);
        System.out.println(response2);
        System.out.println(response3);
        System.out.println(response4);
    }
}
