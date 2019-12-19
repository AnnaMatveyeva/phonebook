package matveyeva.phonebook.menu;

import java.util.Scanner;
import matveyeva.phonebook.service.AuthorizationService;
import matveyeva.phonebook.service.UserService;
import org.apache.log4j.Logger;


public class AdminMenu implements Menu {

    private Scanner scanner = new Scanner(System.in);
    private static final Logger logger = Logger.getLogger(AdminMenu.class);
    private UserService userService = UserService.INSTANCE;
    private AuthorizationService authService = AuthorizationService.INSTANCE;

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

}
