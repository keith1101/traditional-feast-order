/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dispatcher;
import model.*;
import tools.*;
import business.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 *
 * @author LENOVO
 */
public class Menu {
    void displayMenu() {
        System.out.println("Menu");
        System.out.println("1. Register customers.");
        System.out.println("2. Update customer information.");
        System.out.println("3. Search for customer information by name.");
        System.out.println("4. Display feast menus.");
        System.out.println("5. Place a feast order.");
        System.out.println("6. Update order information.");
        System.out.println("7. Save data to file.");
        System.out.println("8. Display Customer or Order lists.");
        System.out.println("9. Quit.");
    }
    
    Customers listCustomer = new Customers();
    SetMenus listFeast = new SetMenus();
    Orders listOrder = new Orders();

    public void showMenu() {
        do {
            displayMenu();
            int choice = 0;
            do {
                choice = Inputter.getInt("Choose an option (1-9):");
                if (!(choice >= 1 && choice <= 9)) {
                    System.out.println("Invalid choice!");
                }
            } while (choice < 1 || choice > 9);
            
            switch(choice) {
                case 1: 
                    registerCustomer();
                    break;
                case 2:
                    updateCustomerInformation();
                    break;
                case 3:
                    searchForCustomerInformationByName();
                    break;
                case 4:
                    displayFeastMenu();
                    break;
                case 5: {
                    placeAFeastOrder();
                    break;
                }
                case 6: {
                    updateOrderInformation();
                    break;
                }
                case 7:
                    saveDataToFile();
                    break;
                case 8:
                    displayCustomerOrderList();
                    break;
                default:
                    return;
            }
            
        } while (true);
    }
    
    void registerCustomer() {
        Customer customer;
        do {
            String customerId = "";
            do {
                customerId = Inputter.inputAndLoop("\nEnter Customer Code:", "Invalid customer code! Must start with C, G, or K followed by 4 digits.", Acceptable.CUSTOMER_ID_VALID);
                if (listCustomer.searchById(customerId) != null) {
                    System.out.println("Customer Id exists!");
                }
            } while(listCustomer.searchById(customerId) != null);

            String customerName = Inputter.inputAndLoop("Enter Customer Name:", "Invalid name! Must be 2-25 alphabetic characters.", Acceptable.NAME_VALID);
            String customerPhone = Inputter.inputAndLoop("Enter Customer Phone Number:", "Invalid phone number! Must start with 09 followed by 8 digits.", Acceptable.PHONE_VALID);
            String customerEmail = Inputter.inputAndLoop("Enter Customer Email:", "Invalid email format! Valid: example@gmail.com.", Acceptable.EMAIL_VALID);

            customer = new Customer(customerId, customerName, customerPhone, customerEmail);
            listCustomer.addNew(customer);

            String returnMenu = Inputter.inputAndLoop("Do you want to continue entering new customers? (Y/N)", "Invalid choice! Please enter Y or N.", Acceptable.YES_NO_VALID);

            if (returnMenu.equalsIgnoreCase("N"))
                break;
        } while (true);
    }
    
    void updateCustomerInformation() {
        Customer customer;
        while(true) {
            String customerId;
            do {
                customerId = Inputter.inputAndLoop("Enter Customer Code to update:", "Invalid customer code! Must start with C, G, or K followed by 4 digits.", Acceptable.CUSTOMER_ID_VALID);
                if (listCustomer.searchById(customerId) == null) {
                    System.out.println("This customer does not exist");
                }
            } while (listCustomer.searchById(customerId) == null);

            customer = listCustomer.searchById(customerId);

            String customerName = Inputter.inputAndLoop("Enter updated name (Press 'Enter' to keep the old value):", "Invalid name! Must be 2-25 alphabetic characters or press Enter to skip.", "(" + Acceptable.NAME_VALID + "|^$)");
            if (customerName != null && !customerName.isEmpty()) {
                customer.setName(customerName);
            }
            String customerPhone = Inputter.inputAndLoop("Enter updated phone number (Press 'Enter' to keep the old value):", "Invalid phone number! Must start with 09 followed by 8 digits or press Enter to skip.", "(" + Acceptable.PHONE_VALID + "|^$)");
            if (customerPhone != null && !customerPhone.isEmpty()) {
                customer.setPhone(customerPhone);
            }
            
            String customerEmail = Inputter.inputAndLoop("Enter updated email (Press 'Enter' to keep the old value):", "Invalid email format or press Enter to skip!", "(" + Acceptable.EMAIL_VALID + "|^$)");
            if (customerEmail != null && !customerEmail.isEmpty()) {
                customer.setEmail(customerEmail);
            }
            
            listCustomer.update(customer);
            System.out.println("Customer information updated successfully!");

            String returnMenu = Inputter.inputAndLoop("Do you want to continue with another update? (Y/N):", "Invalid choice! Please enter Y or N.", Acceptable.YES_NO_VALID);
            if (returnMenu.equalsIgnoreCase("N")) {
                break;
            }
        }
    }
    
