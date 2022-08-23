package deque;

<<<<<<< HEAD
import java.lang.reflect.Array;

public class ArrayDeque<T> implements Deque<T> {
    private T[] values;
    private int size;
    private int head;
    private int tail;

    public ArrayDeque(){
        values= (T[]) new Object[8];
        size=0;

    }
    public T[] upsizehelper(){
        if(size==values.length){T[] a= values; values=(T[]) new Object[2*values.length];
        values[0]=a[head];int oldhead=head; head=0; int position=1;System.out.println(oldhead);
        for(;position<size-1;){
            if(oldhead+1>a.length){oldhead=-1;}
            values[position]=a[oldhead+1];
            oldhead++;position++;
            System.out.println(oldhead);
        }
        values[position]=a[oldhead]; tail=position;
        return values;
        }
        else{return values;}
=======
public class ArrayDeque<T> implements Deque<T> {
    private T[] items;
    private int size;
    private int nextFirst;
    private int nextLast;
    private int userFirst;
    private int userLast;

    public ArrayDeque() {
        items = (T[]) new Object[8];
        size = 0;
        nextFirst = 3;
        nextLast = 4;
>>>>>>> d12afe5ad0f93eb06f5fca6e87af3aa45090dcec
    }

    @Override
    public void addFirst(T item) {
<<<<<<< HEAD
        values=this.upsizehelper();
        if (size==0){
            values[0]=item;
            head=0;
            tail=0;
        }
        else{
            if(head==0){head= values.length-1;values[head]=item;}
            else{head=head-1;values[head]=item;}
        } size++;}



    @Override
    public void addLast(T item) {
        values=this.upsizehelper();
        if (size==0){
            values[0]=item;
            head=0;
            tail=0;
        }
        else{
            if(tail==values.length-1){tail=0;values[tail]=item;}
            else{tail=tail+1;values[tail]=item;}
        } size++;}
=======
        size = size + 1;
        items[nextFirst] = item;
        if (nextFirst - 1 < 0) {
            nextFirst = items.length - 1;
        } else {
            nextFirst = nextFirst - 1;
        }
        if (size == 1) {
            userFirst = nextFirst + 1;
            userLast = nextFirst + 1;
        } else {
            if (userFirst - 1 < 0) {
                userFirst = items.length - 1;
            } else {
                userFirst = userFirst - 1;
            }
        }
        if (resizeUpRequired()) {
            resizeUpHelper();
        }
    }

    @Override
    public void addLast(T item) {
        size = size + 1;
        items[nextLast] = item;
        if (nextLast + 1 == items.length) {
            nextLast = 0;
        } else {
            nextLast = nextLast + 1;
        }
        if (size == 1) {
            userFirst = nextLast - 1;
            userLast = nextLast - 1;
        } else {
            if (userLast + 1 == items.length) {
                userLast = 0;
            } else {
                userLast = userLast + 1;
            }
        }
        if (resizeUpRequired()) {
            resizeUpHelper();
        }
    }

    //checks if array needs to be resized up
    private boolean resizeUpRequired() {
        return items[nextFirst] != null || items[nextLast] != null;
    }

    //doubles size of array and sets userfirst to 0 and userlast accordingly
    private void resizeUpHelper() {
        T[] returnArray = (T[]) new Object[items.length * 2];
        for (int x = 0; x < size; x++) {
            returnArray[x] = get(x);
        }
        items = returnArray;
        userFirst = 0;
        userLast = size - 1;
        nextLast = size;
        nextFirst = items.length - 1;
    }

>>>>>>> d12afe5ad0f93eb06f5fca6e87af3aa45090dcec

    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
<<<<<<< HEAD
        for(int tracker=0;tracker<size-1;){
            System.out.print(this.get(tracker)+" ");
        }
        System.out.println(this.get(tail));
    }

    public T[] downsizehelper(){
        if(size/values.length<0.25){
            T[] a= values; values=(T[]) new Object[values.length/2];
            values[0]=a[head];int oldhead=head; head=0; int position=1;
            for(;position<size-1;){
                values[position]=a[oldhead+1];
                oldhead++;position++;
            }
            values[position]=a[oldhead+1]; tail=position;
            return values;
        }
        else {return values;}
=======
        for (int x = 0; x < size; x++) {
            System.out.print(get(x) + " ");
        }
>>>>>>> d12afe5ad0f93eb06f5fca6e87af3aa45090dcec
    }

    @Override
    public T removeFirst() {
<<<<<<< HEAD
        T a=values[head];
        values[head]=null;
        if(size==0){return null;}
        if(head==values.length-1){head=0;}
        else{head=head+1;}
        size--;
        values=this.downsizehelper();
        return a;
=======
        if (isEmpty()) {
            return null;
        }
        size = size - 1;
        T returnVal = items[userFirst];
        items[userFirst] = null;
        changeNextRemove("first");
        changeUserRemove("first");
        if (resizeDownRequired()) {
            resizeDownHelper();
        }
        return returnVal;
>>>>>>> d12afe5ad0f93eb06f5fca6e87af3aa45090dcec
    }

