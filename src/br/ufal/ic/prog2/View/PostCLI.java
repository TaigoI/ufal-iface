package br.ufal.ic.prog2.View;

import br.ufal.ic.prog2.Model.Bean.Post;

public class PostCLI  extends BaseCLI {

    public String getPostAsText(Post post){
        if(post == null){
            return "Não foram encontrados posts.\n";
        }

        return ""
                +"> "+ post.getTitle()+"\n"
                +String.valueOf("@" + post.getOwner().getUsername()+"   ("+ (post.getTargetName()) +")\n\n").indent(4)
                +String.valueOf("\""+ post.getMessage().replaceAll("(.{1,100})\\s+", "$1\n")+"\"\n").indent(5);
    }

    public void showPostAsText(Post post){
        System.out.println(getPostAsText(post));
    }
}
