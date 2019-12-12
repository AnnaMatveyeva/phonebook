package matveyeva.phonebook.entity;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.Match;
import matveyeva.phonebook.exception.InvalidContactException;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Contact implements Serializable{

    private static final long serialVersionUID = 4L;

    private String firstName;
    private String lastName;
    private String phoneNumber;
    private LocalDate creationDate;
    private User user;

    public Contact(String firstName, String lastName, String phoneNumber,User user) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.user = user;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    //check
    public boolean isContactValid() throws InvalidContactException {
        return isPhoneNumberValid() && isUserNameValid();
    }

    private boolean isPhoneNumberValid() throws InvalidContactException {
        Pattern pattern = Pattern.compile("\\+375\\d{9}");
        Matcher matcher = pattern.matcher(phoneNumber);
        if(matcher.matches()) {
            System.out.println("Phone number is valid");
            return true;
        }
        else throw new InvalidContactException("Phone number is not correct");
    }
    private boolean isUserNameValid() throws InvalidContactException {
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9-]{3,15}$");
        Matcher matcherF = pattern.matcher(firstName);
        Matcher matcherL = pattern.matcher(lastName);
        if(matcherF.matches() && matcherL.matches()) return true;
        else throw new InvalidContactException("User name is not correct");
    }


    public String toString(){
        return this.firstName + " " + this.lastName + " " +this.phoneNumber + " " + this.creationDate;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        Contact contact = (Contact) o;
        return Objects.equals(phoneNumber, contact.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(phoneNumber);
    }
    public boolean isTheSame(Contact contact){
        if(this.getFirstName().equals(contact.getFirstName()) && this.getLastName().equals(contact.getLastName()) && this.getPhoneNumber().equals(contact.getPhoneNumber())){
            return true;
        }else return false;
    }

}
