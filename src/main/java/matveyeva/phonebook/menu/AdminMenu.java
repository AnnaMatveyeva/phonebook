package matveyeva.phonebook.menu;

import java.io.IOException;
import java.util.Scanner;
import matveyeva.phonebook.crud.UserCRUD;
import matveyeva.phonebook.entity.User;
import matveyeva.phonebook.service.AuthorizationService;
import matveyeva.phonebook.service.UserService;
import org.apache.log4j.Logger;


public class AdminMenu implements Menu {

    private Scanner scanner = new Scanner(System.in);
    private final UserCRUD crud = UserCRUD.INSTANCE;
    private static final Logger logger = Logger.getLogger(AdminMenu.class);
    private UserService userService = UserService.getInstance(crud, scanner);
    private AuthorizationService authService = AuthorizationService.getInstance(crud, scanner);

    public AdminMenu() {
        logger.info("AdminMenu opened");
    }

    public void showMenu() {
        boolean check = false;
        while (!check) {
            System.out.println(
                "show user| add user | update user | delete user | show all | delete all | logoff | exit");
            switch (scanner.nextInt()) {
                case 1:
                    userService.readOne();
                    break;
                case 2:
                    userService.createUser();
                    break;
                case 3:
                    userService.update();
                    break;
                case 4:
                    userService.delete();
                    break;
                case 5:
                    userService.readAll();
                    break;
                case 6:
                    userService.deleteAll();
                    break;
                case 7:
                    authService.logoff();
                    check = true;
                    break;
                case 8:
                    authService.exit();
                    break;
            }
        }
    }

//    private void logoff() {
//        try {
//            logger.info("Admin logged off");
//            crud.reloadUsers();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        LoginMenu loginMenu = new LoginMenu();
//        loginMenu.showMenu();
//    }
//
//    private void createUser() {
//        System.out.println("Enter username,password");
//        if (scanner.hasNext()) {
//            String str = scanner.next();
//            User user;
//            if ((user = crud.createUser(str)) != null) {
//                logger.info("Admin created new user" + user.getUserName());
//                System.out.println("Created: " + user.getUserName());
//            }
//        }
//        showMenu();
//    }
//
//    private void readOne() {
//        System.out.println("Enter username");
//        if (scanner.hasNext()) {
//            String username = scanner.next();
//            User user;
//
//            if ((user = crud.findByName(username)) != null) {
//                logger.info("Admin read user " + user.getUserName());
//                System.out.println(
//                    "Username: " + user.getUserName() + ", password: " + user.getPassword());
//            } else {
//                System.out.printf("user not found\n");
//            }
//        }
//        showMenu();
//    }
//
//    private void deleteAll() {
//        if (!crud.findAll().isEmpty()) {
//            System.out.println("Do you want to delete all users? \nYes | No");
//
//            String answer = scanner.next();
//            if (answer.equalsIgnoreCase("1")) {
//                try {
//                    crud.deleteAll();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                logger.info("Admin deleted all users");
//                System.out.println("All users deleted");
//            }
//        } else {
//            System.out.println("Nothing to delete");
//        }
//        showMenu();
//    }
//
//    public void exit() {
//        System.out.println("Exit");
//        logger.info("Admin closed the application");
//        try {
//            crud.reloadUsers();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void readAll() {
//        if (crud.findAll().isEmpty()) {
//            System.out.println("Nothing to show");
//        } else {
//            logger.info("Admin read all users");
//            System.out.println("All users: ");
//            for (User user : crud.findAll()) {
//                System.out.println(user.getUserName());
//            }
//        }
//        showMenu();
//    }
//
//    private void delete() {
//        System.out.println("Enter username");
//        String username = scanner.next();
//        User user;
//
//        if ((user = crud.findByName(username)) != null) {
//            System.out.println("Do you want to delete " + user.getUserName() + "\nYes | No");
//            String answer = scanner.next();
//
//            if (answer.equalsIgnoreCase("1")) {
//                try {
//                    crud.delete(user);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                logger.info("Admin deleted user " + user.getUserName());
//                System.out.println("User deleted");
//            }
//        } else {
//            System.out.println("User not found\n");
//        }
//        showMenu();
//    }
//
//    private void update() {
//        System.out.println("Enter username");
//
//        String username = scanner.next();
//        User user;
//        if ((user = crud.findByName(username)) != null) {
//            System.out.println("Enter username,password");
//            String newContact = scanner.next();
//            if ((user = crud.update(newContact, user)) != null) {
//                logger.info("Admin updated " + user.getUserName());
//                System.out.println("Updated: " + user.getUserName());
//            }
//        } else {
//            System.out.println("User not found\n");
//        }
//        showMenu();
//    }

}
