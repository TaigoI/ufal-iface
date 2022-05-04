package br.ufal.ic.prog2.Controller;

import br.ufal.ic.prog2.Factory.ControllerFactory;
import br.ufal.ic.prog2.Factory.ViewFactory;
import br.ufal.ic.prog2.Model.Bean.Post;
import br.ufal.ic.prog2.Model.Bean.User;
import br.ufal.ic.prog2.View.CommunityCLI;
import br.ufal.ic.prog2.View.PagesCLI;

import java.util.Scanner;

public class PagesController {

    private PagesCLI CLI;

    PagesController(){
        this.CLI = ViewFactory.getPagesCLI();
    }

    private String publicMainPage(Scanner scanner){
        int option = CLI.dialogUnloggedOptions();

        switch (option) {
            case 0 -> {
                return "END";
            }
            case 1 -> ControllerFactory.getUserController().createUserDialog();
            case 2 -> ControllerFactory.getUserController().loginDialog();
            default -> {
                return "A opção escolhida é inválida...";
            }
        }
        return "";
    }

    private String privateMainPage(Scanner scanner){
        int entrada = CLI.dialogMainMenu();

        switch (entrada) {
            case 0 -> ControllerFactory.getUserController().logoutDialog();
            case 1 -> feedPage(scanner);
            case 2 -> communitiesExplorerPage(scanner);
            case 3 -> chatsPage(scanner);
            case 4 -> profilePage(scanner);
            default -> {
                return "A opção escolhida é inválida...";
            }
        }
        return "";
    }

    private String feedPage(Scanner scanner){
        String topMessage = "";
        Post post =

        while(true){
            if(!topMessage.equals("")){System.out.println("\n---> "+topMessage+"\n"); topMessage="";}


            int entrada = CLI.dialogMainFeed(post);

            switch (entrada) {
                case 0 -> {
                    return topMessage;
                }
                case 1 -> {
                    User loggedUser = ControllerFactory.getUserController().getLoggedUser();
                    ControllerFactory.getFeedController().createPost(loggedUser.getFeed(), loggedUser);
                    ControllerFactory.getFeedController().
                    post = ControllerFactory.getFeedController().lastPost(loggedUser.getFeed(), loggedUser);
                }
                case 2 -> {
                    post = ControllerFactory.getFeedController().nextPost(ControllerFactory.getUserController().getLoggedUser().getId());
                }
                case 3 -> {
                    post = ControllerFactory.getFeedController().previousPost(ControllerFactory.getUserController().getLoggedUser().getId());
                }
                default -> {
                    topMessage = "A opção escolhida é inválida...";
                }
            }
        }

        return topMessage;
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
                    String cid = ControllerFactory.getCommunityController().searchCommunitiesByName();
                    if(cid != null){
                        communityPage(cid, scanner);
                    }
                }
                default -> topMessage = "A opção escolhida é inválida...";
            }
        }
    }

    private static void communityPage(String cid, Scanner scanner){

    }

    private static void chatsPage(Scanner scanner){}

    private static void profilePage(Scanner scanner){}
}
