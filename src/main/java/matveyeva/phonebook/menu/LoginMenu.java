package matveyeva.phonebook.menu;

import matveyeva.phonebook.crud.UserCRUD;
import matveyeva.phonebook.entity.User;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class LoginMenu implements Menu {

    private final Scanner scanner = new Scanner(System.in);
    private final UserCRUD userCRUD = UserCRUD.INSTTANCE;
    private static final Logger logger = Logger.getLogger(LoginMenu.class);

    public LoginMenu() {
        logger.info("LoginMenu opened");
    }

    public void showMenu() throws InputMismatchException {
        boolean check = false;

        while (!check) {
            System.out.println("login | registration | exit");
            switch (scanner.nextInt()) {
                case 1:
                    login();
                    check = true;
                    break;
                case 2:
                    registration();
                    check = true;
                    break;
                case 3:
                    exit();
                    check = true;
                    break;
            }
        }
    }

    public void exit() {
        System.out.println("Exit");
        logger.info("Application closed");
        try {
            userCRUD.reloadUsers();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void login() {
        System.out.println("Enter username,password");
        User user;
        String namePass = scanner.next();
        if (namePass.equals("admin,admin")) {
            logger.info("Admin logged in");
            AdminMenu adminMenu = new AdminMenu();

            adminMenu.showMenu();
        } else if ((user = userCRUD.findOne(namePass)) != null) {
            logger.info("User " + user.getUserName() + " logged in");
            MainMenu mainMenu = new MainMenu(user);
            mainMenu.showMenu();
        } else {
            showMenu();
        }

    }

    private void registration() {
        System.out.println("Create new  username,password");
        User user;
        if ((user = userCRUD.createUser(scanner.next())) != null) {
            logger.info("User " + user.getUserName() + " created and logged in");
            MainMenu mainMenu = new MainMenu(user);
            mainMenu.showMenu();
        } else {
            showMenu();
        }
    }
}
