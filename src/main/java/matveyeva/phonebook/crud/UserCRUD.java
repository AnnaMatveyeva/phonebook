package matveyeva.phonebook.crud;

import matveyeva.phonebook.Validator;
import matveyeva.phonebook.entity.User;
import matveyeva.phonebook.exception.InvalidUserException;
import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserCRUD {

    private static Set<User> users = new HashSet<User>();
    private static final Logger logger = Logger.getLogger(UserCRUD.class);
    private ContactCRUD contactCRUD;
    private final Validator validator = new Validator();

    public UserCRUD() {

        try {
            loadUsers();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public User createUser(String str) {
        try {
            User user = split(str);
            users.add(user);
            logger.info("New user " + user.getUserName() + " created");
            return user;
        } catch (InvalidUserException ex) {
            System.out.println(ex.getMessage());
            return null;
        } catch (IllegalArgumentException iex) {
            System.out.println("User exists");
            return null;
        }

//        if(user != null && user.isUserValid()){
//            try {
//                if(users.add(user)){
//                    logger.info("New user " + user.getUserName() + " created");
//
//                    return user;
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        return null;
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
    }

    public void deleteAll() throws IOException {
        for(User user : users) {
            contactCRUD = new ContactCRUD(user);
            contactCRUD.deleteAll();
        }
        contactCRUD.reloadContacts();
        List<User> arr = new ArrayList<User>(users);
        users.removeAll(arr);
    }

    public User update(String newUser, User oldUser) {
        try {
            User user = split(newUser);
            users.remove(oldUser);
            users.add(user);
            return user;
        } catch (InvalidUserException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    public Set<User> findAll() {
        return users;
    }

    public User findByName(String username) {
        for(User user : users) {
            if(user.getUserName().contains(username)) {
                return user;
            }
        }
        return null;
    }

    private void loadUsers() throws Exception {
//        FileInputStream fileInputStream = new FileInputStream("users.ser");
        try(FileInputStream fileInputStream = new FileInputStream("users.ser")) {
            while (fileInputStream.available() > 0) {
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                users = (Set<User>) objectInputStream.readObject();

            }
        }
    }

    public void reloadUsers() throws IOException {
//        FileOutputStream outputStream = new FileOutputStream("users.ser");
        try(FileOutputStream outputStream = new FileOutputStream("users.ser")) {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(users);
        }


    }

    private User split(String str) throws InvalidUserException {
        String[] userstr = str.split(",");
        if(userstr.length == 2) {
            User user = new User(userstr[0], userstr[1]);
            if(!validator.checkPersonData(user.getUserName())) {
                throw new InvalidUserException("Incorrect user name. Name should has from 3 to 15 characters and contains only letters and numerals");
            } else if(!validator.checkPersonData(user.getPassword())) {
                throw new InvalidUserException("Incorrect user password. Password should has from 3 to 15 characters and contains only letters and numerals");
            } else return user;
        } else throw new InvalidUserException("Incorrect input data.Try again");
    }


    public User findOne(String namePass) {
        try {
            User user = split(namePass);
            if(users.contains(user)) {
                return user;
            } else throw new InvalidUserException("User not exists");
        } catch (InvalidUserException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }
}

