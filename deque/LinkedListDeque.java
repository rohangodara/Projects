package deque;

public class LinkedListDeque<T> implements Deque<T> {
<<<<<<< HEAD
    public class TNode {
=======
    private class TNode {
>>>>>>> d12afe5ad0f93eb06f5fca6e87af3aa45090dcec
        public T item;
        public TNode prev;
        public TNode next;

<<<<<<< HEAD
        public TNode(T item, TNode prev, TNode next){
            this.item=item;
            this.prev=prev;
            this.next=next;
        }
    }
    public TNode sentinel;
    private int size;

    public LinkedListDeque(){
        sentinel=new TNode(null,null,null);
        sentinel.next=sentinel;
        sentinel.prev=sentinel;
        size=0;
    }

    @Override
    public void addFirst(T item){
        sentinel.next= new TNode(item,sentinel,sentinel.next);
        sentinel.next.next.prev= sentinel.next;
                size++;
    }

    @Override
    public void addLast(T item){
        sentinel.prev= new TNode(item,sentinel.prev,sentinel);
        sentinel.prev.prev.next=sentinel.prev;
        size++;
    }


    public int size(){
        return size;
    }

    public void printDeque(){
        TNode a= this.sentinel.next;
        while(a.next.next!=sentinel){System.out.print(a+" ");a=a.next;}
        System.out.println(a.item);
    }

    public T removeFirst(){
        if(size==0){return null;}
        TNode a = sentinel.next;
    sentinel.next=sentinel.next.next;
    sentinel.next.prev=sentinel;
    size--;
    return a.item;
    }

    public T removeLast(){
        if(size==0){return null;}
        TNode a = sentinel.prev;
        sentinel.prev=sentinel.prev.prev;
        sentinel.prev.next=sentinel;
        size--;
        return a.item;
    }

    public T get(int index){
        //sentinel 1 2 3
        if(index>=size){return null;}
        TNode a = sentinel.next;
        for(int tracker=0; tracker<index;tracker++){
            a=a.next;
        }
        return a.item;
    }

    public T getRecursive(int index){
        if(index>=size){return null;}
        return helpgetRecursive(index,0,this.sentinel.next);
    }

    public T helpgetRecursive(int index, int tracker, TNode t){
        if(index==tracker){return t.item;}
        else {tracker=tracker+1; TNode a = t.next;
            return helpgetRecursive(index,tracker,a);}
    }

    public boolean equals(Object o){
    if(o instanceof LinkedListDeque){
        if(((LinkedListDeque<T>) o).size==this.size){
            for(int tracker=0;tracker<this.size;){
                if(this.get(tracker)==((LinkedListDeque<?>) o).get(tracker)){tracker++;}
                else {return false;}
            }
            return true;
        }
        else {return false;}
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
            else{return false;}
        }

    }

}
//am i referencing unneeded things that take memory
//if im doing a=this.next and stuff am i changing deque
=======
        public TNode(T i, TNode n, TNode p) {
            item = i;
            next = n;
            prev = p;
        }
    }

    private TNode sentinel;
    private int size;

    public LinkedListDeque() {
        sentinel = new TNode(null, null, null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;
    }

    @Override
    public void addFirst(T item) {
        size = size + 1;
        sentinel.next = new TNode(item, sentinel.next, sentinel);
        sentinel.next.next.prev = sentinel.next;
    }

    @Override
    public void addLast(T item) {
        size = size + 1;
        sentinel.prev = new TNode(item, sentinel, sentinel.prev);
        sentinel.prev.prev.next = sentinel.prev;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        TNode current = sentinel;
        if (current == null) {
            System.out.print("Null List");
        } else {
            current = sentinel.next;
            while (current.next != sentinel) {
                System.out.print(current.item + " ");
                current = current.next;
            }
        }
    }

    @Override
    public T removeFirst() {
        if (size == 0) {
            return null;
        } else {
            size = size - 1;
            T returnVal = sentinel.next.item;
            sentinel.next = sentinel.next.next;
            sentinel.next.prev = sentinel;
            return returnVal;
        }
    }

    @Override
    public T removeLast() {
        if (size == 0) {
            return null;
        } else {
            size = size - 1;
            T returnVal = sentinel.prev.item;
            sentinel.prev = sentinel.prev.prev;
            sentinel.prev.next = sentinel;
            return returnVal;
        }
    }

    @Override
    public T get(int index) {
        if (index >= size) {
            return null;
        } else {
            TNode current;
            current = sentinel.next;
            int workingindex = 0;
            while (workingindex < index) {
                current = current.next;
                workingindex += 1;
            }
            return current.item;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof LinkedListDeque) {
            if (((LinkedListDeque<T>) o).size == this.size) {
                int tracker = 0;
                while (tracker < this.size) {
                    if (this.get(tracker) == ((LinkedListDeque<?>) o).get(tracker)){
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
            if (o instanceof ArrayDeque) {
                if (this.size == ((ArrayDeque<?>) o).size()) {
                    int tracker = 0;
                    while (tracker < size) {
                        T a = this.get((tracker));
                        T b = (T) ((ArrayDeque<?>) o).get(tracker);
                        if (a.equals(b)) {
                            tracker++;
                        } else {
                            return false;
                        }
                    } return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }
    }

    private T recursiveHelper(TNode node, int reqIndex, int currentIndex) {
        if (reqIndex == currentIndex) {
            return node.item;
        } else {
            return recursiveHelper(node.next, reqIndex, currentIndex + 1);
        }
    }

    public T getRecursive(int index) {
        if (index >= size) {
            return null;
        } else {
            return recursiveHelper(sentinel.next, index, 0);
        }
    }
}
>>>>>>> d12afe5ad0f93eb06f5fca6e87af3aa45090dcec
