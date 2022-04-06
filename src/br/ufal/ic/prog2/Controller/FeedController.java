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
        User user = StorageFactory.getUserStorage().getUserByUid(uid).protectPersonalData();

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
        System.out.println("(nextPost) PRE: "+position.get(uid).toString());

        if(position.get(uid)+1 >= history.get(uid).size()){
            User user = StorageFactory.getUserStorage().getUserByUid(uid);
            Post next = StorageFactory.getFeedStorage().findNextPost(user);
            if(next != null){
                history.get(uid).add(next);
            }

            position.put(uid, history.get(uid).size()-1);
            System.out.println("(nextPost) POST1: "+position.get(uid).toString());
            return next;
        } else {
            position.put(uid, position.get(uid)+1);
            System.out.println("(nextPost) POST2: "+position.get(uid).toString());
            return history.get(uid).get(position.get(uid));
        }
    }

    public Post previousPost(String uid) {
        if(!history.containsKey(uid)){
            history.put(uid, new ArrayList<>());
            position.put(uid, -1);
        }
        System.out.println("(prevPost) PRE: "+position.get(uid).toString());

        if(position.get(uid)-1 < 0){
            position.put(uid, -1);
            System.out.println("(prevPost) POST1: "+position.get(uid).toString());
            return null;
        } else {
            position.put(uid, position.get(uid)-1);
            System.out.println("(prevPost) POST2: "+position.get(uid).toString());
            return history.get(uid).get(position.get(uid));
        }
    }

    public String displayPost(Post post){
        if(post == null){
            return "Não foram encontrados posts.\n";
        }

        return ""
                +"> "+ post.getTitle()+"\n"
                +"@" + post.getOwner().getUsername()+"\n\n"
                +"\""+post.getMessage().replaceAll("(.{1,100})\\s+", "$1\n")+"\"\n";
    }
}
