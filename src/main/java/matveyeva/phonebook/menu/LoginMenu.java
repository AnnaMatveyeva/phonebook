package matveyeva.phonebook.menu;

import matveyeva.phonebook.crud.UserCRUD;
import matveyeva.phonebook.entity.User;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Scanner;

public class LoginMenu {
    private final Scanner scanner = new Scanner(System.in);
    private final UserCRUD userCRUD = new UserCRUD();
    private static Logger logger;

    public LoginMenu(){
        logger = Logger.getLogger(LoginMenu.class);
        logger.info("LoginMenu opened");
    }

    public void start(){
        boolean check = false;
        while(!check) {
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

    private void exit() {
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
        if(namePass.equals("admin,admin")){
            logger.info("Admin logged in");
            AdminMenu adminMenu = new AdminMenu();

            adminMenu.showMenu();
        }else if((user = userCRUD.findOne(namePass))!= null){
            logger.info("User " + user.getUserName() + " logged in");
            MainMenu mainMenu = new MainMenu(user);
            mainMenu.showMenu();
        } else {
            System.out.println("username/password is not valid");
            start();
        }

    }
    private void registration(){
        System.out.println("Create new  username,password");
        User user;
        if((user = userCRUD.createUser(scanner.next())) != null){
            logger.info("User " + user.getUserName() + " created and logged in");
            MainMenu mainMenu = new MainMenu(user);
            mainMenu.showMenu();
        } else{
            System.out.println("User exist / username and password are not valid");
            start();
        }
    }
}
