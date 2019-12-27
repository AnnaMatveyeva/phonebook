package matveyeva.phonebook.service;

import java.io.IOException;
import java.util.Scanner;
import matveyeva.phonebook.crud.UserCRUD;
import matveyeva.phonebook.entity.User;
import matveyeva.phonebook.exception.InvalidUserException;
import matveyeva.phonebook.menu.AdminMenu;
import matveyeva.phonebook.menu.MainMenu;
import org.apache.log4j.Logger;

public enum AuthorizationService {
    INSTANCE;

    private static final Logger logger = Logger.getLogger(AuthorizationService.class);
    private UserCRUD crud = UserCRUD.INSTANCE;
    private static final Scanner scanner = new Scanner(System.in);

    public void logoff() {
        try {
            logger.info("User logged off");
            crud.reloadUsers();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void exit() {
        System.out.println("Exit");
        logger.info("Application closed");
        try {
            crud.reloadUsers();
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void login() {
        System.out.println("Enter username,password");
        User user;
        String namePass = scanner.next();
        if (namePass.equals("admin,admin")) {
            logger.info("Admin logged in");
            AdminMenu adminMenu = new AdminMenu();

            adminMenu.showMenu();
        } else {
            try {
                user = crud.findOne(namePass);
                logger.info("User " + user.getUserName() + " logged in");
                MainMenu mainMenu = new MainMenu(user);
                mainMenu.showMenu();
            } catch (InvalidUserException ex) {
                System.out.println(ex.getMessage());
            }
        }

    }

    public void registration() {
        System.out.println("Create new  username,password");
        User user;
        try {
            user = crud.createUser(scanner.next());
            logger.info("User " + user.getUserName() + " created and logged in");
            MainMenu mainMenu = new MainMenu(user);
            mainMenu.showMenu();
        } catch (InvalidUserException ex) {
            System.out.println(ex.getMessage());
        }
    }

}
