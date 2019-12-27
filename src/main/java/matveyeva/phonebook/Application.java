package matveyeva.phonebook;

import java.util.InputMismatchException;
import matveyeva.phonebook.menu.LoginMenu;

public class Application {

    public static void main(String[] args) {
        LoginMenu app = new LoginMenu();
        try {
            app.showMenu();
        } catch (InputMismatchException ex) {
            app.showMenu();
        }
    }
}
