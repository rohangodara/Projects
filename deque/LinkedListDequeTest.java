package deque;

import org.junit.Test;
import static org.junit.Assert.*;


/** Performs some basic linked list deque tests. */
public class LinkedListDequeTest {

    /** You MUST use the variable below for all of your tests. If you test
     * using a local variable, and not this static variable below, the
     * autograder will not grade that test. If you would like to test
     * LinkedListDeques with types other than Integer (and you should),
     * you can define a new local variable. However, the autograder will
     * not grade that test. */

    public static Deque<Integer> lld = new LinkedListDeque<Integer>();
    public static Deque<String> lldString = new LinkedListDeque<String>();
    public static Deque<Integer> lld2 = new LinkedListDeque<Integer>();
    public static Deque<String> lldString2 = new LinkedListDeque<String>();


    @Test
    /** Adds a few things to the list, checks that isEmpty() is correct.
     * This is one simple test to remind you how junit tests work. You
     * should write more tests of your own.
     *
     * && is the "and" operation. */
<<<<<<< HEAD
    public void addIsEmptySizeTest() {
        assertTrue("A newly initialized LLDeque should be empty", lld.isEmpty());
        lld.addFirst(0);
=======

    public void addFirstTest() {
		assertTrue("A newly initialized LLDeque should be empty", lld.isEmpty());
		lld.addFirst(0);
>>>>>>> d12afe5ad0f93eb06f5fca6e87af3aa45090dcec

        assertFalse("lld should now contain 1 item", lld.isEmpty());

        // Reset the linked list deque at the END of the test.
        lld = new LinkedListDeque<Integer>();

        lld.addFirst(0);
        assertEquals(1, lld.size());

        lld.addFirst(1);
        assertEquals(2, lld.size());

        lld.addFirst(2);
        assertEquals(3, lld.size());

        lld.printDeque();

        lld = new LinkedListDeque<Integer>();
<<<<<<< HEAD
    }

    @Test
    public void removeTest() {
        lld = new LinkedListDeque<Integer>();

        lld.addFirst(2);
        lld.removeFirst();
        assertEquals(0, lld.size());


        lld.addFirst(2);
        lld.removeLast();
        assertEquals(0, lld.size());

        lld.addFirst(2);
        lld.removeLast();
        assertEquals(0, lld.size());

        lld.addLast(2);
        lld.removeLast();
        lld.removeFirst();
        assertEquals(0, lld.size());

        assertEquals(null, lld.removeFirst());

        assertEquals(null, lld.removeLast());
    }

    @Test
    public void equalsTest() {
        lld = new LinkedListDeque<Integer>();
        lldString = new LinkedListDeque<String>();
        lld2 = new LinkedListDeque<Integer>();
        lldString2 = new LinkedListDeque<String>();

        assertEquals(false, lld.equals(lldString));
        assertEquals(true, lld.equals(lld));

        lld.addLast(1);
        lld.addLast(2);
        lld.addLast(3);
        lld2.addLast(1);
        lld2.addLast(2);
        lld2.addLast(3);
        assertEquals(true, lld.equals(lld2));

        lld2.removeLast();
        lld2.addLast(4);
        assertEquals(false, lld.equals(lld2));



    }



=======
    }

    @Test
    public void removeTest() {
        lld = new LinkedListDeque<Integer>();

        lld.addFirst(2);
        lld.removeFirst();
        assertEquals(0, lld.size());


        lld.addFirst(2);
        lld.removeLast();
        assertEquals(0, lld.size());

        lld.addFirst(2);
        lld.removeLast();
        assertEquals(0, lld.size());

        lld.addLast(2);
        lld.removeLast();
        lld.removeFirst();
        assertEquals(0, lld.size());

        assertEquals(null, lld.removeFirst());

        assertEquals(null, lld.removeLast());
    }

    @Test
    public void equalsTest() {
        lld = new LinkedListDeque<Integer>();
        lldString = new LinkedListDeque<String>();
        lld2 = new LinkedListDeque<Integer>();
        lldString2 = new LinkedListDeque<String>();
        lld.addLast(5);
        lldString.addLast("test");
        assertEquals(false, lld.equals(lldString));
        assertEquals(true, lld.equals(lld));

        lld = new LinkedListDeque<Integer>();

        lld.addLast(1);
        lld.addLast(2);
        lld.addLast(3);
        lld2.addLast(1);
        lld2.addLast(2);
        lld2.addLast(3);
        assertEquals(true, lld.equals(lld2));

        lld2.removeLast();
        lld2.addLast(4);
        assertEquals(false, lld.equals(lld2));

        lld = new ArrayDeque<Integer>();
        lld2 = new ArrayDeque<Integer>();
        for (int i = 0; i < 128; i++) {
            lld.addLast(i);
            lld2.addLast(i);
        }
        assertEquals(true, lld.equals(lld2));

        lld.addLast(1);
        lld2.addLast(1);
        assertEquals(true, lld.equals(lld2));
        assertEquals(true, lld.equals(lld2));
        lld2.removeLast();
        assertEquals(false, lld.equals(lld2));
    }

    @Test
    public void equalsADtoLLDTest() {
        ArrayDeque<Integer> ad = new ArrayDeque<Integer>();
        LinkedListDeque<Integer> lld= new LinkedListDeque<Integer>();
        assertEquals(true, ad.equals(lld));

        for (int i = 0; i < 128; i++) {
            lld.addLast(i);
            ad.addLast(i);
        }
        assertEquals(true, lld.equals(ad));
        lld.removeLast();
        assertEquals(false, lld.equals(ad));
    }

    @Test
    public void equalsADtoLLDTest2() {
        ArrayDeque<Integer> ad = new ArrayDeque<Integer>();
        LinkedListDeque<Integer> lld= new LinkedListDeque<Integer>();
        assertEquals(true, ad.equals(lld));

        for (int i = 0; i < 129 ; i++) {
            lld.addLast(i);
            ad.addLast(i);
        }
        int test = 0;
        assertEquals(true, lld.equals(ad));
        lld.removeLast();
        assertEquals(false, lld.equals(ad));
    }

    @Test
    public void addLastTest() {

    }

    @Test
    public void isEmptyTest() {

    }

    @Test
    public void sizeTest() {

    }

    @Test
    public void printDequeTest() {

    }

    @Test
    public void removeFirstTest() {

    }

    @Test
    public void removeLastTest() {

    }

    @Test
    public void getTest() {

    }
>>>>>>> d12afe5ad0f93eb06f5fca6e87af3aa45090dcec
}

