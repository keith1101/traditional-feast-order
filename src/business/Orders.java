/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package business;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import model.Order;
import tools.FileUtils;

/**
 *
 * @author LENOVO
 */
public class Orders extends HashSet<Order> implements Workable<Order>{
    
    boolean saved;
    String setMenuPath;
    String pathFileSave;
    
    public SetMenus listSetMenu;
    
    SimpleDateFormat formatTime = new SimpleDateFormat("dd/MM/yyyy");

    
    public Orders() {
        listSetMenu = new SetMenus();
        setMenuPath = "src/FeastMenu.csv";
        pathFileSave = "src/feast_order_service.dat";
        saved = false;
        listSetMenu.readFromFile();
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
        if (updateOrder != null) {
            updateOrder.setCustomerId(x.getCustomerId());
            updateOrder.setMenuId(x.getMenuId());
            updateOrder.setNumOfTables(x.getNumOfTables());
            updateOrder.setEventDate(x.getEventDate());
            saved = false;
        }
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
        
        // Collections.sort(orders, (order_1, order_2) -> (order_1.getEventDate().compareTo(order_2.getEventDate())));
        Collections.sort(orders, Comparator.comparing(Order::getEventDate));
        
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
    
    public void readFromFile() {
        this.clear();
        List<Order> listOrder = FileUtils.readFromFile(pathFileSave);
        for (Order order : listOrder) {
            addNew(order);
        }
    }
    
    private List<Order> toList() {
        return new ArrayList<>(this);
    }
    
    public void saveToFile() {
       if (this.isSaved()) return;
       saved = true;
       FileUtils.saveToFile(toList(), pathFileSave);
       System.out.println("Order data has been successfully saved to “feast_order_service.dat”.");
    }
}
