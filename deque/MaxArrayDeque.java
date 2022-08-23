package deque;
import java.util.Comparator;


<<<<<<< HEAD
public class MaxArrayDeque<T> extends ArrayDeque {

    private Comparator<T> comparer;

    public MaxArrayDeque(Comparator<T> c){
    comparer = c;
    }

    public T max(Comparator<T> c){
        if(this.size() == 0) {
            return null;
        }
        T a=(T) values[head];
        for(int tracker=0;tracker<values.length-1; tracker++){
            if(c.compare((T)this.get(tracker), a)>0){
                a = (T)this.get(tracker);
            }
        } return a;}
=======
public class MaxArrayDeque<T> extends ArrayDeque<T> {

    private Comparator<T> comparer;

    public MaxArrayDeque(Comparator<T> c) {
        comparer = c;
    }

    public T max(Comparator<T> c) {
        if (this.size() == 0) {
            return null;
        }
        T a = (T) this.get(0);
        for (int tracker=0; tracker<this.size()-1; tracker++) {
            if (c.compare((T)this.get(tracker), a)>0) {
                a = (T)this.get(tracker);
            }
        } return a;
    }
>>>>>>> d12afe5ad0f93eb06f5fca6e87af3aa45090dcec

    public T max() {
        return max(comparer);
    }
}
