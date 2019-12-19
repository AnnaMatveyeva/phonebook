package matveyeva.phonebook.service;

import java.util.Scanner;
import java.util.Set;
import matveyeva.phonebook.crud.ContactCRUD;
import matveyeva.phonebook.crud.UserCRUD;
import matveyeva.phonebook.entity.Contact;
import org.apache.log4j.Logger;

public class ContactService {

    private Scanner scanner;
    private UserCRUD usercrud;
    private ContactCRUD concrud;
    private static final Logger logger = Logger.getLogger(UserService.class);
    private static ContactService instance;

    private ContactService(UserCRUD usercrud, ContactCRUD concrud, Scanner scanner) {
        this.concrud = concrud;
        this.usercrud = usercrud;
        this.scanner = scanner;
    }

    public static ContactService getInstance(UserCRUD usercrud, ContactCRUD concrud,
        Scanner scanner) {
        if (instance == null) {
            instance = new ContactService(usercrud, concrud, scanner);
            return instance;
        } else {
            return instance;
        }
    }

    private void deleteAll() {
        if (!concrud.readAll().isEmpty()) {
            System.out.println("Do you want to delete all contacts? \nYes | No");
            String answer = scanner.next();
            if (answer.equals("1")) {
                concrud.deleteAll();
                logger.info("All contacts deleted");
                System.out.println("All contacts deleted");
            }
        } else {
            System.out.println("Nothing to delete");
        }
    }

    private void delete() {
        System.out.println("Enter phoneNumber");
        String phone = scanner.next();
        Contact contact;

        if ((contact = concrud.findByNumber(phone)) != null) {
            System.out.println("Do you want to delete " + contact.toString() + "\nYes | No");
            String answer = scanner.next();

            if (answer.equals("1")) {
                concrud.delete(contact);
                logger.info("contact deleted");
                System.out.println("Contact deleted");
            }
        }
    }

    private void readAll() {
        if (concrud.readAll().isEmpty()) {
            System.out.println("Nothing to show");
        } else {
            System.out.println("All contacts: ");
            for (Contact con : concrud.readAll()) {
                System.out.println(con);
            }
        }
    }

    private void updateContact() {
        System.out.println("Enter phoneNumber");

        String phone = scanner.next();
        Contact contact;
        if ((contact = concrud.findByNumber(phone)) != null) {
            System.out.println("Enter firstName,lastName,phoneNumber");
            String newContact = scanner.next();
            if ((contact = concrud.update(newContact, contact)) != null) {
                logger.info("User updated contact " + contact);
                System.out.println("Updated: " + contact);
            }
        }
    }

    private void readOne() {
        System.out.println("Enter phoneNumber");

        if (scanner.hasNext()) {
            String phone = scanner.next();
            Contact contact;
            if ((contact = concrud.findByNumber(phone)) != null) {
                System.out.println(contact);
            }
        }
    }

}
