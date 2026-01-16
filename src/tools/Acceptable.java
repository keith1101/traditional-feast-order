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
    public final static String DOUBLE_VALID = "";
    public final static String INTEGER_VALID = "^[1-9]\\d*$";
    public final static String PHONE_VALID = "^[0]\\d{9}$";
    public final static String VIETTEL_VALID = "";
    public final static String VNPT_VALID = "";
    public final static String EMAIL_VALID = "^[A-Za-z0-9]+[@][a-z]+[.][a-z]{2,}$";
    public final static String YES_NO_VALID = "^(Y|N)";
    
    public static boolean isValid(String data, String pattern) {
        return data.matches(pattern);
    }
}
