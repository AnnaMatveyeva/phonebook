package matveyeva.phonebook.crud;

import java.util.Set;
import matveyeva.phonebook.UserDB;
import matveyeva.phonebook.Validator;
import matveyeva.phonebook.entity.Contact;
import matveyeva.phonebook.entity.User;
import matveyeva.phonebook.exception.InvalidContactException;

public enum ContactCRUD {
    INSTANCE;

    private final Validator validator = new Validator();

    public Contact findByNumber(String phone, User user) throws InvalidContactException {
        for (Contact con : UserDB.INSTANCE.usersContacts.get(user)) {
            if (con.getPhoneNumber().contains(phone)) {
                return con;
            }
        }
        throw new InvalidContactException("Contact not found");
    }

    public Contact update(String newContact, Contact oldContact, User user)
        throws InvalidContactException {

        Contact upContact = split(newContact);
        if (UserDB.INSTANCE.usersContacts.get(user).contains(oldContact)) {
            for (Contact con : UserDB.INSTANCE.usersContacts.get(user)) {
                if (con.equals(oldContact)) {
                    con.setFirstName(upContact.getFirstName());
                    con.setLastName(upContact.getLastName());
                    con.setPhoneNumber(upContact.getPhoneNumber());
                }
            }
        } else {
            throw new InvalidContactException("Contact not found");
        }
        return upContact;

    }

    public Contact create(String str, User user) throws InvalidContactException {
        try {
            Contact contact = split(str);
            UserDB.INSTANCE.usersContacts.get(user).add(contact);
            return contact;
        } catch (IllegalArgumentException iex) {
            throw new InvalidContactException("Contact exists");
        }
    }

    public void delete(Contact contact, User user) {
        UserDB.INSTANCE.usersContacts.get(user).remove(contact);
    }

    public Set<Contact> readAll(User user) {
        return UserDB.INSTANCE.usersContacts.get(user);
    }

    public void deleteAll(User user) {
        UserDB.INSTANCE.usersContacts.get(user).clear();
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

