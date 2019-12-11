package matveyeva.phonebook;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class Contact implements Serializable {

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
    public boolean isContactValid(){
        return true;
    }
    //check phoneNumber
    public boolean isPhoneValid(){
        return true;
    }

    public String toString(){
        return this.firstName + " " + this.lastName + " " +this.phoneNumber + " " + this.creationDate;
    }
}
