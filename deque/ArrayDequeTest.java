package deque;

import org.junit.Test;
import static org.junit.Assert.*;

/* Performs some basic array deque tests. */
public class ArrayDequeTest {

    /** You MUST use the variable below for all of your tests. If you test
     * using a local variable, and not this static variable below, the
     * autograder will not grade that test. If you would like to test
     * ArrayDeques with types other than Integer (and you should),
     * you can define a new local variable. However, the autograder will
     * not grade that test. */

    public static Deque<Integer> ad = new ArrayDeque<Integer>();

    @Test
    public void addFirstTest() {
        ad = new ArrayDeque<Integer>();
        ad.addFirst(1);
        assertEquals(1, ad.size());
        ad = new ArrayDeque<Integer>();

        for (int i = 0; i <= 20; i++) {
            ad.addFirst(i);
<<<<<<< HEAD
            System.out.println(i+" test");
        }
        assertEquals(21, ad.size());
        ad = new ArrayDeque<Integer>();

=======
        }
        assertEquals(21, ad.size());
        ad = new ArrayDeque<Integer>();
    }

    @Test
    public void addFest2() {
        ad = new ArrayDeque<Integer>();
        ad.addLast(0);
        ad.isEmpty();
        ad.removeFirst();
        ad.addLast(3);
        ad.removeFirst();
        ad.addLast(5);
        ad.removeFirst();
        ad.isEmpty();
        ad.isEmpty();
        ad.addLast(9);
        ad.removeFirst();
>>>>>>> d12afe5ad0f93eb06f5fca6e87af3aa45090dcec
    }

    @Test
    public void addLastTest() {
        ad.addLast(1);
        assertEquals(1, ad.size());
        ad = new ArrayDeque<Integer>();

        for (int i = 0; i <= 20; i++) {
            ad.addLast(i);
        }
        assertEquals(21, ad.size());
        ad.printDeque();
        ad = new ArrayDeque<Integer>();
    }

    @Test
    public void isEmptyTest() {
        ad = new ArrayDeque<Integer>();
        assertTrue(ad.isEmpty());
        for (int i = 0; i <= 20; i++) {
            ad.addLast(i);
        }
        assertFalse(ad.isEmpty());

    }

    @Test
    public void sizeTest() {
        ad = new ArrayDeque<Integer>();
        ad.addFirst(1);
        assertEquals(1, ad.size());
        ad = new ArrayDeque<Integer>();

        for (int i = 0; i <= 20; i++) {
            ad.addFirst(i);
        }
        assertEquals(21, ad.size());
    }

    @Test
    public void printDequeTest() {
        ad = new ArrayDeque<Integer>();

        for (int i = 0; i <= 20; i++) {
            ad.addLast(i);
        }
        assertEquals(21, ad.size());
        ad.printDeque();
        ad = new ArrayDeque<Integer>();
    }

    @Test
    public void removeFirstTest() {
        ad = new ArrayDeque<Integer>();
        for (int i = 0; i < 20; i++) {
            ad.addLast(i);
        }
        ad.printDeque();
        ad.removeFirst();
        assertEquals(19, ad.size());
    }

    @Test
    public void removeLastTest() {
        ad = new ArrayDeque<Integer>();
        for (int i = 0; i < 20; i++) {
            ad.addLast(i);
        }
        ad.printDeque();
        ad.removeLast();
        assertEquals(19, ad.size());
    }

<<<<<<< HEAD

=======
>>>>>>> d12afe5ad0f93eb06f5fca6e87af3aa45090dcec
    @Test
    public void getTest() {
        ad = new ArrayDeque<Integer>();

        for (int i = 20; i >=0; i--) {
            ad.addFirst(i);
        }
        assertEquals((Integer) 19, ad.get(19));
        ad.printDeque();
    }

<<<<<<< HEAD
=======
    @Test
    public void resizedownTest() {
        ArrayDeque<Integer> ad1 = new ArrayDeque<Integer>();
        for (int i = 0; i < 16; i ++) {
            ad1.addLast(i);
        }
        for (int i = 16; i > 1; i --) {
            ad1.removeLast();
        }
        ad1.printDeque();
    }

    @Test
    public void equalsTest() {
        ad = new ArrayDeque<Integer>();
        ArrayDeque<String> adString = new ArrayDeque<String>();
        ArrayDeque<Integer> ad2 = new ArrayDeque<Integer>();
        ArrayDeque<String> adString2 = new ArrayDeque<String>();
        ad.addLast(5);
        adString.addLast("test");
        assertEquals(false, ad.equals(adString));
        assertEquals(true, ad.equals(ad));

        ad = new ArrayDeque<Integer>();

        ad.addLast(1);
        ad.addLast(2);
        ad.addLast(3);
        ad2.addLast(1);
        ad2.addLast(2);
        ad2.addLast(3);
        assertEquals(true, ad.equals(ad2));

        ad2.removeLast();
        ad2.addLast(4);
        assertEquals(false, ad.equals(ad2));

        ad = new ArrayDeque<Integer>();
        ad2 = new ArrayDeque<Integer>();
        for (int i = 0; i < 100; i++) {
            ad.addLast(i);
            ad2.addLast(i);
        }
        assertEquals(true, ad.equals(ad2));
        ad2.removeLast();
        assertEquals(false, ad.equals(ad2));
    }

    @Test
    public void equalsADtoLLDTest() {
        ad = new ArrayDeque<Integer>();
        LinkedListDeque<Integer> lld= new LinkedListDeque<Integer>();
        assertEquals(true, ad.equals(lld));

        for (int i = 0; i < 4000; i++) {
            lld.addLast(i);
            ad.addLast(i);
        }
        assertEquals(true, ad.equals(lld));
        lld.removeLast();
        assertEquals(false, ad.equals(lld));
    }

    }




>>>>>>> d12afe5ad0f93eb06f5fca6e87af3aa45090dcec

