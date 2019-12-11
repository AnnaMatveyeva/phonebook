package matveyeva.phonebook;

import matveyeva.phonebook.menu.LoginMenu;

public class Application {

    public static void main(String[] args) throws Exception {
        LoginMenu app = new LoginMenu();
        app.start();
    }
}
