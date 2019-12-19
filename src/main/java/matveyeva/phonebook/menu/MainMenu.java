package matveyeva.phonebook.menu;

import java.util.Scanner;
import matveyeva.phonebook.entity.User;
import matveyeva.phonebook.service.AuthorizationService;
import matveyeva.phonebook.service.ContactService;
import org.apache.log4j.Logger;

public class MainMenu implements Menu {

    private Scanner scanner = new Scanner(System.in);
    private User user;
    private static final Logger logger = Logger.getLogger(MainMenu.class);
    private ContactService contactService = ContactService.INSTANCE;
    private AuthorizationService authService = AuthorizationService.INSTANCE;

    public MainMenu(User user) {
        logger.info("MainMenu opened");
        this.user = user;
    }

    public void showMenu() {
        boolean check = false;
        while (!check) {
            System.out.println(
                "show contact| add contact | update contact | delete contact | show all | delete all | logoff | exit");
            switch (scanner.nextInt()) {
                case 1:
                    contactService.readOne(user);
                    break;
                case 2:
                    contactService.createContact(user);
                    break;
                case 3:
                    contactService.updateContact(user);
                    break;
                case 4:
                    contactService.delete(user);
                    break;
                case 5:
                    contactService.readAll(user);
                    break;
                case 6:
                    contactService.deleteAll(user);
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
