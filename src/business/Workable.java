package business;


public interface Workable<T>{
    void addNew(T x);
    void update(T x);
    T searchById(String id);
    void showAll();
}
