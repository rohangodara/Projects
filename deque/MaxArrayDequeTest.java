package deque;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Comparator;

public class MaxArrayDequeTest<T> {

    private Comparator<Integer> comparer = new CompareTest();

    @Test
    public void addFirstTest() {
        MaxArrayDeque<Integer> ad = new MaxArrayDeque<Integer>(comparer);
        ad.addFirst(1);
        ad.addFirst(2);
        assertEquals((Integer) 1, (Integer) ad.max());


    }


}


