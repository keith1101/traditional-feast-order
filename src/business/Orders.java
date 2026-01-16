/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package business;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import model.Order;
import model.SetMenu;
import tools.Acceptable;
import tools.FileUtils;
import tools.Inputter;

/**
 *
 * @author LENOVO
 */
public class Orders extends HashSet<Order> implements Workable<Order>{
    
    boolean saved;
    String setMenuPath;
    String pathFileSave;
    
    SetMenus listSetMenu;
    
    SimpleDateFormat formatTime = new SimpleDateFormat("dd/MM/yyyy");

    
    public Orders() {
        listSetMenu = new SetMenus();
        setMenuPath = "src/FeastMenu.csv";
        pathFileSave = "src/feast_order_service.dat";
        saved = false;
        readSetMenuFromFile();
    }
    
    public SetMenu isValidMenuId(String menuId) {
        for (SetMenu menu: listSetMenu) {
            if (menu.getMenuId().equals(menuId)) {
                return menu;
            }
        }
        return null;
    }
    
    public boolean isSaved() {
        return saved;
    }

    public boolean isDuplicate(Order x) {
        return this.contains(x);
    }
    
    @Override
    public void addNew(Order x) {
        if (!this.isDuplicate(x))
            this.add(x);
    }
    
    @Override
    public void update(Order x) {
        Order updateOrder = searchById(x.getOrderCode());
        updateOrder = x;
    }
    
    @Override
    public Order searchById(String id) {
        for (Order order : this) {
            if (order.getOrderCode().equals(id)) {
                return order;
            }
        }
        return null;
    }
    
    @Override
    public void showAll() {
        readFromFile();
        showAll(toList());
    }
    
    public void showAll(List<Order> orders) {
        if (orders.isEmpty()) {
            System.out.println("Does not have any customer information.");
            return;
        }
        
        Collections.sort(orders, (order_1, order_2) -> (order_1.getEventDate().compareTo(order_2.getEventDate())));
        
        
        System.out.println("-------------------------------------------------------------------------------");
        System.out.println(String.format("%-15s|%-10s|%-11s|%-7s|%-9s|%-6s|%15s","ID","Event","Customer ID","SetMenu","Price","Tables","Cost"));
        System.out.println("-------------------------------------------------------------------------------");
        for (Order order:orders) {
            
            String priceToPrint = listSetMenu.formatSetMenuPrice(listSetMenu.isValidMenuId(order.getMenuId()).getPrice()); priceToPrint = priceToPrint.substring(0, priceToPrint.length()-4);
            String costToPrint = listSetMenu.formatSetMenuPrice(listSetMenu.isValidMenuId(order.getMenuId()).getPrice()*order.getNumOfTables()); costToPrint = costToPrint.substring(0, costToPrint.length()-4);
            
            
            System.out.println(String.format("%-15s|%-10s|%-11s|%-7s|%-9s|%6s|%15s", order.getOrderCode(), formatTime.format(order.getEventDate()), order.getCustomerId(), order.getMenuId(), 
                    priceToPrint,order.getNumOfTables(),
                    costToPrint));
        }          
        System.out.println("-------------------------------------------------------------------------------");
    }
    
    
    public void displaySuccess(Customers listCustomer, SetMenus listFeast, Orders listOrder, Order x) {
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
    
    public void placeFeastOrder (Customers listCustomer, SetMenus listFeast, Orders listOrder) {
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
    
    public SetMenu dataToObject(String text) {
        String[] setMenuItem = text.split(",");
        return new SetMenu(setMenuItem[0],setMenuItem[1],Double.parseDouble(setMenuItem[2]),setMenuItem[3]);
    }
    
    public void readSetMenuFromFile() {
        try {
            File fileObject = new File(setMenuPath);
            Scanner sc = new Scanner(fileObject);
            sc.nextLine();
            while (sc.hasNextLine()) {
                listSetMenu.add(dataToObject(sc.nextLine()));
            }
        } catch (FileNotFoundException e) {
            System.out.println("Cannot read data from feastMenu.csv. Please check it.");
        }
    }
    
    public void readFromFile() {
        this.clear();
        List<Order> l = FileUtils.readFromFile(pathFileSave);
        for (Order i : l) {
            addNew(i);
        }
//        this.saved = true;
    }
    
    private List<Order> toList() {
        return new ArrayList<>(this);
    }
    
    public void saveToFile() {
       if (this.isSaved()) return;
//       saved = true;
       FileUtils.saveToFile(toList(), pathFileSave);
       System.out.println("Order data has been successfully saved to “feast_order_service.dat”.");
    }
}
