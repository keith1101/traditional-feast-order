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
                customerId = Inputter.inputAndLoop("\nEnter Customer Code:", Acceptable.CUSTOMER_ID_VALID);
                if (listCustomer.searchById(customerId) != null) {
                    System.out.println("Customer Id exists!");
                }
            } while(listCustomer.searchById(customerId) != null);

            String customerName = Inputter.inputAndLoop("Enter Customer Name:", Acceptable.NAME_VALID);
            String customerPhone = Inputter.inputAndLoop("Enter Customer Phone Number:", Acceptable.PHONE_VALID);
            String customerEmail = Inputter.inputAndLoop("Enter Customer Email:", Acceptable.EMAIL_VALID);

            customer = new Customer(customerId, customerName, customerPhone, customerEmail);
            listCustomer.addNew(customer);

            String returnMenu = Inputter.inputAndLoop("Do you want to continue entering new customers? (Y/N)", Acceptable.YES_NO_VALID);

            if (returnMenu.equalsIgnoreCase("N"))
                break;
        } while (true);
    }
    
    void updateCustomerInformation() {
        Customer customer;
        while(true) {
            String customerId;
            do {
                customerId = Inputter.inputAndLoop("Enter Customer Code to update:", Acceptable.CUSTOMER_ID_VALID);
                if (listCustomer.searchById(customerId) == null) {
                    System.out.println("This customer does not exist");
                }
            } while (listCustomer.searchById(customerId) == null);

            customer = listCustomer.searchById(customerId);

            String updateChoice = Inputter.inputAndLoop("Which do you want to update? (Choose 1-3)\n1.Name\n2.Phone\n3.Email\nChoose:", "^[1-3]$");

            switch (updateChoice) {
                case "1":
                    String customerName = Inputter.inputAndLoop("Enter updated name:", Acceptable.NAME_VALID);
                    customer.setName(customerName);
                    break;
                case "2":
                    String customerPhone = Inputter.inputAndLoop("Enter updated phone number:", Acceptable.PHONE_VALID);
                    customer.setPhone(customerPhone);
                    break;
                case "3":
                    String customerEmail = Inputter.inputAndLoop("Enter updated email:", Acceptable.EMAIL_VALID);
                    customer.setEmail(customerEmail);
                    break;
            }
            listCustomer.update(customer);
            System.out.println("Customer information updated successfully!");

            String returnMenu = Inputter.inputAndLoop("Do you want to continue with another update? (Y/N):", Acceptable.YES_NO_VALID);
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

            String updateChoice = Inputter.inputAndLoop("Which do you want to update? (Choose 1-3)\n1. Code of set menu\n2. Number of table\n3. Preferred event date\nChoose:", "^[1-3]$");

            switch (updateChoice) {
                case "1":
                    String codeOfSetMenu;
                    do {
                        codeOfSetMenu = Inputter.getString("Enter the menu code to update:");
                        if (listOrder.listSetMenu.isValidMenuId(codeOfSetMenu) == null) {
                            System.out.println("Code menu does not exists!");
                        }
                    } while (listOrder.listSetMenu.isValidMenuId(codeOfSetMenu) == null);
                    order.setMenuId(codeOfSetMenu);
                    break;
                case "2":
                    int numOfTable;
                    do {
                        numOfTable = Inputter.getInt("Enter number of table to update:");
                        if (numOfTable <= 0) {
                            System.out.println("Number of tables need to be greater than 0.");
                        }
                    } while (numOfTable <= 0);
                    order.setNumOfTables(numOfTable);
                    break;
                case "3":
                    Date dateObj = null;
                    SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy");
                    do {
                        try {
                            dateObj = formatDate.parse(Inputter.getString("Enter date to update:"));
                            if (dateObj != null && new Date().after(dateObj)) {
                                System.out.println("The preferred date needs to be in the future!");
                                dateObj = null;
                            }
                        } catch (ParseException e) {
                            System.out.println("Wrong date format!");
                        }

                    } while (dateObj == null || new Date().after(dateObj));

                    order.setEventDate(dateObj);
                    break;
            }
            listOrder.update(order);
            String returnMenu = Inputter.inputAndLoop("Do you want to continue with another update? (Y/N):", Acceptable.YES_NO_VALID);

            if (returnMenu.equalsIgnoreCase("N")) {
                break;
            }
        }
    }
    
    void saveDataToFile() {
        String saveChoice = Inputter.inputAndLoop("Which do you want to save? (Choose 1-2):\n1. Customers\n2. Order List\nChoose:", "^(1|2)");
        if (saveChoice.equals("1")) {
            listCustomer.saveToFile();
        } else {
            listOrder.saveToFile();
        }
    }
    
    void displayCustomerOrderList() {
        String displaySavedInfo = Inputter.inputAndLoop("What do you want to show? (Choose 1-2)\n1. Customers\n2. Order List\nChoose:","^(1|2)$");

        if (displaySavedInfo.equals("1")) {
            listCustomer.showAll();
        } else {
            listOrder.showAll();
        }
    }
    
}
