/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package business;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import model.SetMenu;

/**
 *
 * @author LENOVO
 */
public class SetMenus extends ArrayList<SetMenu> {
    String pathFile;
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
        String cost = String.format("%.0f",price);
        String returnFormat = "";
        
        if (cost.length() % 3 != 0) {
            returnFormat = returnFormat.concat(cost.substring(0,cost.length()%3)+",");
        }
        for (int j = cost.length()%3; j < cost.length(); j+=3) {
            returnFormat = returnFormat.concat(cost.substring(j,j+3)+",");
        }
        
        return returnFormat.substring(0,returnFormat.length()-1) + " Vnd";
        
    }
    
    public void showMenuList() {
        System.out.println("----------------------------------------------------");
        System.out.println("List of Set Menus for ordering party:");
        System.out.println("----------------------------------------------------");
        
        Collections.sort(this, (set_menu_1, set_menu_2) -> (Double.compare(set_menu_1.getPrice(), set_menu_2.getPrice())));
        
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
