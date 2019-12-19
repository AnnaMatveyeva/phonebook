package matveyeva.phonebook.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class Contact implements Serializable {

    private static final long serialVersionUID = 4L;

    private String firstName;
    private String lastName;
    private String phoneNumber;
    private LocalDate creationDate;

    public Contact(String firstName, String lastName, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.creationDate = LocalDate.now();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }


    public String toString() {
        return this.firstName + " " + this.lastName + " " + this.phoneNumber + " "
            + this.creationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Contact contact = (Contact) o;
        return Objects.equals(phoneNumber, contact.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(phoneNumber);
    }

    public boolean isTheSame(Contact contact) {
        if (this.getFirstName().equals(contact.getFirstName()) && this.getLastName()
            .equals(contact.getLastName()) && this.getPhoneNumber()
            .equals(contact.getPhoneNumber())) {
            return true;
        } else {
            return false;
        }
    }

}
