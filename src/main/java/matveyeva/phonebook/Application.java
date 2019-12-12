package matveyeva.phonebook;

import matveyeva.phonebook.menu.LoginMenu;

import java.util.InputMismatchException;

public class Application {

    public static void main(String[] args){
        LoginMenu app = new LoginMenu();
        try{
            app.start();
        }catch (InputMismatchException ex){
            app.start();
        }
    }
}
