/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tools;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author LENOVO
 */
public class FileUtils {
    public static <T> void saveToFile(List<T> list, String path) {
        FileOutputStream fos = null;
        try {
            File f = new File(path);
            fos = new FileOutputStream(f);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            for (T object : list) {
                oos.writeObject(object);
            }
        }
        catch (FileNotFoundException ex) {
            System.out.println("Cannot find " + path + ". Please check!");
        }
        catch (IOException ex) {
            System.out.println(ex);
        }
        finally {
            try {
                fos.close();
            }
            catch (IOException ex) {
                System.out.println(ex);
            }
        }
    }
    
    public static <T> List<T> readFromFile (String path) {
        List<T> listObject = new ArrayList<>();
        File fileToRead = new File(path);
        try {
            FileInputStream fileInputStreamObject = new FileInputStream(fileToRead);
            ObjectInputStream objectInputStreamObject = new ObjectInputStream(fileInputStreamObject);
            
            while (fileInputStreamObject.available() > 0) {
                try {
                    T item = (T)objectInputStreamObject.readObject();
                    listObject.add(item);
                } catch (EOFException | ClassNotFoundException e) {
                    break;
                }
            }
            
            fileInputStreamObject.close();
            objectInputStreamObject.close();
        }
        catch (IOException ex) {
            System.out.println(ex);
        }
        return listObject;
    }
}
