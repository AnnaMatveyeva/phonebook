package matveyeva.phonebook.service;

import java.io.IOException;
import java.util.Scanner;
import matveyeva.phonebook.crud.UserCRUD;
import matveyeva.phonebook.entity.User;
import org.apache.log4j.Logger;

public class UserService {

    private Scanner scanner;
    private UserCRUD crud;
    private static final Logger logger = Logger.getLogger(UserService.class);
    private static UserService instance;

    private UserService(UserCRUD crud, Scanner scanner) {
        this.crud = crud;
        this.scanner = scanner;
    }

    public static UserService getInstance(UserCRUD crud, Scanner scanner) {
        if (instance == null) {
            instance = new UserService(crud, scanner);
            return instance;
        }else return instance;
    }

    public void createUser() {
        System.out.println("Enter username,password");
        if (scanner.hasNext()) {
            String str = scanner.next();
            User user;
            if ((user = crud.createUser(str)) != null) {
                logger.info("Admin created new user" + user.getUserName());
                System.out.println("Created: " + user.getUserName());
            }
        }
    }

    public void readOne() {
        System.out.println("Enter username");
        if (scanner.hasNext()) {
            String username = scanner.next();
            User user;

            if ((user = crud.findByName(username)) != null) {
                logger.info("Admin read user " + user.getUserName());
                System.out.println(
                    "Username: " + user.getUserName() + ", password: " + user.getPassword());
            } else {
                System.out.printf("user not found\n");
            }
        }
    }

    public void deleteAll() {
        if (!crud.findAll().isEmpty()) {
            System.out.println("Do you want to delete all users? \nYes | No");

            String answer = scanner.next();
            if (answer.equalsIgnoreCase("1")) {
                try {
                    crud.deleteAll();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                logger.info("Admin deleted all users");
                System.out.println("All users deleted");
            }
        } else {
            System.out.println("Nothing to delete");
        }
    }

    public void readAll() {
        if (crud.findAll().isEmpty()) {
            System.out.println("Nothing to show");
        } else {
            logger.info("Admin read all users");
            System.out.println("All users: ");
            for (User user : crud.findAll()) {
                System.out.println(user.getUserName());
            }
        }
    }

    public void delete() {
        System.out.println("Enter username");
        String username = scanner.next();
        User user;

        if ((user = crud.findByName(username)) != null) {
            System.out.println("Do you want to delete " + user.getUserName() + "\nYes | No");
            String answer = scanner.next();

            if (answer.equalsIgnoreCase("1")) {
                try {
                    crud.delete(user);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                logger.info("Admin deleted user " + user.getUserName());
                System.out.println("User deleted");
            }
        } else {
            System.out.println("User not found\n");
        }
    }

    public void update() {
        System.out.println("Enter username");

        String username = scanner.next();
        User user;
        if ((user = crud.findByName(username)) != null) {
            System.out.println("Enter username,password");
            String newContact = scanner.next();
            if ((user = crud.update(newContact, user)) != null) {
                logger.info("Admin updated " + user.getUserName());
                System.out.println("Updated: " + user.getUserName());
            }
        } else {
            System.out.println("User not found\n");
        }
    }

}
