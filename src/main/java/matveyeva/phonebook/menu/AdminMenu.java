package matveyeva.phonebook.menu;

import matveyeva.phonebook.crud.UserCRUD;
import matveyeva.phonebook.entity.User;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Scanner;


public class AdminMenu implements Menu {
    private Scanner scanner = new Scanner(System.in);
    private UserCRUD crud;
    private static final Logger logger = Logger.getLogger(AdminMenu.class);

    public AdminMenu() {
        crud = new UserCRUD();
        logger.info("AdminMenu opened");
    }

    public void showMenu() {
        boolean check = false;
        while (!check) {
            System.out.println("show user| add user | update user | delete user | show all | delete all | logoff | exit");
            switch (scanner.nextInt()) {
                case 1:
                    readOne();
                    check = true;
                    break;
                case 2:
                    createUser();
                    check = true;
                    break;
                case 3:
                    update();
                    check = true;
                    break;
                case 4:
                    delete();
                    check = true;
                    break;
                case 5:
                    readAll();
                    check = true;
                    break;
                case 6:
                    deleteAll();
                    check = true;
                    break;
                case 7:
                    logoff();
                    check = true;
                    break;
                case 8:
                    exit();
                    check = true;
                    break;
            }
        }
    }

    private void logoff() {
        try {
            logger.info("Admin logged off");
            crud.reloadUsers();

        } catch (IOException e) {
            e.printStackTrace();
        }
        LoginMenu loginMenu = new LoginMenu();
        loginMenu.showMenu();
    }

    private void createUser() {
        System.out.println("Enter username,password");
        if(scanner.hasNext()) {
            String str = scanner.next();
            User user;
            if((user = crud.createUser(str)) != null) {
                logger.info("Admin created new user" + user.getUserName());
                System.out.println("Created: " + user.getUserName());
            }
//            else System.out.println("user exists or user is not valid");
        }
        showMenu();
    }

    private void readOne() {
        System.out.println("Enter username");
        if(scanner.hasNext()) {
            String username = scanner.next();
            User user;

            if((user = crud.findByName(username)) != null) {
                logger.info("Admin read user " + user.getUserName());
                System.out.println("Username: " + user.getUserName() + ", password: " + user.getPassword());
            } else System.out.printf("user not found\n");
        }
        showMenu();
    }

    private void deleteAll() {
        if(!crud.findAll().isEmpty()) {
            System.out.println("Do you want to delete all users? \nYes | No");
            switch (Integer.parseInt(scanner.next())) {
                case 1:
                    try {
                        crud.deleteAll();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    logger.info("Admin deleted all users");
                    System.out.println("All users deleted");
                    break;
                case 2:
                    break;
            }
        } else System.out.println("Nothing to delete");
        showMenu();
    }

    public void exit() {
        System.out.println("Exit");
        logger.info("Admin closed the application");
        try {
            crud.reloadUsers();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readAll() {
        if(crud.findAll().isEmpty()) {
            System.out.println("Nothing to show");
        } else {
            logger.info("Admin read all users");
            System.out.println("All users: ");
            for(User user : crud.findAll()) {
                System.out.println(user.getUserName());
            }
        }
        showMenu();
    }

    private void delete() {
        System.out.println("Enter username");
        String username = scanner.next();
        User user;

        if((user = crud.findByName(username)) != null) {
            System.out.println("Do you want to delete " + user.getUserName() + "\nYes | No");
            String answer = scanner.next();
            switch (Integer.parseInt(answer)) {
                case 1:
                    try {
                        crud.delete(user);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    logger.info("Admin deleted user " + user.getUserName());
                    System.out.println("User deleted");
                    break;
                case 2:
                    break;
            }
        } else
            System.out.println("User not found\n");

        showMenu();
    }

    private void update() {
        System.out.println("Enter username");

        String username = scanner.next();
        User user;
        if((user = crud.findByName(username)) != null) {
            System.out.println("Enter username,password");
            String newContact = scanner.next();
            if((user = crud.update(newContact, user)) != null) {
                logger.info("Admin updated " + user.getUserName());
                System.out.println("Updated: " + user.getUserName());
            }
        } else
            System.out.println("User not found\n");

        showMenu();
    }

}
