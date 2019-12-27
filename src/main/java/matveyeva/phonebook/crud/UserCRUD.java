package matveyeva.phonebook.crud;

import java.io.IOException;
import java.util.HashSet;
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

    public User createUser(String str) throws InvalidUserException {
        User user = split(str);
        user.setContacts(new HashSet<>());
        int size = UserDB.INSTANCE.usersContacts.size();
        UserDB.INSTANCE.usersContacts.put(user, user.getContacts());
        if (size < UserDB.INSTANCE.usersContacts.size()) {
            logger.info("New user " + user.getUserName() + " created");
            return user;
        } else {
            throw new InvalidUserException("User exists");
        }
    }

    public void delete(User user) throws IOException {
        UserDB.INSTANCE.usersContacts.remove(user);
    }

    public void deleteAll() throws IOException {
        UserDB.INSTANCE.usersContacts.clear();
    }

    public User update(String newUser, User oldUser) throws InvalidUserException {
        User user = split(newUser);
        user.setContacts(oldUser.getContacts());

        if (!UserDB.INSTANCE.usersContacts.containsKey(user)) {
            UserDB.INSTANCE.usersContacts.remove(oldUser);
            UserDB.INSTANCE.usersContacts.put(user, user.getContacts());
            return user;
        } else {
            throw new InvalidUserException("User exists");
        }

    }

    public Set<User> findAll() {
        return UserDB.INSTANCE.usersContacts.keySet();
    }

    public User findByName(String username) throws InvalidUserException {
        for (User user : UserDB.INSTANCE.usersContacts.keySet()) {
            if (user.getUserName().contains(username)) {
                return user;
            }
        }
        throw new InvalidUserException("User not found");
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

    public User findOne(String namePass) throws InvalidUserException {
        User user = split(namePass);
        for (User u : UserDB.INSTANCE.usersContacts.keySet()) {
            if (u.equals(user)) {
                user.setContacts(u.getContacts());
                return user;
            }
        }
        throw new InvalidUserException("User not exists");
    }
}

