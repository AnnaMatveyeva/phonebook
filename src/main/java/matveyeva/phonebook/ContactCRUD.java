package matveyeva.phonebook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ContactCRUD {
    private List<Contact> contacts;
    private final Scanner scanner = new Scanner(System.in);

    public ContactCRUD(){
        try {
            loadContacts();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Contact readOne(){

        return null;
    }

    public Contact update(){
        return null;
    }

    public Contact create(String str){
        String[] cont = str.split("|");
        Contact contact = new Contact(cont[0], cont[1], cont[2]);
        if(contact.isContactValid()){
            try {
                writeContactToFile(contact);
                contacts.add(contact);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return contact;
    }

    public void delete(){
    }

    public List<Contact> readAll(){

        return contacts;
    }

    private void loadContacts() throws Exception {
        contacts = new ArrayList<Contact>();
        FileInputStream fileInputStream = new FileInputStream("contacts.ser");
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        while (fileInputStream.available() > 0) {
            Contact contact = (Contact) objectInputStream.readObject();
            contacts.add(contact);
        }
    }

    private void writeContactToFile(Contact contact) throws Exception{
        FileOutputStream outputStream = new FileOutputStream("contacts.ser",true);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject(contact);
        objectOutputStream.close();
        outputStream.close();
    }

}

