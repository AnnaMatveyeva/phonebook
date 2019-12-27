package matveyeva.phonebook.entity;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    private String userName;
    private String password;
    private Set<Contact> contacts;

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public Set<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(Set<Contact> contacts) {
        this.contacts = contacts;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return this.userName.equals(user.getUserName()) && this.password.equals(user.getPassword());
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, password);
    }
}
