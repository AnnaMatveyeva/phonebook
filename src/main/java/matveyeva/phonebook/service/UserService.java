package matveyeva.phonebook.service;

import matveyeva.phonebook.crud.UserCRUD;
import matveyeva.phonebook.entity.User;
import matveyeva.phonebook.exception.InvalidUserException;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Scanner;

public enum UserService {
    INSTANCE;
    private static final Scanner scanner = new Scanner(System.in);
    private UserCRUD crud = UserCRUD.INSTANCE;
    private static final Logger logger = Logger.getLogger(UserService.class);


    public void createUser() {
        System.out.println("Enter username,password");
        try {
            if(scanner.hasNext()) {
                String str = scanner.next();
                User user;
                user = crud.createUser(str);
                logger.info("Admin created new user" + user.getUserName());
                System.out.println("Created: " + user.getUserName());
            }
        } catch (InvalidUserException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void readOne() {
        System.out.println("Enter username");
        try {
            if(scanner.hasNext()) {
                String username = scanner.next();
                User user;
                user = crud.findByName(username);

                logger.info("Admin read user " + user.getUserName());
                System.out.println(
                        "Username: " + user.getUserName() + ", password: " + user.getPassword());
            }
        }catch (InvalidUserException ex){
            System.out.println(ex.getMessage());
        }
    }

    public void deleteAll() {
        if(!crud.findAll().isEmpty()) {
            System.out.println("Do you want to delete all users? \nYes | No");

            String answer = scanner.next();
            if(answer.equalsIgnoreCase("1")) {
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
        if(crud.findAll().isEmpty()) {
            System.out.println("Nothing to show");
        } else {
            logger.info("Admin read all users");
            System.out.println("All users: ");
            for(User user : crud.findAll()) {
                System.out.println(user.getUserName());
            }
        }
    }

    public void delete() {
        System.out.println("Enter username");
        String username = scanner.next();
        User user;
        try {
            user = crud.findByName(username);
            System.out.println("Do you want to delete " + user.getUserName() + "\nYes | No");
            String answer = scanner.next();

            if(answer.equalsIgnoreCase("1")) {
                try {
                    crud.delete(user);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                logger.info("Admin deleted user " + user.getUserName());
                System.out.println("User deleted");
            }
        } catch (InvalidUserException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void update() {
        System.out.println("Enter username");
        try {
            String username = scanner.next();
            User user;
            user = crud.findByName(username);
            System.out.println("Enter username,password");
            String newContact = scanner.next();
            user = crud.update(newContact, user);
            logger.info("Admin updated " + user.getUserName());
            System.out.println("Updated: " + user.getUserName());
        } catch (InvalidUserException ex) {
            System.out.println(ex.getMessage());
        }
    }

}
