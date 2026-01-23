package dispatcher;
import model.Customer;
import model.Order;
import tools.Acceptable;
import tools.Inputter;
import business.Customers;
import business.Orders;
import business.SetMenus;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Menu {
    private void displayMenu() {
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
    
    private Customers listCustomer = new Customers();
    private SetMenus listFeast = new SetMenus();
    private Orders listOrder = new Orders();

    public void showMenu() {
        boolean exitMenu = false;
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
                case 9:
                    exitMenu = true;
            }
            
        } while (exitMenu == false);
    }
    
    private void registerCustomer() {
        Customer customer;
        String returnMenu;
        do {
            String customerId = "";
            do {
                customerId = Inputter.inputAndLoop("\nEnter Customer Code:", "Invalid customer code! (Must start with C/G/K followed by 4 digits)", Acceptable.CUSTOMER_ID_VALID);
                if (listCustomer.searchById(customerId) != null) {
                    System.out.println("Customer Id already exists!");
                }
            } while(listCustomer.searchById(customerId) != null);

            String customerName = Inputter.inputAndLoop("Enter Customer Name:", "Invalid name! (2-25 alphabetic characters only)", Acceptable.NAME_VALID);
            String customerPhone = Inputter.inputAndLoop("Enter Customer Phone Number:", "Invalid phone number! (Must start with 090 and have 10 digits)", Acceptable.PHONE_VALID);
            String customerEmail = Inputter.inputAndLoop("Enter Customer Email:", "Invalid email format! (example@domain.com)", Acceptable.EMAIL_VALID);

            customer = new Customer(customerId, customerName, customerPhone, customerEmail);
            listCustomer.addNew(customer);
            System.out.println("Customer registered successfully!");

            returnMenu = Inputter.inputAndLoop("\nDo you want to continue entering new customers? (Y/N):", "Invalid choice! (Y or N only)", Acceptable.YES_NO_VALID);

        } while (returnMenu.equalsIgnoreCase("N") == false);
    }
    
    private void updateCustomerInformation() {
        
        Customer customer;
        String returnMenu;
        do {
            String customerId;
            do {
                customerId = Inputter.inputAndLoop("Enter Customer Code to update:", "Invalid customer code! (Must start with C/G/K followed by 4 digits)", Acceptable.CUSTOMER_ID_VALID);
                if (listCustomer.searchById(customerId) == null) {
                    System.out.println("Customer not found!");
                }
            } while (listCustomer.searchById(customerId) == null);

            customer = listCustomer.searchById(customerId);

            String customerName = Inputter.inputAndLoop("Enter updated name (Press 'Enter' to keep the old value):", "Invalid name! (2-25 alphabetic characters only)", "(" + Acceptable.NAME_VALID + "|^$)");
            if (customerName != null && !customerName.isEmpty()) {
                customer.setName(customerName);
            }
            String customerPhone = Inputter.inputAndLoop("Enter updated phone number (Press 'Enter' to keep the old value):", "Invalid phone number! (Must start with 090 and have 10 digits)", "(" + Acceptable.PHONE_VALID + "|^$)");
            if (customerPhone != null && !customerPhone.isEmpty()) {
                customer.setPhone(customerPhone);
            }
            
            String customerEmail = Inputter.inputAndLoop("Enter updated email (Press 'Enter' to keep the old value):", "Invalid email format! (example@domain.com)", "(" + Acceptable.EMAIL_VALID + "|^$)");
            if (customerEmail != null && !customerEmail.isEmpty()) {
                customer.setEmail(customerEmail);
            }
            
            listCustomer.update(customer);
            System.out.println("Customer information updated successfully!");

            returnMenu = Inputter.inputAndLoop("Do you want to continue with another update? (Y/N):", "Invalid choice! (Y or N only)", Acceptable.YES_NO_VALID);

        } while (returnMenu.equalsIgnoreCase("N") == false);
    }
    
    private void searchForCustomerInformationByName() {
        Inputter.searchCustomerByName(listCustomer);
    }
    
    private void displayFeastMenu() {
        listFeast.showMenuList();
    }
    
    private void placeAFeastOrder() {
        Inputter.placeFeastOrder(listCustomer, listFeast, listOrder);
    }
    
    private void updateOrderInformation() {
        Order order;
        String returnMenu;
        do {
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
                if (codeOfSetMenu != null && !codeOfSetMenu.isEmpty() && listOrder.getListSetMenu().isValidMenuId(codeOfSetMenu) == null) {
                    System.out.println("Menu code does not exist!");
                }
            } while (listOrder.getListSetMenu().isValidMenuId(codeOfSetMenu) == null && codeOfSetMenu != null && !codeOfSetMenu.isEmpty());
            if (codeOfSetMenu != null && !codeOfSetMenu.isEmpty()) {
                order.setMenuId(codeOfSetMenu);
            }

            String numOfTable;
            do {
                numOfTable = Inputter.inputAndLoop("Enter number of table to update (Press 'Enter' to keep the old value):", "Invalid number! (Must be a positive integer)", "(" + Acceptable.INTEGER_VALID + "|^$)");
                if (!numOfTable.isEmpty() && Integer.parseInt(numOfTable) <= 0) {
                    System.out.println("Number of tables need to be greater than 0.");
                }
            } while (!numOfTable.isEmpty() && Integer.parseInt(numOfTable) <= 0);
            if (!numOfTable.isEmpty()) {
                order.setNumOfTables(Integer.parseInt(numOfTable));
            }

            Date dateObj = null;
            SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy");
            formatDate.setLenient(false);
            do {
                dateObj = null;
                try {
                    String dateObjString = Inputter.getString("Enter date to update (Press 'Enter' to keep the old value):");
                    if (dateObjString.isEmpty()) {
                        break;
                    }
                    dateObj = formatDate.parse(dateObjString);
                    if (dateObj != null && new Date().after(dateObj)) {
                        System.out.println("Date must be in the future!");
                        dateObj = null;
                    }
                } catch (ParseException e) {
                    System.out.println("Invalid date format! (Use dd/MM/yyyy)");
                }

            } while (dateObj == null || new Date().after(dateObj));

            if (dateObj != null) {
                order.setEventDate(dateObj);
            }
            
            listOrder.update(order);
            returnMenu = Inputter.inputAndLoop("Do you want to continue with another update? (Y/N):", "Invalid choice! (Y or N only)", Acceptable.YES_NO_VALID);

        } while (returnMenu.equalsIgnoreCase("N") == false);
    }
    
    private void saveDataToFile() {
        String saveChoice = Inputter.inputAndLoop("Which do you want to save? (Choose 1-2):\n1. Customers\n2. Order List\nChoose:", "Invalid choice!", "^(1|2)$");
        if (saveChoice.equals("1")) {
            listCustomer.saveToFile();
        } else {
            listOrder.saveToFile();
        }
    }
    
    private void displayCustomerOrderList() {
        String displaySavedInfo = Inputter.inputAndLoop("What do you want to show? (Choose 1-2)\n1. Customers\n2. Order List\nChoose:", "Invalid choice!", "^(1|2)$");

        if (displaySavedInfo.equals("1")) {
            listCustomer.showAll();
        } else {
            listOrder.showAll();
        }
    }
    
}
