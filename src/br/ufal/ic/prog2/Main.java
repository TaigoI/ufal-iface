package br.ufal.ic.prog2;

import br.ufal.ic.prog2.Bean.User;
import br.ufal.ic.prog2.Factory.ControllerFactory;
import br.ufal.ic.prog2.Controller.UserController;
import br.ufal.ic.prog2.DAO.ResponseEnums.CreateUserResponse;
import br.ufal.ic.prog2.Factory.StorageFactory;
import br.ufal.ic.prog2.DAO.UserStorage;

import java.util.Scanner;

public class Main {

    public static void clearScreen() {
        System.out.println(System.lineSeparator().repeat(100));
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String topMessage = "";
        while(true){
            if(topMessage.equals("FINISH")){return;}
            clearScreen();
            if(!topMessage.equals("")){System.out.println("\n---> "+topMessage+"\n");}

            if(ControllerFactory.getUserController().getLoggedUser() == null){
                topMessage = publicMainPage(scanner);
            } else {
                topMessage = privateMainPage(scanner);
            }
        }
    }

    private static String publicMainPage(Scanner scanner){
        int entrada;
        String topMessage = "";

        System.out.println("Bem vindo ao iFace");

        System.out.println("""
                
                <1> Criar Novo Usuário
                <2> Entrar
                <0> Fechar Aplicação""".indent(4));

        System.out.println("\nEscolha: ");
        entrada = scanner.nextInt();

        switch (entrada) {
            case 0:
                return "FINISH";
            case 1:
                ControllerFactory.getUserController().createUserDialog(); break;
            case 2:
                ControllerFactory.getUserController().loginDialog(); break;
            default:
                topMessage = "A opção escolhida é inválida...";
        }

        return topMessage;
    }

    private static String privateMainPage(Scanner scanner){
        int entrada;
        String topMessage = "";

        System.out.println("iFace <@"+ControllerFactory.getUserController().getLoggedUser().getUsername()+">");

        System.out.println("""

                <1> Feed
                <2> Comunidades
                <3> Chat
                <4> Meu Perfil
                <0> Sair""".indent(4));

        System.out.println("\nEscolha: ");
        entrada = scanner.nextInt();

        switch (entrada) {
            case 0 -> ControllerFactory.getUserController().logoutDialog();
            case 1 -> feedPage(scanner);
            case 2 -> communitiesPage(scanner);
            case 3 -> chatsPage(scanner);
            case 4 -> profilePage(scanner);
            default -> topMessage = "A opção escolhida é inválida...";
        }

        return topMessage;
    }

    private static void feedPage(Scanner scanner){}

    private static void communitiesPage(Scanner scanner){}

    private static void chatsPage(Scanner scanner){}

    private static void profilePage(Scanner scanner){}

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
        UserStorage uStorage = StorageFactory.getUserStorage();

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
