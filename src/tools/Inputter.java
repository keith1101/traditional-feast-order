/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tools;

import java.util.Scanner;

/**
 *
 * @author LENOVO
 */
public class Inputter {
    static Scanner ndl = new Scanner(System.in);
    
    public Inputter() {
    }
    
    public static String getString(String mess) {
        System.out.print(mess);
        return ndl.nextLine();
    }
    
    public static int getInt(String mess) {
        int result = 0;
        String temp = getString(mess);
        if (Acceptable.isValid(temp, Acceptable.INTEGER_VALID))
            result = Integer.parseInt(temp);
        return result;
    }
    
    public static double getDouble(String mess) {
        double result = 0;
        String temp = getString(mess);
        if (Acceptable.isValid(temp, Acceptable.DOUBLE_VALID))
            result = Double.parseDouble(temp);
        return result;
    }
    
    public static String inputAndLoop(String mess, String pattern) {
        boolean more = true;
        String result;
        do {
            result = getString(mess).trim();
            more = !Acceptable.isValid(result, pattern);
            if (result.length() <= 0 || more) {
                System.out.println("Data invalid! Please re-enter...");
            }
        } while (more);
        return result;
    }
}