    void searchForCustomerInformationByName() {
        Inputter.searchCustomerByName(listCustomer);
    }
    
    void displayFeastMenu() {
        listFeast.showMenuList();
    }
    
    void placeAFeastOrder() {
        Inputter.placeFeastOrder(listCustomer, listFeast, listOrder);
    }
    
    void updateOrderInformation() {
        Order order;
        while (true) {
            String orderId;
            do {
                orderId = Inputter.getString("Enter order id:");
                if (listOrder.searchById(orderId) == null) {
                    System.out.println("This Order does not exist.");
                }
            }
            while (listOrder.searchById(orderId) == null);

            order = listOrder.searchById(orderId);
            
            String codeOfSetMenu;
            do {
                codeOfSetMenu = Inputter.getString("Enter the menu code to update (Press 'Enter' to keep the old value):");
                if (codeOfSetMenu != null && listOrder.getListSetMenu().isValidMenuId(codeOfSetMenu) == null) {
                    System.out.println("Code menu does not exists!");
                }
            } while (listOrder.getListSetMenu().isValidMenuId(codeOfSetMenu) == null && codeOfSetMenu != null && codeOfSetMenu.isEmpty());
            if (codeOfSetMenu != null && !codeOfSetMenu.isEmpty()) {
                order.setMenuId(codeOfSetMenu);
            }

            String numOfTable;
            do {
                numOfTable = Inputter.inputAndLoop("Enter number of table to update (Press 'Enter' to keep the old value):", "Invalid number! Must be a positive integer or press Enter to skip.", "(" + Acceptable.INTEGER_VALID + "|^$)");
                if (!numOfTable.isEmpty() && numOfTable != null && Integer.parseInt(numOfTable) <= 0) {
                    System.out.println("Number of tables need to be greater than 0.");
                }
            } while (!numOfTable.isEmpty() && numOfTable != null && Integer.parseInt(numOfTable) <= 0);
            if (!numOfTable.isEmpty() && numOfTable != null) {
                order.setNumOfTables(Integer.parseInt(numOfTable));
            }

            Date dateObj = null;
            SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy");
            do {
                dateObj = null;
                try {
                    String dateObjString = Inputter.getString("Enter date to update (Press 'Enter' to keep the old value):");
                    if (dateObjString.isEmpty() || dateObjString == null) {
                        break;
                    }
                    dateObj = formatDate.parse(dateObjString);
                    if (dateObj != null && new Date().after(dateObj)) {
                        System.out.println("The preferred date needs to be in the future!");
                        dateObj = null;
                    }
                } catch (ParseException e) {
                    System.out.println("Wrong date format!");
                }

            } while (dateObj == null || new Date().after(dateObj));

            if (dateObj != null) {
                order.setEventDate(dateObj);
            }
            
            listOrder.update(order);
            String returnMenu = Inputter.inputAndLoop("Do you want to continue with another update? (Y/N):", "Invalid choice! Please enter Y or N.", Acceptable.YES_NO_VALID);

            if (returnMenu.equalsIgnoreCase("N")) {
                break;
            }
        }
    }
    
    void saveDataToFile() {
        String saveChoice = Inputter.inputAndLoop("Which do you want to save? (Choose 1-2):\n1. Customers\n2. Order List\nChoose:", "Invalid choice! Please enter 1 or 2.", "^(1|2)$");
        if (saveChoice.equals("1")) {
            listCustomer.saveToFile();
        } else {
            listOrder.saveToFile();
        }
    }
    
    void displayCustomerOrderList() {
        String displaySavedInfo = Inputter.inputAndLoop("What do you want to show? (Choose 1-2)\n1. Customers\n2. Order List\nChoose:", "Invalid choice! Please enter 1 or 2.", "^(1|2)$");

        if (displaySavedInfo.equals("1")) {
            listCustomer.showAll();
        } else {
            listOrder.showAll();
        }
    }
    
}
