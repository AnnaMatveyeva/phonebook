package matveyeva.phonebook.crud;

import matveyeva.phonebook.Validator;
import matveyeva.phonebook.entity.Contact;
import matveyeva.phonebook.entity.User;
import matveyeva.phonebook.exception.InvalidContactException;
import matveyeva.phonebook.exception.InvalidUserException;

import java.io.*;
import java.util.*;

public class ContactCRUD {
    private Set<Contact> allContacts;
    private Set<Contact> userContacts;
    private User user;
    private final Validator validator = new Validator();

    public ContactCRUD(User user){
        this.user = user;
        try {
            loadContacts();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Contact findByNumber(String phone){
        for(Contact con : userContacts){
            if(con.getPhoneNumber().contains(phone)){
                return con;
            }
        }
        return null;
    }

    public Contact update(String newContact, Contact oldContact){
        try{
            Contact upContact = split(newContact);
            userContacts.remove(oldContact);
            userContacts.add(upContact);
            return upContact;

        }catch (InvalidContactException ex){
            System.out.println(ex.getMessage());
            return null;
        }

    }

    public Contact create(String str){
        try{
            Contact contact = split(str);
            userContacts.add(contact);
            return contact;
        }catch (InvalidContactException ex){
            System.out.println(ex.getMessage());
            return null;
        }catch (IllegalArgumentException iex){
            System.out.println("Contact exists");
            return null;
        }

//
//        if(contact != null && contact.isContactValid()){
//            try {
//                if(userContacts.add(contact)){
////                    writeContactToFile(contact);
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }

    }

    public void delete(Contact contact){
        userContacts.remove(contact);
    }

    public Set<Contact> readAll(){
        return userContacts;
    }

    private void loadContacts() throws Exception {
        allContacts = new HashSet<Contact>();
        userContacts = new HashSet<Contact>();
        FileInputStream fileInputStream = new FileInputStream("contacts.ser");
        while (fileInputStream.available() > 0) {
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            allContacts = (Set<Contact>) objectInputStream.readObject();
        }

        for(Contact con : allContacts){
            if(con.getUser().equals(this.user)){
                userContacts.add(con);
            }
        }
        allContacts.removeAll(userContacts);
    }

    public void reloadContacts() throws IOException {
//        FileOutputStream outputStream = new FileOutputStream("contacts.ser");
        try(FileOutputStream outputStream = new FileOutputStream("contacts.ser")){
            allContacts.addAll(userContacts);
            ObjectOutputStream oos = new ObjectOutputStream(outputStream);
            oos.writeObject(allContacts);
//            for(Contact cont: allContacts){
//                ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
//                objectOutputStream.writeObject(cont);
//            }
        }
    }

    public void deleteAll() {
        List<Contact> arr = new ArrayList<Contact>(userContacts);
        userContacts.removeAll(arr);
    }

    private Contact split(String str) throws InvalidContactException {

        String[] cont = str.split(",");
        Contact contact;
        if(cont.length == 3){
            contact = new Contact(cont[0], cont[1], cont[2],this.user);
            if(!validator.checkPersonData(contact.getFirstName())){
                throw new InvalidContactException("Incorrect first name.First name should has from 3 to 15 characters and contains only letters and numerals");
            }else if(!validator.checkPersonData(contact.getLastName())){
                throw new InvalidContactException("Incorrect last name.Last name should has from 3 to 15 characters and contains only letters and numerals");
            } else if(!validator.checkPhone(contact.getPhoneNumber())) {
                throw new InvalidContactException("Incorrect phone number. Phone number should starts with +375 and has 12 numerals");
            } else return contact;
        }else throw new InvalidContactException("Incorrect contact data.Try again");
    }
}