    @Override
    public T removeLast() {
<<<<<<< HEAD
        T a=values[tail];
        values[tail]=null;
        if(size==0){return null;}
        if(tail==0){tail=values.length-1;}
        else{tail=tail-1;}
        size--;
        values=this.downsizehelper();
        return a;
=======
        if (size == 0) {
            return null;
        }
        size = size - 1;
        T returnVal = items[userLast];
        items[userLast] = null;
        changeNextRemove("last");
        changeUserRemove("last");
        if (resizeDownRequired()) {
            resizeDownHelper();
        }
        return returnVal;
    }

    //changes nextFirst or nextLast based on which remove function is being called
    private void changeNextRemove(String s) {
        if (s.equals("first")) {
            if (size == 0) {
                nextFirst = 3;
                nextLast = 4;
            } else {
                if (nextFirst + 1 == items.length) {
                    nextFirst = 0;
                } else {
                    nextFirst++;
                }
            }
        } else if (s.equals("last")) {
            if (size == 0) {
                nextFirst = 3;
                nextLast = 4;
            } else {
                if (nextLast - 1 == -1) {
                    nextLast = items.length - 1;
                } else {
                    nextLast--;
                }
            }
        }
    }

    //changes userFirst or userLast based on which remove function is being called
    private void changeUserRemove(String s) {
        if (s.equals("first")) {
            if (size == 0) {
                userFirst = 3;
                userLast = 4;
            } else {
                if (userFirst + 1 == items.length) {
                    userFirst = 0;
                } else {
                    userFirst++;
                }
            }
        } else if (s.equals("last")) {
            if (size == 0) {
                userFirst = 3;
                userLast = 4;
            } else {
                if (userLast - 1 == -1) {
                    userLast = items.length - 1;
                } else {
                    userLast--;
                }
            }
        }
    }

    //checks if array needs to be downsized based on memory constraints
    private boolean resizeDownRequired() {
        if (items.length >= 16) {
            if ((double) size / (double) items.length < 0.25) {
                return true;
            }
        }
        return false;
    }

    //downsizes by reducing array to half its length
    private void resizeDownHelper() {
        T[] returnArray = (T[]) new Object[items.length / 2];
        for (int x = 0; x < size; x++) {
            returnArray[x] = get(x);
        }
        items = returnArray;
        nextFirst = items.length - 1;
        nextLast = size;
        userFirst = 0;
        userLast = size - 1;
>>>>>>> d12afe5ad0f93eb06f5fca6e87af3aa45090dcec
    }

    @Override
    public T get(int index) {
<<<<<<< HEAD
        if(values.length-1-head>=index){
            return values[head+index];
        }
        else{return values[index-values.length-1-head-1];}
    }

    @Override
    public boolean equals(Object o){
        if(o instanceof ArrayDeque){
        if(this.size==((ArrayDeque<?>) o).size){
            for(int tracker=0;tracker<size;){
                if(this.get(tracker)==((ArrayDeque<?>) o).get(tracker)){tracker++;}
                else {return false;}
            }return true;
        }
        else {return false;}
    }
    else {
        if(o instanceof LinkedListDeque){
            if(((LinkedListDeque<?>) o).size()==this.size){
                    for(int tracker=0;tracker<size;){
                        if(this.get(tracker)==((LinkedListDeque<?>) o).get(tracker)){tracker++;}
                        else{return false;}
                    } return true;
                }
                else{return false;}
            }
            else{return false;}
        }
else {
            if(o instanceof ArrayDeque){
                if(this.size==((ArrayDeque<?>) o).size){
                    for(int tracker=0;tracker<size;){
                        if(this.get((tracker))==((ArrayDeque<?>) o).get(tracker)){tracker++;}
                        else{return false;}
                    } return true;
                }
                else{return false;}
            }
    }
    }
=======
        if (userFirst + index >= items.length) {
            return items[userFirst + index - items.length];
        } else {
            return items[userFirst + index];
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof ArrayDeque) {
            if (this.size == ((ArrayDeque<?>) o).size) {
                int tracker = 0;
                while (tracker < size) {
                    if (this.get(tracker) == ((ArrayDeque<?>) o).get(tracker)) {
                        tracker++;
                    } else {
                        return false;
                    }
                }
                return true;
            } else {
                return false;
            }
        } else {
            if (o instanceof LinkedListDeque) {
                if (((LinkedListDeque<?>) o).size()==this.size) {
                    int tracker = 0;
                    while (tracker < this.size) {
                        T a = this.get(tracker);
                        T b = ((LinkedListDeque<T>) o).get(tracker);
                        if (a.equals(b)) {
                            tracker++;
                        } else {
                            return false;
                        }
                    }
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }
    }
}
>>>>>>> d12afe5ad0f93eb06f5fca6e87af3aa45090dcec

