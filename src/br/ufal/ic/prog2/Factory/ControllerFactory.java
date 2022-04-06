package br.ufal.ic.prog2.Factory;

import br.ufal.ic.prog2.Controller.UserController;

public class ControllerFactory {

    private static final UserController userController = new UserController();

    public static UserController getUserController(){
        return userController;
    }

}
