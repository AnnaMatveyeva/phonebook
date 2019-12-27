package matveyeva.phonebook.service;

import java.util.Scanner;
import matveyeva.phonebook.crud.ContactCRUD;
import matveyeva.phonebook.entity.Contact;
import matveyeva.phonebook.entity.User;
import matveyeva.phonebook.exception.InvalidContactException;
import org.apache.log4j.Logger;

public enum ContactService {
    INSTANCE;

    private ContactCRUD concrud = ContactCRUD.INSTANCE;
    private static final Logger logger = Logger.getLogger(UserService.class);
    private static final Scanner scanner = new Scanner(System.in);

    public void deleteAll(User user) {
        if (!concrud.readAll(user).isEmpty()) {
            System.out.println("Do you want to delete all contacts? \nYes | No");
            String answer = scanner.next();
            if (answer.equals("1")) {
                concrud.deleteAll(user);
                logger.info("All contacts deleted");
                System.out.println("All contacts deleted");
            }
        } else {
            System.out.println("Nothing to delete");
        }
    }

    public void delete(User user) {
        System.out.println("Enter phoneNumber");
        String phone = scanner.next();
        Contact contact;

        try {
            contact = concrud.findByNumber(phone, user);
            System.out.println("Do you want to delete " + contact.toString() + "\nYes | No");
            String answer = scanner.next();

            if (answer.equals("1")) {
                concrud.delete(contact, user);
                logger.info("contact deleted");
                System.out.println("Contact deleted");
            }
        } catch (InvalidContactException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void readAll(User user) {
        if (concrud.readAll(user).isEmpty()) {
            System.out.println("Nothing to show");
        } else {
            System.out.println("All contacts: ");
            for (Contact con : concrud.readAll(user)) {
                System.out.println(con);
            }
        }
    }

    public void updateContact(User user) {
        System.out.println("Enter phoneNumber");

        String phone = scanner.next();
        Contact contact;
        try {
            contact = concrud.findByNumber(phone, user);
            System.out.println("Enter firstName,lastName,phoneNumber");
            String newContact = scanner.next();
            contact = concrud.update(newContact, contact, user);
            logger.info("User updated contact " + contact);
            System.out.println("Updated: " + contact);

        } catch (InvalidContactException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void readOne(User user) {
        System.out.println("Enter phoneNumber");
        try {
            if (scanner.hasNext()) {
                String phone = scanner.next();
                Contact contact;
                contact = concrud.findByNumber(phone, user);
                System.out.println(contact);
            }
        } catch (InvalidContactException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void createContact(User user) {
        System.out.println("Enter firstName,lastName,phoneNumber");
        if (scanner.hasNext()) {
            String str = scanner.next();
            Contact con;
            try {
                con = concrud.create(str, user);
                logger.info("User " + user.getUserName() + " created new contact");
                System.out.println("Created: " + con);
            } catch (InvalidContactException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

}
