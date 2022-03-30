package br.ufal.ic.prog2.Controller;

import br.ufal.ic.prog2.Bean.User;
import br.ufal.ic.prog2.Controller.IdentifierEnums.UserIdentifier;
import br.ufal.ic.prog2.DAO.StorageFactory;
import br.ufal.ic.prog2.DAO.UserStorage;

import java.util.Random;
import java.util.Scanner;

public class UserController {

    User loggedUser;
    private static final Scanner scanner = new Scanner(System.in);

    private void sendFriendInvite(UserIdentifier type, String Identifier){
        if(type.equals(UserIdentifier.UID)){

        } else if (type.equals(UserIdentifier.DISPLAY_NAME)){

        }
    }

    public void sendInviteDialog(){



    }

    private String generateRandomUid(){
        Random random = new Random();
        int rUid = random.nextInt(99999998);
        String preUid =  String.valueOf(rUid);
        String uid = "";
        for( int i = 0; i < (8 - preUid.length()); i++){
            uid = uid.concat("0");
        }
        uid = uid.concat(preUid);

        return uid;
    }

    public boolean deleteUserDialog(){
        UserStorage userStorage = StorageFactory.getUserStorageObject();
        System.out.println("iFace > Apagar Perfil\n");

        if(loggedUser == null){
            System.out.println("Você ainda não está logado... Vamos fazer seu login\n");
            loginDialog();
        } else {
            System.out.println("Olá "+loggedUser.getUsername()+"...\n");
        }

        System.out.println("Deseja realmente apagar seu perfil? (\"sim\" para afirmativo, qualquer outra coisa para negativo)");
        if(scanner.next().equals("sim")){

            userStorage.getMemoryDatabase().remove(loggedUser.getUid());
            userStorage.getUsernameToUidDatabase().remove(loggedUser.getUsername());

            this.loggedUser = null;
            System.out.println("Seu perfil foi excluído e você saiu do sistema, volte sempre...");
            return true;
        } else {
            System.out.println("Você escolheu não apagar seu perfil, voltando para o menu principal...");
            return false;
        }
    }

    public User updateUserDialog(){
        UserStorage userStorage = StorageFactory.getUserStorageObject();
        System.out.println("iFace > Edição de Perfil\n");

        if(loggedUser == null){
            System.out.println("Você ainda não está logado... Vamos fazer seu login\n");
            loginDialog();
        } else {
            System.out.println("Olá "+loggedUser.getUsername()+"... Vamos alterar seu perfil!\n");
        }

        System.out.println("Atualmente, o único atributo de perfil disponível é \"Display Name\": "+loggedUser.getDisplayName()+", seu nome...");
        System.out.println("Deseja realmente alterar? (\"sim\" para afirmativo, qualquer outra coisa para negativo)");
        if(scanner.next().toString().equals("sim")){
            System.out.println("Informe seu primeiro nome (sem espaços): ");
            String firstDisplayName = scanner.next();
            System.out.println("Informe seu último nome (sem espaços): ");
            String lastDisplayName = scanner.next();

            String displayName = firstDisplayName.concat(" ").concat(lastDisplayName);
            loggedUser.setDisplayName(displayName);

            userStorage.getMemoryDatabase().put(loggedUser.getUid(), loggedUser);
        } else {
            System.out.println("Você escolheu não alterar seu perfil, voltando para o menu principal...");
        }

        return loggedUser;
    }

    public User createUserDialog(){
        UserStorage userStorage = StorageFactory.getUserStorageObject();

        System.out.println("iFace > Criar novo usuário\n");

        System.out.println("Informe seu login desejado (sem espaços): ");
        String username = scanner.next();
        while(userStorage.usernameAlreadyExists(username)){
            System.out.println("\n O login \""+username+"\" já existe...");
            System.out.println("Informe seu login desejado (sem espaços): ");
            username = scanner.next();
        }

        String uid = generateRandomUid();
        while(userStorage.uidAlreadyExists(uid)){
            uid = generateRandomUid();
        }

        System.out.println("Informe seu primeiro nome (sem espaços): ");
        String firstDisplayName = scanner.next();
        System.out.println("Informe seu último nome (sem espaços): ");
        String lastDisplayName = scanner.next();

        String displayName = firstDisplayName.concat(" ").concat(lastDisplayName);

        System.out.println("Informe sua senha (sem espaços): ");
        String password = scanner.next();

        User user = new User();
        user.setUsername(username);
        user.setDisplayName(displayName);
        user.setUid(uid);
        user.setPassword(password);

        userStorage.storeUser(user);

        System.out.println("Usuário criado com sucesso!");
        return user;
    }

    public User loginDialog(){
        UserStorage userStorage = StorageFactory.getUserStorageObject();

        System.out.println("iFace > Entrar\n");

        System.out.println("Informe seu login (sem espaços): ");
        String login = scanner.next();
        System.out.println("Informe sua senha (sem espaços): ");
        String password = scanner.next();

        User user = userStorage.attemptLogin(login, password);

        while(user == null){
            System.out.println("\nLogin ou senha incorretos, tente novamente...");

            System.out.println("Informe seu login (sem espaços): ");
            login = scanner.next();
            System.out.println("Informe sua senha (sem espaços): ");
            password = scanner.next();

            user = userStorage.attemptLogin(login, password);
        }

        System.out.println("Login realizado com sucesso!");
        this.loggedUser = user;
        return user;
    }

    public boolean logoutDialog(){
        UserStorage userStorage = StorageFactory.getUserStorageObject();

        System.out.println("iFace > Sair\n");

        System.out.println("Deseja realmente sair? (\"sim\" para afirmativo, qualquer outra coisa para negativo)");
        if(scanner.next().toString().equals("sim")){
            this.loggedUser = null;
            System.out.println("Você saiu do sistema, volte sempre...");
            return true;
        } else {
            System.out.println("Você escolheu não sair, voltando para o menu principal...");
            return false;
        }
    }

    public String displayUser(){
        if(loggedUser == null){
            return "Não há usuário logado";
        }

        return loggedUser.getUid()+" {"
                +"\n    username: "+loggedUser.getUsername()
                +"\n    DisplayName: "+loggedUser.getDisplayName()
                +"\n    Password: "+loggedUser.getPassword()
                +"\n}";
    }

}
