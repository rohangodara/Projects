package deque;

/* The Deque interface defines the expected behavior for any
* Deque, whether it is an ArrayDeque or LinkedListDeque. A
* Deque is a doubly-ended queue, that allows you to quickly add
* and remove items from the front and back. */
public interface Deque<T> {
    void addFirst(T item);
<<<<<<< HEAD
    void addLast(T item);
    default boolean isEmpty(){
        if(size()==0){return true;}
        else {return false;}
    }
    int size();
    void printDeque();
    T removeFirst();
    T removeLast();
    T get(int index);
=======

    void addLast(T item);

    default boolean isEmpty(){
        if (size() == 0) {
            return true;
        }
        return false;
    }

    int size();

    void printDeque();

    T removeFirst();

    T removeLast();

    T get(int index);

>>>>>>> d12afe5ad0f93eb06f5fca6e87af3aa45090dcec
    boolean equals(Object o);
}
