package matveyeva.phonebook.crud;

import matveyeva.phonebook.entity.User;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class UserCRUD {

    private static Set<User> users = new HashSet<User>();
    private static Logger logger;
    private ContactCRUD contactCRUD;

    public UserCRUD(){
        logger = Logger.getLogger(this.getClass());
        try {
            loadUsers();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public User createUser(String str){
        User user = split(str);

        if(user != null && user.isUserValid()){
            try {
                if(users.add(user)){
                    logger.info("New user " + user.getUserName() + " created");

                    return user;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void delete(User user) throws IOException {
        contactCRUD = new ContactCRUD(user);
        contactCRUD.deleteAll();
        try {
            contactCRUD.reloadContacts();
        } catch (IOException e) {
            e.printStackTrace();
        }
        users.remove(user);
        reloadUsers();
    }

    public void deleteAll() throws IOException {
        for(User user: users){
            contactCRUD = new ContactCRUD(user);
            contactCRUD.deleteAll();
        }
        contactCRUD.reloadContacts();
        ArrayList<User> arr = new ArrayList<User>(users);
        users.removeAll(arr);
        reloadUsers();
    }

    public User update(String newUser, User oldUser){
        User user = split(newUser);
        if(user.isUserValid()){
            users.remove(oldUser);
            users.add(user);
            return user;
        }
        return null;
    }

    public Set<User> findAll(){
        return users;
    }

    public User findByName(String username){
        for(User user: users){
            if(user.getUserName().contains(username)){
                return user;
            }
        }
        return null;
    }

    private void loadUsers() throws Exception {
        FileInputStream fileInputStream = new FileInputStream("users.ser");
        while (fileInputStream.available() > 0) {
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            User user = (User) objectInputStream.readObject();
            users.add(user);
        }
    }
    public void reloadUsers() throws IOException {
        FileOutputStream outputStream = new FileOutputStream("users.ser");
        for(User user : users){
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(user);
        }
        outputStream.close();

    }

    private User split(String str){
        String[] userstr = str.split(",");
        if(userstr.length == 2){
            User user = new User(userstr[0], userstr[1]);
            return user;
        }
        return null;
    }
    private void writeUserToFile(User user) throws IOException{
        FileOutputStream outputStream = new FileOutputStream("users.ser",true);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject(user);
        objectOutputStream.close();
        outputStream.close();
    }

    public User findOne(String namePass) {
        User user = split(namePass);
        if(user != null && user.isUserValid() && users.contains(user)){
            return user;
        }
        return null;
    }
}

