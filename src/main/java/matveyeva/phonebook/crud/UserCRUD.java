package matveyeva.phonebook.crud;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import matveyeva.phonebook.UserDB;
import matveyeva.phonebook.Validator;
import matveyeva.phonebook.entity.User;
import matveyeva.phonebook.exception.InvalidUserException;
import org.apache.log4j.Logger;

public enum UserCRUD {
    INSTANCE;

    private static final Logger logger = Logger.getLogger(UserCRUD.class);
    private final Validator validator = new Validator();

    public Set<User> getUsers() {
        return UserDB.INSTANCE.usersContacts.keySet();
    }

    public User createUser(String str) {
        try {
            User user = split(str);
            user.setContacts(new HashSet<>());
            if (UserDB.INSTANCE.usersContacts.keySet().add(user)) {
                logger.info("New user " + user.getUserName() + " created");
                return user;
            } else {
                throw new InvalidUserException("User exists");
            }
        } catch (InvalidUserException ex) {
            System.out.println(ex.getMessage());
            return null;
        } catch (IllegalArgumentException iex) {
            System.out.println("User exists");
            return null;
        }

    }

    public void delete(User user) throws IOException {
        UserDB.INSTANCE.usersContacts.keySet().remove(user);
    }

    public void deleteAll() throws IOException {
        List<User> arr = new ArrayList<User>(UserDB.INSTANCE.usersContacts.keySet());
        UserDB.INSTANCE.usersContacts.keySet().removeAll(arr);
    }

    public User update(String newUser, User oldUser) {
        try {
            User user = split(newUser);
            user.setContacts(oldUser.getContacts());
            UserDB.INSTANCE.usersContacts.keySet().remove(oldUser);
            UserDB.INSTANCE.usersContacts.keySet().add(user);

            return user;
        } catch (InvalidUserException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    public Set<User> findAll() {
        return UserDB.INSTANCE.usersContacts.keySet();
    }

    public User findByName(String username) {
        for (User user : UserDB.INSTANCE.usersContacts.keySet()) {
            if (user.getUserName().contains(username)) {
                return user;
            }
        }
        return null;
    }

    public void reloadUsers() throws IOException {
        UserDB.INSTANCE.save();
    }

    private User split(String str) throws InvalidUserException {
        String[] userstr = str.split(",");
        if (userstr.length == 2) {
            User user = new User(userstr[0], userstr[1]);
            if (!validator.checkPersonData(user.getUserName())) {
                throw new InvalidUserException(
                    "Incorrect user name. Name should has from 3 to 15 characters and contains only letters and numerals");
            } else if (!validator.checkPersonData(user.getPassword())) {
                throw new InvalidUserException(
                    "Incorrect user password. Password should has from 3 to 15 characters and contains only letters and numerals");
            } else {
                return user;
            }
        } else {
            throw new InvalidUserException("Incorrect input data.Try again");
        }
    }

    public User findOne(String namePass) {
        try {
            User user = split(namePass);
            for (User u : UserDB.INSTANCE.usersContacts.keySet()) {
                if (u.equals(user)) {
                    user.setContacts(u.getContacts());
                    return user;
                }
            }
            throw new InvalidUserException("User not exists");
        } catch (InvalidUserException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }
}

