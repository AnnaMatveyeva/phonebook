package matveyeva.phonebook;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import matveyeva.phonebook.entity.User;

public class Validator {

    private final Pattern patternForNames = Pattern.compile("^[a-zA-Z0-9-]{3,15}$");
    private final Pattern patternForPhone = Pattern.compile("\\+375\\d{9}");

    public boolean checkPhone(String phone) {
        Matcher matcher = patternForPhone.matcher(phone);
        return matcher.matches();
    }

    public boolean checkPersonData(String name) {
        Matcher matcher = patternForNames.matcher(name);
        return matcher.matches();
    }

    public boolean checkUser(User user) {
        Matcher name = patternForNames.matcher(user.getUserName());
        Matcher pass = patternForNames.matcher(user.getPassword());
        return name.matches() && pass.matches();
    }
}
