package matveyeva.phonebook.menu;

import matveyeva.phonebook.crud.ContactCRUD;
import matveyeva.phonebook.crud.UserCRUD;
import matveyeva.phonebook.entity.Contact;
import matveyeva.phonebook.entity.User;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Scanner;

public class MainMenu {
    private  Scanner scanner = new Scanner(System.in);
    private ContactCRUD concrud;
    private UserCRUD userCRUD;
    private User user;
    static Logger logger;

    public MainMenu(User user){
        logger = Logger.getLogger(this.getClass());
        logger.info("MainMenu opened");
        this.user = user;
        concrud = new ContactCRUD(user);
        userCRUD = new UserCRUD();
    }


    public void showMenu() {
        boolean check = false;
        while(!check) {
            System.out.println("show contact| add contact | update contact | delete contact | show all | delete all | logoff | exit");
            switch (scanner.nextInt()) {
                case 1:
                    readOne();
                    check = true;
                    break;
                case 2:
                    createContact();
                    check = true;
                    break;
                case 3:
                    updateContact();
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
            logger.info("User " + user.getUserName() + " logged off");
            concrud.reloadContacts();
            userCRUD.reloadUsers();

        } catch (IOException e) {
            e.printStackTrace();
        }
        LoginMenu loginMenu = new LoginMenu();
        loginMenu.start();
    }

    private void deleteAll() {
        if(!concrud.readAll().isEmpty()){
            System.out.println("Do you want to delete all contacts? \nYes | No");
            switch (Integer.parseInt(scanner.next())){
                case 1:
                    concrud.deleteAll();
                    logger.info("All " + user.getUserName() + "s contacts deleted");
                    System.out.println("All contacts deleted");
                    break;
                case 2:
                    break;
            }
        } else System.out.println("Nothing to delete");
        showMenu();
    }

    private void delete() {
        System.out.println("Enter phoneNumber");
        String phone = scanner.next();
        Contact contact;

        if((contact = concrud.findByNumber(phone)) != null){
            System.out.println("Do you want to delete " + contact.toString()+ "\nYes | No");
            String answer = scanner.next();
            switch (Integer.parseInt(answer)){
                case 1:
                    concrud.delete(contact);
                    logger.info(user.getUserName() + "s contact " + contact +" deleted");
                    System.out.println("Contact deleted");
                    break;
                case 2:
                    break;
            }
        }
//        else
//            System.out.println("Contact not found\n");

        showMenu();
    }

    private void exit() {
        System.out.println("Exit");
        logger.info("User " + user.getUserName() + " closed the application");
        try {
            concrud.reloadContacts();
            userCRUD.reloadUsers();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createContact(){
        System.out.println("Enter firstName,lastName,phoneNumber");
        if(scanner.hasNext()){
            String str = scanner.next();
            Contact con;
            if((con = concrud.create(str))!= null){
                logger.info("User " + user.getUserName() + " created new contact");
                System.out.println("Created: " + con);
            }
//            else System.out.println("Contact exists or contact is not valid");
        }
        showMenu();
    }

    private void readAll(){
        if(concrud.readAll().isEmpty()) {
            System.out.println("Nothing to show");
        }else {
            logger.info("User " + user.getUserName() + " read all contacts");
            System.out.println("All contacts: ");
            for(Contact con : concrud.readAll()) {
                System.out.println(con);
            }
        }
        showMenu();
    }
    private void updateContact(){
        System.out.println("Enter phoneNumber");

        String phone = scanner.next();
        Contact contact;
        if((contact = concrud.findByNumber(phone)) != null){
            System.out.println("Enter firstName,lastName,phoneNumber");
            String newContact = scanner.next();
            if((contact = concrud.update(newContact,contact)) != null){
                logger.info("User " + user.getUserName() + " updated contact " + contact);
                System.out.println("Updated: " + contact);
            }
//            else System.out.println("Contact is not valid");
        }
//        else System.out.println("Contact not found\n");

        showMenu();
    }

    private void readOne(){
        System.out.println("Enter phoneNumber");

        if(scanner.hasNext()){
            String phone = scanner.next();
            Contact contact;
            if((contact = concrud.findByNumber(phone)) != null){
                logger.info("User " + user.getUserName() + " read contact " + contact);
                System.out.println(contact);
            }
//            else System.out.printf("Contact not found\n");
        }
        showMenu();
    }


}
