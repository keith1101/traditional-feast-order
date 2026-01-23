package business;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import model.Order;
import tools.FileUtils;

public class Orders extends HashSet<Order> implements Workable<Order>{
    
    private final String pathFileSave;
    
    private final SetMenus listSetMenu;
    
    private final SimpleDateFormat formatTime = new SimpleDateFormat("dd/MM/yyyy");
    
    public SetMenus getListSetMenu() {
        return listSetMenu;
    }

    
    public Orders() {
        listSetMenu = new SetMenus();
        pathFileSave = "src/feast_order_service.dat";
        listSetMenu.readFromFile();
    }
    
    public boolean isDuplicate(Order x) {
        return this.contains(x);
    }
    
    @Override
    public void addNew(Order x) {
        if (!this.isDuplicate(x)) {
            this.add(x);
        }
    }
    
    @Override
    public void update(Order x) {
        Order updateOrder = searchById(x.getOrderCode());
        if (updateOrder != null) {
            updateOrder.setCustomerId(x.getCustomerId());
            updateOrder.setMenuId(x.getMenuId());
            updateOrder.setNumOfTables(x.getNumOfTables());
            updateOrder.setEventDate(x.getEventDate());
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
        
        for (int i = 0; i < orders.size() - 1; i++) {
            for (int j = 0; j < orders.size() - 1 - i; j++) {
                if (orders.get(j).getEventDate().compareTo(orders.get(j + 1).getEventDate()) > 0) {
                    Order temp = orders.get(j);
                    orders.set(j, orders.get(j + 1));
                    orders.set(j + 1, temp);
                }
            }
        }
        
        System.out.println("-------------------------------------------------------------------------------");
        System.out.println(String.format("%-15s|%-10s|%-12s|%-8s|%-9s|%-6s|%13s","ID","Event","Customer ID","SetMenu","Price","Tables","Cost"));
        System.out.println("-------------------------------------------------------------------------------");
        for (Order order:orders) {
            
            String priceToPrint = listSetMenu.formatSetMenuPrice(listSetMenu.isValidMenuId(order.getMenuId()).getPrice()); priceToPrint = priceToPrint.substring(0, priceToPrint.length()-4);
            String costToPrint = listSetMenu.formatSetMenuPrice(listSetMenu.isValidMenuId(order.getMenuId()).getPrice()*order.getNumOfTables()); costToPrint = costToPrint.substring(0, costToPrint.length()-4);
            
            
            System.out.println(String.format("%-15s|%-10s|%-12s|%-8s|%-9s|%6s|%13s", order.getOrderCode(), formatTime.format(order.getEventDate()), order.getCustomerId(), order.getMenuId(), 
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
       FileUtils.saveToFile(toList(), pathFileSave);
       System.out.println("Order data has been successfully saved to “feast_order_service.dat”.");
    }
}
