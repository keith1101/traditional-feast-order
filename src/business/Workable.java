/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package business;

/**
 *
 * @author LENOVO
 * @param <T>
 */
public interface Workable<T>{
    void addNew(T x);
    void update(T x);
    T searchById(String id);
    void showAll();
}
