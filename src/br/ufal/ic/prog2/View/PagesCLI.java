package br.ufal.ic.prog2.View;

import br.ufal.ic.prog2.Factory.ControllerFactory;
import br.ufal.ic.prog2.Factory.ViewFactory;
import br.ufal.ic.prog2.Model.Bean.Post;
import br.ufal.ic.prog2.Model.Bean.User;

import java.util.ArrayList;

public class PagesCLI extends BaseCLI{

    public void showHeader(String action){
        super.showHeader();
        System.out.println(" + "+action);
    }

    public int dialogUnloggedOptions(){
        showHeader("Bem vindo");

        ArrayList<Integer> indices = new ArrayList<>();
        ArrayList<String> descriptions = new ArrayList<>();

        indices.add(1); descriptions.add("Novo Usuário");
        indices.add(2); descriptions.add("Entrar");
        indices.add(0); descriptions.add("Fechar Aplicação");

        return getNextOption("\nEscolha: ", indices, descriptions);
    }

    public int dialogMainMenu(){
        showHeader();

        ArrayList<Integer> indices = new ArrayList<>();
        ArrayList<String> descriptions = new ArrayList<>();

        indices.add(1); descriptions.add("Feed");
        indices.add(2); descriptions.add("Comunidades");
        indices.add(3); descriptions.add("Chat");
        indices.add(4); descriptions.add("Meu Perfil");
        indices.add(0); descriptions.add("Sair");

        return getNextOption("\nEscolha: ", indices, descriptions);
    }

    public int dialogMainFeed(Post post){
        showHeader("Main Feed");
        ViewFactory.getPostCLI().showPostAsText(post);

        ArrayList<Integer> indices = new ArrayList<>();
        ArrayList<String> descriptions = new ArrayList<>();

        indices.add(1); descriptions.add("Novo Post");
        indices.add(2); descriptions.add("Próximo");
        indices.add(3); descriptions.add("Anterior");
        indices.add(0); descriptions.add("Voltar");
        return getNextOption("\nEscolha: ", indices, descriptions);
    }
}
