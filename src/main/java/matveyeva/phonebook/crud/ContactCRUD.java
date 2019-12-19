package matveyeva.phonebook.crud;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import matveyeva.phonebook.Validator;
import matveyeva.phonebook.entity.Contact;
import matveyeva.phonebook.exception.InvalidContactException;

public class ContactCRUD {

    private Set<Contact> contacts;
    private final Validator validator = new Validator();

    public Contact findByNumber(String phone) {
        for (Contact con : contacts) {
            if (con.getPhoneNumber().contains(phone)) {
                return con;
            }
        }
        return null;
    }

    public Set<Contact> getContacts(){
        return this.contacts;
    }

    public void setContacts(Set<Contact> contactSet){
        this.contacts = contactSet;
    }
    public Contact update(String newContact, Contact oldContact) {
        try {
            Contact upContact = split(newContact);
            contacts.remove(oldContact);
            contacts.add(upContact);
            return upContact;
        } catch (InvalidContactException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    public Contact create(String str) {
        try {
            Contact contact = split(str);
            contacts.add(contact);
            return contact;
        } catch (InvalidContactException ex) {
            System.out.println(ex.getMessage());
            return null;
        } catch (IllegalArgumentException iex) {
            System.out.println("Contact exists");
            return null;
        }
    }

    public void delete(Contact contact) {
        contacts.remove(contact);
    }

    public Set<Contact> readAll() {
        return contacts;
    }

    public void deleteAll() {
        List<Contact> arr = new ArrayList<Contact>(contacts);
        contacts.removeAll(arr);
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

