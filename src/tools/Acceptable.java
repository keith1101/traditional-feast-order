/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package tools;

/**
 *
 * @author LENOVO
 */
public interface Acceptable {
    public final static String CUSTOMER_ID_VALID = "^[CcGgKk]\\d{4}$";
    public final static String NAME_VALID = "^[A-Za-z ]{2,25}$";
    public final static String DOUBLE_VALID = "^[1-9]\\d*(\\.\\d+)?$";
    public final static String INTEGER_VALID = "^[1-9]\\d*$";
    public final static String PHONE_VALID = "^09\\d{8}$";
    public final static String EMAIL_VALID = "^[A-Za-z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
    public final static String YES_NO_VALID = "^[YyNn]$";
    
    public static boolean isValid(String data, String pattern) {
        return data.matches(pattern);
    }
}
