package matveyeva.phonebook.menu;

import java.util.InputMismatchException;
import java.util.Scanner;
import matveyeva.phonebook.crud.UserCRUD;
import matveyeva.phonebook.service.AuthorizationService;
import org.apache.log4j.Logger;

public class LoginMenu implements Menu{

    private final Scanner scanner = new Scanner(System.in);
    private final UserCRUD userCRUD = UserCRUD.INSTANCE;
    private static final Logger logger = Logger.getLogger(LoginMenu.class);
    private AuthorizationService authService = AuthorizationService.getInstance(userCRUD,scanner);

    public LoginMenu() {
        logger.info("LoginMenu opened");
    }

    public void showMenu() throws InputMismatchException {
        boolean check = false;

        while (!check) {
            System.out.println("login | registration | exit");
            switch (scanner.nextInt()) {
                case 1:
                    authService.login();
                    break;
                case 2:
                    authService.registration();
                    break;
                case 3:
                    authService.exit();
                    check = true;
                    break;
            }
        }
    }
}
