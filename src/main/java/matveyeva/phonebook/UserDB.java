package matveyeva.phonebook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import matveyeva.phonebook.entity.Contact;
import matveyeva.phonebook.entity.User;

public enum UserDB {
    INSTANCE;

    public Map<User, Set<Contact>> usersContacts = new HashMap<>();

    UserDB() {
        try {
            loadUsers();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadUsers() throws Exception {
        Set<User> users = new HashSet<>();
        try (FileInputStream fileInputStream = new FileInputStream("users.ser")) {
            while (fileInputStream.available() > 0) {
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                users = (Set<User>) objectInputStream.readObject();
            }
        }

        for (User user : users) {
            usersContacts.put(user, user.getContacts());
        }
    }

    public void save() throws IOException {
        Set<User> users = new HashSet<>();
        users.addAll(usersContacts.keySet());

        try (FileOutputStream outputStream = new FileOutputStream("users.ser")) {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(users);
        }
    }


}
