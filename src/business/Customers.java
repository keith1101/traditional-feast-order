package business;
import java.util.ArrayList;
import java.util.List;
import model.Customer;
import tools.FileUtils;
import java.util.Collections;


/**
 *
 * @author LENOVO
 */
public class Customers extends ArrayList<Customer> implements Workable<Customer> {
    String pathFileSave;
    boolean saved;
    
    public Customers() {
        pathFileSave = "src/customer.dat";
        saved = false;
    }
    
    public boolean isSaved() {
        return saved;
    }
    
    @Override
    public void addNew(Customer customer) {
        this.add(customer);
    }
    
    @Override
    public void update(Customer customer) {
        Customer updatedCustomer = searchById(customer.getId());
        if (updatedCustomer != null) {
            updatedCustomer.setName(customer.getName());
            updatedCustomer.setPhone(customer.getPhone());
            updatedCustomer.setEmail(customer.getEmail());
        }
    }
    
    @Override
    public Customer searchById(String id) {
        for (Customer customer: this) {
            if (id.equals(customer.getId())) {
                return customer;
            }
        }
        return null;
    }
    
    public String formatName(String name) {
        if (name == null || name.isEmpty()) return "";
        String[] customerName = name.split(" ");
        String temp = customerName[customerName.length - 1];
        customerName[customerName.length - 1] = "";
        return temp+", "+String.join(" ", customerName).trim();
    }
    
    public List<Customer> filterByName(String name) {
        name = name.toLowerCase();
        ArrayList<Customer> filteredCustomers = new ArrayList<>();
        for (Customer customer: this) {
            String[] customerName = customer.getName().split(" ");
            if (customerName[customerName.length - 1].toLowerCase().contains(name)) {
                String temp = customerName[customerName.length - 1];
                customerName[customerName.length - 1] = "";
                
                Customer customerBeFiltered = new Customer(customer.getId(),customer.getName(),customer.getPhone(),customer.getEmail());
                customerBeFiltered.setName(temp+", "+String.join(" ", customerName).trim());
                
                filteredCustomers.add(customerBeFiltered);
            }
        }
        
        Collections.sort(filteredCustomers, (customer1, customer2) -> (customer1.getName().compareTo(customer2.getName())));
        return filteredCustomers;

    }
    
    @Override
    public void showAll() {
        saveToFile();
        this.clear();
        readFromFile();
        Collections.sort(this, (customer_1, customer_2) -> (customer_1.getName().compareTo(customer_2.getName())));
        showAll(this);
    }
    
    public void showAll(List<Customer> customers) {
        if (customers.isEmpty()) {
            System.out.println("Does not have any customer information.");
            return;
        }
        System.out.println("-------------------------------------------------------------------------");
        System.out.println(String.format("%-10s|%-20s|%-20s|%-20s","Code","Customer Name","Phone","Email"));
        System.out.println("-------------------------------------------------------------------------");
        for (Customer customer:customers) {
            System.out.println(String.format("%-10s|%-20s|%-20s|%-20s", customer.getId(), customer.getName(), customer.getPhone(), customer.getEmail()));
        }            
        System.out.println("-------------------------------------------------------------------------");
    }
    
    
    
    public void readFromFile() {
        this.clear();
        this.addAll(FileUtils.readFromFile(pathFileSave));
    }
    
    public void saveToFile() {
        if (this.isSaved()) return;
        FileUtils.saveToFile(this, pathFileSave);
        saved = true;
        System.out.println("Customer data has been successfully saved to “customers.dat”.");
    }
    
}
