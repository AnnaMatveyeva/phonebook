package matveyeva.phonebook;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class Application {
    private final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws Exception {
        Contact con = new Contact("egor","Kleumonov","+375446820633");

        FileOutputStream outputStream = new FileOutputStream("contacts.ser");
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject(con);
        objectOutputStream.close();
        outputStream.close();
        Application app = new Application();
        app.start();
    }

    public void start(){
        boolean check = false;
        while(!check) {
            System.out.println("login | registration");
            switch (scanner.nextInt()) {
                case 1:
                    MainMenu mainMenu = new MainMenu();
                    mainMenu.showMenu();
                    check = true;
                    break;
                case 2:
                    System.out.println("registration");
                    check = true;
                    break;
            }
        }

    }
}
