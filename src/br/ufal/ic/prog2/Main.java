package br.ufal.ic.prog2;

import br.ufal.ic.prog2.Factory.ViewFactory;
import br.ufal.ic.prog2.Model.Bean.Post;
import br.ufal.ic.prog2.Model.Bean.User;
import br.ufal.ic.prog2.Factory.ControllerFactory;
import br.ufal.ic.prog2.Controller.UserController;
import br.ufal.ic.prog2.Model.DAO.ResponseEnums.CreateUserResponse;
import br.ufal.ic.prog2.Factory.StorageFactory;
import br.ufal.ic.prog2.Model.DAO.UserStorage;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        while(true){
            try{
                if(ControllerFactory.getUserController().getLoggedUser() == null){
                    if (ControllerFactory.getPagesController().publicMainPage()) return;
                } else {
                    ControllerFactory.getPagesController().privateMainPage();
                }
            } catch (Exception e) {
                System.out.println("An unexpected error occurred returning to main menu...");
                ViewFactory.getPagesCLI().getEnter();
            }
        }
    }

}
