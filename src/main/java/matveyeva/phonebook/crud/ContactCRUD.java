package matveyeva.phonebook.crud;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import matveyeva.phonebook.UserDB;
import matveyeva.phonebook.Validator;
import matveyeva.phonebook.entity.Contact;
import matveyeva.phonebook.entity.User;
import matveyeva.phonebook.exception.InvalidContactException;

public enum ContactCRUD {
    INSTANCE;

    private final Validator validator = new Validator();

    public Contact findByNumber(String phone, User user) {
        for (Contact con : UserDB.INSTANCE.usersContacts.get(user)) {
            if (con.getPhoneNumber().contains(phone)) {
                return con;
            }
        }
        return null;
    }

    public Contact update(String newContact, Contact oldContact, User user) {
        try {
            Contact upContact = split(newContact);
            UserDB.INSTANCE.usersContacts.get(user).remove(oldContact);
            UserDB.INSTANCE.usersContacts.get(user).add(upContact);
            return upContact;
        } catch (InvalidContactException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    public Contact create(String str, User user) {
        try {
            Contact contact = split(str);
            UserDB.INSTANCE.usersContacts.get(user).add(contact);
            return contact;
        } catch (InvalidContactException ex) {
            System.out.println(ex.getMessage());
            return null;
        } catch (IllegalArgumentException iex) {
            System.out.println("Contact exists");
            return null;
        }
    }

    public void delete(Contact contact, User user) {
        UserDB.INSTANCE.usersContacts.get(user).remove(contact);
    }

    public Set<Contact> readAll(User user) {
        return UserDB.INSTANCE.usersContacts.get(user);
    }

    public void deleteAll(User user) {
        List<Contact> arr = new ArrayList<Contact>(UserDB.INSTANCE.usersContacts.get(user));
        UserDB.INSTANCE.usersContacts.get(user).removeAll(arr);
    }

    private Contact split(String str) throws InvalidContactException {

        String[] cont = str.split(",");
        Contact contact;
        if (cont.length == 3) {
            contact = new Contact(cont[0], cont[1], cont[2]);
            if (!validator.checkPersonData(contact.getFirstName())) {
                throw new InvalidContactException(
                    "Incorrect first name.First name should has from 3 to 15 characters and contains only letters and numerals");
            } else if (!validator.checkPersonData(contact.getLastName())) {
                throw new InvalidContactException(
                    "Incorrect last name.Last name should has from 3 to 15 characters and contains only letters and numerals");
            } else if (!validator.checkPhone(contact.getPhoneNumber())) {
                throw new InvalidContactException(
                    "Incorrect phone number. Phone number should starts with +375 and has 12 numerals");
            } else {
                return contact;
            }
        } else {
            throw new InvalidContactException("Incorrect contact data.Try again");
        }
    }
}

