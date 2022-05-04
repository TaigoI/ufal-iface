package br.ufal.ic.prog2.Controller;

import br.ufal.ic.prog2.Model.Bean.User;
import br.ufal.ic.prog2.Factory.StorageFactory;
import br.ufal.ic.prog2.Model.DAO.UserStorage;

import java.util.Random;
import java.util.Scanner;

public class UserController {

    private User loggedUser;

    private static final Scanner scanner = new Scanner(System.in);

    /*private void sendFriendInvite(UserIdentifier type, String Identifier){
        if(type.equals(UserIdentifier.UID)){

        } else if (type.equals(UserIdentifier.DISPLAY_NAME)){

        }
    }*/

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
        UserStorage userStorage = StorageFactory.getUserStorage();
        System.out.println("iFace > Apagar Perfil\n");

        if(loggedUser == null){
            System.out.println("Você ainda não está logado... Vamos fazer seu login\n");
            loginDialog();
        } else {
            System.out.println("Olá "+loggedUser.getUsername()+"...\n");
        }

        System.out.println("Deseja realmente apagar seu perfil? (\"sim\" para afirmativo, qualquer outra coisa para negativo)");
        if(scanner.next().equals("sim")){

            userStorage.getMemoryDatabase().remove(loggedUser.getId());
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
        UserStorage userStorage = StorageFactory.getUserStorage();
        System.out.println("iFace > Edição de Perfil\n");

        if(loggedUser == null){
            System.out.println("Você ainda não está logado... Vamos fazer seu login\n");
            loginDialog();
        } else {
            System.out.println("Olá "+loggedUser.getUsername()+"... Vamos alterar seu perfil!\n");
        }

        System.out.println("Atualmente, o único atributo de perfil disponível é \"Display Name\": "+loggedUser.getDisplayName()+", seu nome...");
        System.out.println("Deseja realmente alterar? (\"sim\" para afirmativo, qualquer outra coisa para negativo)");
        if(scanner.next().equals("sim")){
            System.out.println("Informe seu primeiro nome (sem espaços): ");
            String firstDisplayName = scanner.next();
            System.out.println("Informe seu último nome (sem espaços): ");
            String lastDisplayName = scanner.next();

            String displayName = firstDisplayName.concat(" ").concat(lastDisplayName);
            loggedUser.setDisplayName(displayName);

            userStorage.getMemoryDatabase().put(loggedUser.getId(), loggedUser);
        } else {
            System.out.println("Você escolheu não alterar seu perfil, voltando para o menu principal...");
        }

        return loggedUser;
    }

    public User createUserDialog(){
        UserStorage userStorage = StorageFactory.getUserStorage();

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
        user.setId(uid);
        user.setPassword(password);

        userStorage.createUser(user);

        System.out.println("Usuário criado com sucesso!");
        return user;
    }

    public User loginDialog(){
        if(StorageFactory.getUserStorage().isEmpty()){
            User newUser = createUserDialog();
            this.loggedUser = newUser;
            return newUser;
        }

        System.out.println("iFace > Entrar\n");

        System.out.println("Informe seu login (sem espaços): ");
        String login = scanner.next();
        System.out.println("Informe sua senha (sem espaços): ");
        String password = scanner.next();

        User user = StorageFactory.getUserStorage().attemptLogin(login, password);

        while(user == null){
            clearScreen();

            System.out.println("\n---> Login ou senha incorretos, tente novamente...");

            System.out.println("Informe seu login (sem espaços): ");
            login = scanner.next();
            System.out.println("Informe sua senha (sem espaços): ");
            password = scanner.next();

            user = StorageFactory.getUserStorage().attemptLogin(login, password);
        }

        System.out.println("Login realizado com sucesso!");
        this.loggedUser = user;
        return user;
    }

    public boolean logoutDialog(){
        UserStorage userStorage = StorageFactory.getUserStorage();

        System.out.println("iFace > Sair\n");

        System.out.println("Deseja realmente sair? (\"sim\" para afirmativo, qualquer outra coisa para negativo)");
        if(scanner.next().equals("sim")){
            this.loggedUser = null;
            System.out.println("Você saiu do sistema, volte sempre...");
            return true;
        } else {
            System.out.println("Você escolheu não sair, voltando para o menu principal...");
            return false;
        }
    }

    public String displayLoggedUser(){
        if(loggedUser == null){
            return "Não há usuário logado";
        }
        return displayUser(loggedUser);
    }

    public String displayUser(User user){
        return user.getId()+" {"
                +"\n    username: "+user.getUsername()
                +"\n    DisplayName: "+user.getDisplayName()
                +"\n    Password: "+user.getPassword()
                +"\n}";
    }

    public User getLoggedUser() {
        return loggedUser;
    }

}
