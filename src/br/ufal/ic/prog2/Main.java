package br.ufal.ic.prog2;

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
        String os = System.getProperty("os.name");
        System.out.println("---> "+os);

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
        System.out.print("\nEscolha: ");
        entrada = scanner.nextInt();

        switch (entrada) {
            case 0 -> topMessage = "FINISH";
            case 1 -> ControllerFactory.getUserController().createUserDialog();
            case 2 -> ControllerFactory.getUserController().loginDialog();
            default -> topMessage = "A opção escolhida é inválida...";
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
        System.out.print("Escolha: ");
        entrada = scanner.nextInt();

        switch (entrada) {
            case 0 -> ControllerFactory.getUserController().logoutDialog();
            case 1 -> feedPage(scanner);
            case 2 -> communitiesExplorerPage(scanner);
            case 3 -> chatsPage(scanner);
            case 4 -> profilePage(scanner);
            default -> topMessage = "A opção escolhida é inválida...";
        }

        return topMessage;
    }

    private static void feedPage(Scanner scanner){
        int entrada;
        String topMessage = "";
        Post post = ControllerFactory.getFeedController().nextPost(ControllerFactory.getUserController().getLoggedUser().getUid());

        boolean flag = true;

        while(flag){
            clearScreen();
            if(!topMessage.equals("")){System.out.println("\n---> "+topMessage+"\n"); topMessage="";}

            System.out.println("iFace [Feed] <@"+ControllerFactory.getUserController().getLoggedUser().getUsername()+">\n");
            System.out.println(ControllerFactory.getFeedController().displayPost(post));
            System.out.println("<1> Novo Post     <2> Ver Próximo     <3> Ver Anterior     <0> Voltar");
            System.out.print("Escolha: ");
            entrada = scanner.nextInt();



            switch (entrada) {
                case 0 -> flag = false;
                case 1 -> {
                    ControllerFactory.getFeedController().createPost(ControllerFactory.getUserController().getLoggedUser().getUid());
                    post = ControllerFactory.getFeedController().lastPost(ControllerFactory.getUserController().getLoggedUser().getUid());
                }
                case 2 -> post = ControllerFactory.getFeedController().nextPost(ControllerFactory.getUserController().getLoggedUser().getUid());
                case 3 -> post = ControllerFactory.getFeedController().previousPost(ControllerFactory.getUserController().getLoggedUser().getUid());
                default -> topMessage = "A opção escolhida é inválida...";
            }
        }
    }

    private static void communitiesExplorerPage(Scanner scanner){
        int entrada;
        String topMessage = "";

        boolean flag = true;

        while(flag){
            clearScreen();
            if(!topMessage.equals("")){System.out.println("\n---> "+topMessage+"\n"); topMessage="";}

            System.out.println("iFace [Communities Explorer] <@"+ControllerFactory.getUserController().getLoggedUser().getUsername()+">\n");
            System.out.println("""
                    <1> Criar Comunidade 
                    <2> Listar Todas as Comunidades
                    <3> Buscar Comunidade 
                    <0> Voltar""".indent(4));
            System.out.print("Escolha: ");
            entrada = scanner.nextInt();

            switch (entrada) {
                case 0 -> flag = false;
                case 1 -> ControllerFactory.getCommunityController().createCommunityDialog();
                case 2 -> {
                    clearScreen();
                    System.out.println("iFace [Communities List] <@"+ControllerFactory.getUserController().getLoggedUser().getUsername()+">");

                    String list = ControllerFactory.getCommunityController().listCommunities();
                    System.out.println();
                    System.out.println(list.equals("") ? "Ainda não existem comunidades cadastradas..." : list);
                    System.out.println();

                    System.out.println("<0> Voltar");
                    System.out.print("Escolha: ");
                    int voltar = scanner.nextInt();
                    while(voltar != 0){
                        System.out.println("\nPressione \"0\" e Enter para voltar...");
                        voltar = scanner.nextInt();
                    }
                }
                case 3 -> {
                    String cid = ControllerFactory.getCommunityController().searchDialog();
                    communityPage(cid, scanner);
                }
                default -> topMessage = "A opção escolhida é inválida...";
            }
        }
    }

    private static void communityPage(String cid, Scanner scanner){

    }

    private static void chatsPage(Scanner scanner){}

    private static void profilePage(Scanner scanner){}

    public static void testDisplayPost(){
        Post post = new Post();
        User me = new User();
        me.setUsername("taigopedrosa");
        post.setOwner(me);
        post.setTitle("This is how you Lorem Ipsum");
        post.setMessage("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Morbi iaculis vitae lectus vitae aliquet. Nam at bibendum tortor. Quisque egestas lorem id mauris vestibulum vehicula. Nullam suscipit volutpat tempor. Curabitur vitae metus elit. Nulla aliquam volutpat dapibus. Aenean non pulvinar nisi. Aliquam erat volutpat.");

        System.out.println(ControllerFactory.getFeedController().displayPost(post));
    }

    public static void testUserController() {
        UserController controller = new UserController(); //TODO: Jogar num factory com flyweight
        controller.createUserDialog();
        controller.loginDialog();
        controller.updateUserDialog();
        System.out.println(controller.displayLoggedUser());
        controller.logoutDialog();
        controller.createUserDialog();
        controller.loginDialog();
        System.out.println(controller.displayLoggedUser());
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
