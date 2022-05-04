package br.ufal.ic.prog2.View;

import br.ufal.ic.prog2.Model.Bean.Community;
import br.ufal.ic.prog2.Model.Bean.Post;

import java.util.ArrayList;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class CommunityCLI extends BaseCLI {

    public void showHeader(){
        super.showHeader();
        System.out.println(" > Communities");
    }
    public void showHeader(String action){
        showHeader();
        System.out.println(" + "+action);
    }

    public String dialogCommunityName(String action) {
        showHeader(action);
        return getNextWord("Nome: ");
    }

    public String dialogCommunityDescription(String action) {
        showHeader(action);
        return getNextSentence("Descrição: ");
    }

    public String dialogSearchByName(ArrayList<String> sortedSearch) {
        showHeader();
        System.out.println("Busca de Comunidades");

        String name = getNextWord("Informe o termo de busca: ");

        int pI = 0;
        int pF = min(5,sortedSearch.size()-1);
        while(true){
            int j = 1;
            ArrayList<Integer> indices = new ArrayList<>();
            ArrayList<String> descriptions = new ArrayList<>();

            for(int i = pI; i <= pF; i++, j++){
                if(j>5) break;
                indices.add(j); descriptions.add(sortedSearch.get(i));
            }

            indices.add(-1);
            indices.add(0); descriptions.add("Voltar");
            indices.add(6); descriptions.add("Anterior");
            indices.add(7); descriptions.add("Próxima");

            int option = getNextOption("\nEscolha a opção:", indices, descriptions);

            if(option == 0){
                return null;
            } else if (option == 6){
                pI = max(0,pI-5); pF = min(pF-5,sortedSearch.size()-1);
            } else if (option == 7){
                pI = max(0,pI+5); pF = min(pF+5,sortedSearch.size()-1);
            } else if (option >= 1 && option <= 5){
                return sortedSearch.get(option-1);
            }
        }
    }

    public String getCommunityAsText(Community community){
        if(community == null){
            return "Comunidade indefinida.\n";
        }

        return ""
                + community.getId() +": {\n"
                +String.valueOf("name:        \"" + community.getName() + "\"\n").indent(4)
                +String.valueOf("description: \"" + community.getDescription().replaceAll("(.{0,70})\\s+", "$1\n")+"\"\n").indent(4)
                +"}\n";
    }

    public void showCommunityAsText(Community community){
        System.out.println(getCommunityAsText(community));
    }
}
