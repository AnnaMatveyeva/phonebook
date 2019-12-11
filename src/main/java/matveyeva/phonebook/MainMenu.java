package matveyeva.phonebook;

import java.util.Scanner;

public class MainMenu {
    private final Scanner scanner = new Scanner(System.in);
    private final ContactCRUD crud = new ContactCRUD();
    public void showMenu() {
        boolean check = false;
        while(!check) {
            System.out.println("show contact| add contact | update contact | delete contact | show all");
            switch (scanner.nextInt()) {
                case 1:
                    System.out.println("show contact");
                    check = true;
                    break;
                case 2:
                    createContact();
                    check = true;
                    break;
                case 3:
                    System.out.println("update contact");
                    check = true;
                    break;
                case 4:
                    System.out.println("delete contact");
                    check = true;
                    break;
                case 5:
                    readAll();
                    check = true;
                    break;
            }
        }
    }

    private void createContact(){
        System.out.println("Enter firstName|lastName|phoneNumber");
        String str = scanner.nextLine();
        crud.create(str);
        System.out.println("Created: " + crud.create(str));
        showMenu();
    }

    private void readAll(){
        System.out.println("All contacts: ");
        for(Contact con : crud.readAll()){
            System.out.println(con.toString());
        }
        showMenu();
    }
}
