package tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class FileUtils<T> {
    public static <T> List<T> readFromFile(String filePath) {
        List<T> listObj = new ArrayList<>();
        FileInputStream fileInputStreamObj;
        try {
            File file = new File(filePath);
            fileInputStreamObj = new FileInputStream(file);
            ObjectInputStream objectInputStreamObj = new ObjectInputStream(fileInputStreamObj);
            while (fileInputStreamObj.available() > 0) {
                T object = (T) objectInputStreamObj.readObject();
                listObj.add(object);
            }
            fileInputStreamObj.close();
            objectInputStreamObj.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filePath);
        } catch (IOException e) {
            System.out.println("Error reading file: " + filePath);
        } catch (ClassNotFoundException e) {
            System.out.println("Invalid data format in file: " + filePath);
        }
        return listObj;
    }
    
    public static <T> void saveToFile(List<T> listObj, String filePath) {
        FileOutputStream fos;
        try {
            File file = new File(filePath);
            fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            for (T object : listObj) {
                oos.writeObject(object);
            }
            fos.close();
            oos.close();
        } catch (FileNotFoundException e) {
            System.out.println("Cannot create file: " + filePath);
        } catch (IOException e) {
            System.out.println("Error writing to file: " + filePath);
        }
    }
}
