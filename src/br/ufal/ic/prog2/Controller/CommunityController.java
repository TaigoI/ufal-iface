package br.ufal.ic.prog2.Controller;

import br.ufal.ic.prog2.Bean.Community;
import br.ufal.ic.prog2.Bean.Post;
import br.ufal.ic.prog2.Bean.User;
import br.ufal.ic.prog2.DAO.CommunityStorage;
import br.ufal.ic.prog2.Factory.ControllerFactory;
import br.ufal.ic.prog2.Factory.StorageFactory;

import java.util.*;

public class CommunityController {

    private static final Map<String, Map<String, ArrayList<Post>>> communitiesHistory = new HashMap<>();
    private static final Map<String, Map<String, Integer>> communitiesPosition = new HashMap<>();

    private static final Scanner scanner = new Scanner(System.in);

    private String generateRandomCid(){
        Random random = new Random();
        int rCid = random.nextInt(999999998);
        String preUid =  String.valueOf(rCid);
        String cid = "";
        for( int i = 0; i < (9 - preUid.length()); i++){
            cid = cid.concat("0");
        }
        cid = cid.concat(preUid);

        return cid;
    }

    public Community createCommunityDialog(){
        CommunityStorage communityStorage = StorageFactory.getCommunityStorage();

        System.out.println("iFace > Criar nova comunidade\n");

        System.out.println("Informe seu nome desejado (sem espaços): ");
        String name = scanner.next();
        while(communityStorage.nameAlreadyExists(name)){
            System.out.println("\n O nome \""+name+"\" já existe...");
            System.out.println("Informe seu nome desejado (sem espaços): ");
            name = scanner.next();
        }

        String cid = generateRandomCid();
        while(communityStorage.cidAlreadyExists(cid)){
            cid = generateRandomCid();
        }

        System.out.println("Informe a Descrição: ");
        String description = scanner.nextLine();
        if(description.equals("")){
            description = scanner.nextLine();
        }

        Community community = new Community();
        community.setCid(cid);
        community.setName(name);
        community.setOwner(ControllerFactory.getUserController().getLoggedUser());
        community.setDescription(description);

        communityStorage.storeCommunity(community);

        System.out.println("Comunidade criada com sucesso!");
        return community;
    }

    public String listCommunities(){
        String result = "";
        for (String cid : StorageFactory.getCommunityStorage().getMemoryDatabase().keySet()){
            Community community = StorageFactory.getCommunityStorage().getCommunityByCid(cid);
            result = result.concat(displayCommunity(community)+"\n");
        }
        return result;
    }

    public Post createPost(String cid, String uid) {
        if(!communitiesHistory.containsKey(cid)){
            communitiesHistory.put(cid, new HashMap<>());
            communitiesPosition.put(cid, new HashMap<>());
        }
        Map<String, ArrayList<Post>> history = communitiesHistory.get(cid);
        Map<String, Integer> position = communitiesPosition.get(cid);

        Community community = StorageFactory.getCommunityStorage().getCommunityByCid(cid);
        System.out.println("iFace [+Post@"+community.getName()+"] <@"+ ControllerFactory.getUserController().getLoggedUser().getUsername()+">\n");
        User user = StorageFactory.getUserStorage().getUserByUid(uid);

        Post newPost = new Post();
        newPost.setOwner(user);

        System.out.println("Informe o Título do Post: ");
        String title = scanner.nextLine();
        System.out.println("Informe o Texto do Post: ");
        String text = scanner.nextLine();

        newPost.setTitle(title);
        newPost.setMessage(text);

        if(!history.containsKey(uid)){
            history.put(uid, new ArrayList<>());
            position.put(uid, -1);
        }

        Post returnPost = StorageFactory.getCommunityStorage().storePost(community.getCid(), newPost);
        history.get(uid).add(returnPost);
        position.put(uid, history.get(uid).size()-1);
        return returnPost;
    }

    public Post nextPost(String cid, String uid) {
        if(!communitiesHistory.containsKey(cid)){
            communitiesHistory.put(cid, new HashMap<>());
            communitiesPosition.put(cid, new HashMap<>());
        }
        Map<String, ArrayList<Post>> history = communitiesHistory.get(cid);
        Map<String, Integer> position = communitiesPosition.get(cid);

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

    public Post previousPost(String cid, String uid) {
        if(!communitiesHistory.containsKey(cid)){
            communitiesHistory.put(cid, new HashMap<>());
            communitiesPosition.put(cid, new HashMap<>());
        }
        Map<String, ArrayList<Post>> history = communitiesHistory.get(cid);
        Map<String, Integer> position = communitiesPosition.get(cid);

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
                +"@" + post.getOwner().getUsername()+"   ("+ (post.getTarget()) +")\n\n"
                +"\""+post.getMessage().replaceAll("(.{1,100})\\s+", "$1\n")+"\"\n";
    }

    public String displayCommunity(Community community){
        if(community == null){
            return "Comunidade indefinida.\n";
        }

        return ""
                + community.getCid() +"{\n"
                +"    name:        \"" + community.getName() + "\"\n"
                +"    description: \"" + community.getDescription().replaceAll("(.{0,70})\\s+", "$1\n")+"\"\n";
    }

    public Post lastPost(String cid, String uid) {
        if(!communitiesHistory.containsKey(cid)){
            communitiesHistory.put(cid, new HashMap<>());
            communitiesPosition.put(cid, new HashMap<>());
        }
        Map<String, ArrayList<Post>> history = communitiesHistory.get(cid);
        Map<String, Integer> position = communitiesPosition.get(cid);

        if(!history.containsKey(uid)){
            history.put(uid, new ArrayList<>());
            position.put(uid, -1);
        }

        position.put(uid, history.get(uid).size()-1);
        return history.get(uid).get(position.get(uid));
    }

}
