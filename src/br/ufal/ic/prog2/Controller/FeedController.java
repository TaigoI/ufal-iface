package br.ufal.ic.prog2.Controller;

import br.ufal.ic.prog2.Bean.Post;
import br.ufal.ic.prog2.Bean.User;
import br.ufal.ic.prog2.Factory.ControllerFactory;
import br.ufal.ic.prog2.Factory.StorageFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class FeedController {

    private static final Map<String, ArrayList<Post>> history = new HashMap<>();
    private static final Map<String, Integer> position = new HashMap<>();

    private static final Scanner scanner = new Scanner(System.in);

    public Post createPost(String uid) {
        System.out.println("iFace [+Post] <@"+ ControllerFactory.getUserController().getLoggedUser().getUsername()+">\n");
        User user = StorageFactory.getUserStorage().getUserByUid(uid);
        //TODO: lembrar de clonar o objeto para evitar null pointer .protectPersonalData();

        Post newPost = new Post();
        newPost.setOwner(user);

        System.out.println("Informe o Título do Post: ");
        String title = scanner.nextLine();
        System.out.println("Informe o Texto do Post: ");
        String text = scanner.nextLine();

        newPost.setTitle(title);
        newPost.setMessage(text);

        System.out.println("Deseja marcar este post como público? (\"sim\" para afirmativo, qualquer outra coisa para negativo)");

        if(!history.containsKey(uid)){
            history.put(uid, new ArrayList<>());
            position.put(uid, -1);
        }

        Post returnPost = StorageFactory.getFeedStorage().storePost(newPost, scanner.next().equals("sim"));
        history.get(uid).add(returnPost);
        position.put(uid, history.get(uid).size()-1);
        return returnPost;
    }

    public Post nextPost(String uid) {
        if(!history.containsKey(uid)){
            history.put(uid, new ArrayList<>());
            position.put(uid, -1);
        }

        if(position.get(uid)+1 >= history.get(uid).size()){
            User user = StorageFactory.getUserStorage().getUserByUid(uid);
            Post next = StorageFactory.getFeedStorage().findNextPost(user);
            if(next != null){
                history.get(uid).add(next);
            }

            position.put(uid, history.get(uid).size()-1);
            return next;
        } else {
            position.put(uid, position.get(uid)+1);
            return history.get(uid).get(position.get(uid));
        }
    }

    public Post previousPost(String uid) {
        if(!history.containsKey(uid)){
            history.put(uid, new ArrayList<>());
            position.put(uid, -1);
        }

        if(position.get(uid).equals(0)){
            position.put(uid, -1);
            return history.get(uid).get(0);
        }
        else if(position.get(uid)-1 <= 0){
            position.put(uid, -1);
            return null;
        } else {
            position.put(uid, position.get(uid)-1);
            return history.get(uid).get(position.get(uid));
        }
    }

    public String displayPost(Post post){
        if(post == null){
            return "Não foram encontrados posts.\n";
        }

        return ""
                +"> "+ post.getTitle()+"\n"
                +"@" + post.getOwner().getUsername()+"   ("+ (post.isPublic() ? "público" : "amigos") +")\n\n"
                +"\""+post.getMessage().replaceAll("(.{1,100})\\s+", "$1\n")+"\"\n";
    }

    public Post lastPost(String uid) {
        if(!history.containsKey(uid)){
            history.put(uid, new ArrayList<>());
            position.put(uid, -1);
        }

        position.put(uid, history.get(uid).size()-1);
        return history.get(uid).get(position.get(uid));
    }
}
