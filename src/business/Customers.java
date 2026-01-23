package business;
import java.util.ArrayList;
import java.util.List;
import model.Customer;
import tools.FileUtils;

public class Customers extends ArrayList<Customer> implements Workable<Customer> {
    private String pathFileSave;
    
    public Customers() {
        pathFileSave = "src/customer.dat";
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
            if (id.equalsIgnoreCase(customer.getId())) {
                return customer;
            }
        }
        return null;
    }
    

    private void sortByLastName(List<Customer> customers) {
        for (int i = 0; i < customers.size() - 1; ++i) {
            for (int j = 0; j < customers.size() - 1 - i; ++j) {
                String last1[] = customers.get(j).getName().split(" ");
                String last2[] = customers.get(j + 1).getName().split(" ");
                
                if (last1[last1.length - 1].compareToIgnoreCase(last2[last2.length - 1]) > 0) {
                    Customer temp = customers.get(j);
                    customers.set(j, customers.get(j+1));
                    customers.set(j+1, temp);
                }
            }
        }
    }
    
    public List<Customer> filterByName(String name) {
        List<Customer> filteredCustomers = new ArrayList<>();
        for (Customer customer : this) {
            String[] lastCustomerName = customer.getName().split(" ");
            if (lastCustomerName[lastCustomerName.length - 1].toLowerCase().contains(name.toLowerCase())) {
                filteredCustomers.add(customer);
            }
        }

        sortByLastName(filteredCustomers);
        return filteredCustomers;
    }
    
    public String formatName(String name) {
        if (name == null || name == "") return "";
        
        String[] nameFormated = name.split(" ");
        String lastName = nameFormated[nameFormated.length - 1];
        nameFormated[nameFormated.length - 1] = "";
        
        String returnName = lastName + ", " + String.join(" ", nameFormated);
        return returnName.trim();
    }

    @Override
    public void showAll() {
        readFromFile();

        sortByLastName(this);

        showAll(this);
    }
    
    public void showAll(List<Customer> customers) {
        if (customers.isEmpty()) {
            System.out.println("Does not have any customer information.");
        } else {
            System.out.println("-------------------------------------------------------------------------");
            System.out.println(String.format("%-10s|%-20s|%-20s|%-20s","Code","Customer Name","Phone","Email"));
            System.out.println("-------------------------------------------------------------------------");
            for (Customer customer:customers) {
                System.out.println(String.format("%-10s|%-20s|%-20s|%-20s", customer.getId(), formatName(customer.getName()), customer.getPhone(), customer.getEmail()));
            }            
            System.out.println("-------------------------------------------------------------------------");
        }
    }
    
    
    
    public void readFromFile() {
        this.clear();
        this.addAll(FileUtils.readFromFile(pathFileSave));
    }
    
    public void saveToFile() {
        FileUtils.saveToFile(this, pathFileSave);
        System.out.println("Customer data has been successfully saved to “customers.dat”.");
    }
    
}
