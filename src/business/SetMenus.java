package business;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import model.SetMenu;

public class SetMenus extends ArrayList<SetMenu> {
    private String pathFile;
    public SetMenus() {
        pathFile = "src/FeastMenu.csv";
        readFromFile();
    }
    
    public SetMenu isValidMenuId(String menuId) {
        for (SetMenu menu: this) {
            if (menu.getMenuId().equals(menuId)) {
                return menu;
            }
        }
        return null;
    }
    
    public SetMenu dataToObject(String text) {
        String[] setMenuItem = text.split(",");
        return new SetMenu(setMenuItem[0],setMenuItem[1],Double.parseDouble(setMenuItem[2]),setMenuItem[3]);
    }
    
    public void readFromFile() {
        this.clear();
        try {
            File fileObject = new File(pathFile);
            Scanner sc = new Scanner(fileObject);
            sc.nextLine();
            while (sc.hasNextLine()) {
                this.add(dataToObject(sc.nextLine()));
            }
            sc.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("Cannot read data from feastMenu.csv. Please check it.");
        }     
    }
    
    public String formatSetMenuPrice(double price) {
        String formatPrice = String.format("%.0f", price);
        String returnPrice = "";
        if (formatPrice.length() % 3 != 0) {
            returnPrice = returnPrice + formatPrice.substring(0, formatPrice.length() % 3) + ",";
        }
        for (int i = formatPrice.length() % 3; i < formatPrice.length(); i += 3) {
            returnPrice = returnPrice + formatPrice.substring(i, i + 3) + ",";
        }
        return returnPrice.substring(0, returnPrice.length() - 1) + " Vnd";
    }
    
    public void showMenuList() {
        System.out.println("----------------------------------------------------");
        System.out.println("List of Set Menus for ordering party:");
        System.out.println("----------------------------------------------------");
        
        for (int i = 0; i < this.size() - 1; i++) {
            for (int j = 0; j < this.size() - 1 - i; j++) {
                if (Double.compare(this.get(j).getPrice(), this.get(j + 1).getPrice()) > 0) {
                    SetMenu temp = this.get(j);
                    this.set(j, this.get(j + 1));
                    this.set(j + 1, temp);
                }
            }
        }
        
        for (SetMenu item: this) {
            String[] listIngredients = item.getIngredients().split("#");
            System.out.println(String.format("Code%-6s :%s\n"
                    +                        "Name%-6s :%s\n"
                    +                        "Price%-6s: %s\n"
                    +                        "Ingredients:\n%s\n%s\n%s\n",
                    "",item.getMenuId(),"",item.getMenuName(),"",formatSetMenuPrice(item.getPrice()),listIngredients[0].substring(1),listIngredients[1],listIngredients[2].substring(0, listIngredients[2].length()-1)));
            System.out.println("----------------------------------------------------");
        }
    }
}
