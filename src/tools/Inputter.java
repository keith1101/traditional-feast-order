package tools;

import business.Customers;
import business.Orders;
import business.SetMenus;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import model.Customer;
import model.Order;

public class Inputter {
    static Scanner scanner = new Scanner(System.in);
    
    public Inputter() {
    }
    
    public static String getString(String mess) {
        System.out.print(mess);
        return scanner.nextLine();
    }
    
    public static int getInt(String mess) {
        int result = 0;
        String temp = getString(mess);
        if (Acceptable.isValid(temp, Acceptable.INTEGER_VALID))
            result = Integer.parseInt(temp);
        return result;
    }
    
    public static double getDouble(String mess) {
        double result = 0;
        String temp = getString(mess);
        if (Acceptable.isValid(temp, Acceptable.DOUBLE_VALID))
            result = Double.parseDouble(temp);
        return result;
    }
    
    public static String inputAndLoop(String mess, String pattern) {
        boolean more = true;
        String result;
        do {
            result = getString(mess).trim();
            more = !Acceptable.isValid(result, pattern);
            if (result.isEmpty() || more) {
                System.out.println("Data invalid! Please re-enter...");
            }
        } while (more);
        return result;
    }

    public static void searchCustomerByName(Customers listCustomer) {
        while (true) {
            String name = Inputter.inputAndLoop("Enter customer name to search:", Acceptable.NAME_VALID);
            List<Customer> customers = listCustomer.filterByName(name);
            if (!customers.isEmpty()) {
                listCustomer.showAll(customers);
            } else {
                System.out.println("No one matches the search criteria!");
            }
            
            String choice = Inputter.inputAndLoop("Do you want to continue to search for customers by name? (Y/N)", Acceptable.YES_NO_VALID);
            
            if (choice.equalsIgnoreCase("N")) {
                break;
            }
        }
    }

    public static void displaySuccess(Customers listCustomer, SetMenus listFeast, Orders listOrder, Order x) {
        String[] listIngredients = listFeast.isValidMenuId(x.getMenuId()).getIngredients().split("#");
        SimpleDateFormat formatTime = new SimpleDateFormat("dd/MM/yyyy");

        System.out.println("------------------------------------------------------------");
        System.out.println(String.format("Customer order information [Order ID: %s]",x.getOrderCode()));
        System.out.println("------------------------------------------------------------");
        System.out.println(String.format(
          "Code             : %s\n"
        + "Customer name    : %s\n"
        + "Phone number     : %s\n"
        + "Email            : %s\n"
        + "------------------------------------------------------------\n"
        + "Code of Set Menu: %s\n"
        + "Set menu name   : %s\n"
        + "Event date      : %s\n"
        + "Number of tables: %d\n"
        + "Price           : %s\n"
        + "Ingredients:\n"
        + "%s\n%s\n%s\n"
        + "------------------------------------------------------------\n"
        + "Total cost      : %s\n"
        + "------------------------------------------------------------",
        x.getCustomerId(), listCustomer.formatName(listCustomer.searchById(x.getCustomerId()).getName()),
        listCustomer.searchById(x.getCustomerId()).getPhone(),
        listCustomer.searchById(x.getCustomerId()).getEmail(),
        x.getMenuId(),
        listFeast.isValidMenuId(x.getMenuId()).getMenuName(),
        formatTime.format(x.getEventDate()),
        x.getNumOfTables(),
        listFeast.formatSetMenuPrice(listFeast.isValidMenuId(x.getMenuId()).getPrice()),
        listIngredients[0].substring(1),listIngredients[1],listIngredients[2].substring(0, listIngredients[2].length()-1),
        listFeast.formatSetMenuPrice(listFeast.isValidMenuId(x.getMenuId()).getPrice() * x.getNumOfTables())));            
        
    }

    public static void placeFeastOrder (Customers listCustomer, SetMenus listFeast, Orders listOrder) {
        SimpleDateFormat formatTime = new SimpleDateFormat("dd/MM/yyyy");
        while (true) {
            String customerId;
            do {
                customerId = Inputter.inputAndLoop("Enter customer code to order:", Acceptable.CUSTOMER_ID_VALID);
                if (listCustomer.searchById(customerId) == null) {
                    System.out.println("Customer code doesn't exist!");
                }
            } while (listCustomer.searchById(customerId) == null);

            String setMenuCode;
            do {
                setMenuCode = Inputter.getString("Enter code of set menu to order:");
                if (listFeast.isValidMenuId(setMenuCode) == null) {
                    System.out.println("Set menu code doesn't exist.");
                }
            } while (listFeast.isValidMenuId(setMenuCode) == null);

            int numOfTables;
            do {
                numOfTables = Inputter.getInt("Enter number of tables:");
                if (numOfTables <= 0) {
                    System.out.println("Number of tables is greater than 0!");
                }
            } while (numOfTables <= 0);


            Date date = null;
            do {
                try {
                    date = formatTime.parse(Inputter.getString("Enter the event date:"));
                } catch (ParseException e) {
                    System.out.println("Wrong date format!");
                    continue;
                }


                if (new Date().after(date)) {
                    System.out.println("The preferred event date must be in the future.");
                }
            } while (new Date().after(date));

            Order newOrder = new Order(customerId, setMenuCode, numOfTables, date);

            if (listOrder.isDuplicate(newOrder)) {
                System.out.println("Dupplicate data!");
                continue;
            } 

            listOrder.addNew(newOrder);
            
            displaySuccess(listCustomer, listFeast, listOrder, newOrder);

            String returnMenu = Inputter.inputAndLoop("Do you want to continue with place another order? (Y/N):", Acceptable.YES_NO_VALID);
            if (returnMenu.equalsIgnoreCase("N")) {
                break;
            }
        }
    }
}
