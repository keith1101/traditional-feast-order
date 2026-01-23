package model;

public class SetMenu {
    private String menuId;
    private String menuName;
    private double price;
    private String ingredients;
    
    public SetMenu() {
        menuId = "";
        menuName = "";
        price = 0;
        ingredients = "";
    }

    public SetMenu(String menuId, String menuName, double price, String ingredients) {
        this.menuId = menuId;
        this.menuName = menuName;
        this.price = price;
        this.ingredients = ingredients;
    }

    public String getMenuId() {
        return menuId;
    }

    public String getMenuName() {
        return menuName;
    }

    public double getPrice() {
        return price;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    @Override
    public String toString() {
        return "SetMenu{" + "menuId=" + menuId + ", menuName=" + menuName + ", price=" + price + ", ingredients=" + ingredients + '}';
    }
    
    
}
