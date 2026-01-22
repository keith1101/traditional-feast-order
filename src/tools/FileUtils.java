package tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class FileUtils<T> {
    public static <T> List<T> readFromFile(String filePath) {
        List<T> listObject = new ArrayList<>();
        FileInputStream fileInputStreamObject;
        try {
            File file = new File(filePath);
            
            fileInputStreamObject = new FileInputStream(file);
            try {
                ObjectInputStream objectInputStreamObject = new ObjectInputStream(fileInputStreamObject);
                while (fileInputStreamObject.available() > 0) {
                    T object = (T) objectInputStreamObject.readObject();
                    listObject.add(object);
                }
                
                fileInputStreamObject.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            
        } catch (IOException e) {
            System.out.println("Error reading from file: " + filePath);
        }
        return listObject;
    }
    
    public static <T> void saveToFile(List<T> listObject, String filePath) {
        FileOutputStream fileOutputStreamObject;
        try {
            File file = new File(filePath);
            fileOutputStreamObject = new FileOutputStream(file);
            try {
                ObjectOutputStream objectOutputStreamObject = new ObjectOutputStream(fileOutputStreamObject);
                for (T object : listObject) {
                    objectOutputStreamObject.writeObject(object);
                }
                
                fileOutputStreamObject.close();
                objectOutputStreamObject.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        catch (IOException e) {
            System.out.println("Error saving to file: " + filePath + " - " + e.getMessage());
        }
    }
}