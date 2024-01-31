import static org.junit.Assert.assertEquals;

import java.util.Comparator;

import org.junit.Test;

import components.sortingmachine.SortingMachine;

/**
 * JUnit test fixture for {@code SortingMachine<String>}'s constructor and
 * kernel methods.
 *
 * @author Selin Kirbas & [Removed for privacy]
 *
 */
public abstract class SortingMachineTest {

    /**
     * Invokes the appropriate {@code SortingMachine} constructor for the
     * implementation under test and returns the result.
     *
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @return the new {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures constructorTest = (true, order, {})
     */
    protected abstract SortingMachine<String> constructorTest(
            Comparator<String> order);

    /**
     * Invokes the appropriate {@code SortingMachine} constructor for the
     * reference implementation and returns the result.
     *
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @return the new {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures constructorRef = (true, order, {})
     */
    protected abstract SortingMachine<String> constructorRef(
            Comparator<String> order);

    /**
     *
     * Creates and returns a {@code SortingMachine<String>} of the
     * implementation under test type with the given entries and mode.
     *
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @param insertionMode
     *            flag indicating the machine mode
     * @param args
     *            the entries for the {@code SortingMachine}
     * @return the constructed {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures <pre>
     * createFromArgsTest = (insertionMode, order, [multiset of entries in args])
     * </pre>
     */
    private SortingMachine<String> createFromArgsTest(Comparator<String> order,
            boolean insertionMode, String... args) {
        SortingMachine<String> sm = this.constructorTest(order);
        for (int i = 0; i < args.length; i++) {
            sm.add(args[i]);
        }
        if (!insertionMode) {
            sm.changeToExtractionMode();
        }
        return sm;
    }

    /**
     *
     * Creates and returns a {@code SortingMachine<String>} of the reference
     * implementation type with the given entries and mode.
     *
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @param insertionMode
     *            flag indicating the machine mode
     * @param args
     *            the entries for the {@code SortingMachine}
     * @return the constructed {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures <pre>
     * createFromArgsRef = (insertionMode, order, [multiset of entries in args])
     * </pre>
     */
    private SortingMachine<String> createFromArgsRef(Comparator<String> order,
            boolean insertionMode, String... args) {
        SortingMachine<String> sm = this.constructorRef(order);
        for (int i = 0; i < args.length; i++) {
            sm.add(args[i]);
        }
        if (!insertionMode) {
            sm.changeToExtractionMode();
        }
        return sm;
    }

    /**
     * Comparator<String> implementation to be used in all test cases. Compare
     * {@code String}s in lexicographic order.
     */
    private static class StringLT implements Comparator<String> {

        @Override
        public int compare(String s1, String s2) {
            return s1.compareToIgnoreCase(s2);
        }

    }

    /**
     * Comparator instance to be used in all test cases.
     */
    private static final StringLT ORDER = new StringLT();

    /*
     * Sample test cases.
     */

    /**
     * Test for constructor.
     */
    @Test
    public final void testConstructor() {
        /*
         * Set up variables.
         */
        SortingMachine<String> m = this.constructorTest(ORDER);
        SortingMachine<String> mExpected = this.constructorRef(ORDER);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
    }

    /**
     * Test for add method with empty String.
     */
    @Test
    public final void testAddEmpty() {
        /*
         * Set up variables.
         */
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "green");
        /*
         * Call method under test
         */
        m.add("green");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
    }

    /**
     * Test for add method with non empty String.
     */
    @Test
    public final void testAddNE1() {
        /*
         * Set up variables.
         */
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true,
                "black", "green");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "black", "green", "yellow");
        /*
         * Call method under test
         */
        m.add("yellow");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
    }

    /**
     * Test for add method with non empty + more entries String.
     */
    @Test
    public final void testAddNE2() {
        /*
         * Set up variables.
         */
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true,
                "black", "green", "yellow", "purple", "white", "blue");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "black", "green", "yellow", "purple", "white", "blue", "orange");
        /*
         * Call method under test
         */
        m.add("orange");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
    }

    /**
     * Test for changeToExtractionMode method with empty String.
     */
    @Test
    public final void testChangeToExtractionModeEmpty() {
        /*
         * Set up variables
         */
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false);
        /*
         * Call method to test
         */
        m.changeToExtractionMode();
        /*
         * Assert values are true
         */
        assertEquals(mExpected, m);
    }

    /**
     * Test for changeToExtractionMode method with non empty String.
     */
    @Test
    public final void testChangeToExtractionModeNE1() {
        /*
         * Set up variables
         */
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true,
                "black");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "black");
        /*
         * Call method to test
         */
        m.changeToExtractionMode();
        /*
         * Assert values are true
         */
        assertEquals(mExpected, m);
    }

    /**
     * Test for changeToExtractionMode method with non empty + more entries String.
     */
    @Test
    public final void testChangeToExtractionModeNE2() {
        /*
         * Set up variables
         */
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true,
                "black", "green", "yellow", "purple", "white", "blue");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "black", "green", "yellow", "purple", "white", "blue");
        /*
         * Call method to test
         */
        m.changeToExtractionMode();
        /*
         * Assert values are true
         */
        assertEquals(mExpected, m);
    }

    /**
     * Test for removeFirst method to empty String.
     */
    @Test
    public final void testRemoveFirstEmpty() {
        /*
         * Set up variables
         */
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false,
                "black");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "black");
        /*
         * Call method to test
         */
        String r = m.removeFirst();
        String rExpected = mExpected.removeFirst();
        /*
         * Assert values are true
         */
        assertEquals(mExpected, m);
        assertEquals(rExpected, r);
    }

    /**
     * Test for removeFirst method with non empty String.
     */
    @Test
    public final void testRemoveFirstNE1() {
        /*
         * Set up variables
         */
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false,
                "black", "green", "yellow");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "black", "green", "yellow");
        /*
         * Call method to test
         */
        String r = m.removeFirst();
        String rExpected = mExpected.removeFirst();
        /*
         * Assert values are true
         */
        assertEquals(mExpected, m);
        assertEquals(rExpected, r);
    }

    /**
     * Test for removeFirst method with non empty + more entries String.
     */
    @Test
    public final void testRemoveFirstNE2() {
        /*
         * Set up variables
         */
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false, "black",
                "green", "yellow", "purple", "white", "blue");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "black", "green", "yellow", "purple", "white", "blue");
        /*
         * Call method to test
         */
        String r = m.removeFirst();
        String rExpected = mExpected.removeFirst();
        /*
         * Assert values are true
         */
        assertEquals(mExpected, m);
        assertEquals(rExpected, r);
    }

    /**
     * Test for isInInsertionMode method with empty + true String.
     */
    @Test
    public final void testIsInInsertionModeEmptyT() {
        /*
         * Set up variables
         */
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> mExpected = this.createFromArgsTest(ORDER, true);
        /*
         * Assert values are true
         */
        assertEquals(true, m.isInInsertionMode());
        assertEquals(mExpected, m);
    }

    /**
     * Test for isInInsertionMode method with empty + false String.
     */
    @Test
    public final void testIsInInsertionModeEmptyF() {
        /*
         * Set up variables
         */
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false);
        SortingMachine<String> mExpected = this.createFromArgsTest(ORDER,
                false);
        /*
         * Assert values are true
         */
        assertEquals(false, m.isInInsertionMode());
        assertEquals(mExpected, m);
    }

    /**
     * Test for isInInsertionMode method with non empty + true String.
     */
    @Test
    public final void testIsInInsertionModeNET() {
        /*
         * Set up variables
         */
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "black",
                "green", "yellow");
        SortingMachine<String> mExpected = this.createFromArgsTest(ORDER, true,
                "black", "green", "yellow");
        /*
         * Assert values are true
         */
        assertEquals(true, m.isInInsertionMode());
        assertEquals(mExpected, m);
    }

    /**
     * Test for isInInsertionMode method with non empty + false String.
     */
    @Test
    public final void testIsInInsertionModeNEF() {
        /*
         * Set up variables
         */
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false, "black",
                "green", "yellow");
        SortingMachine<String> mExpected = this.createFromArgsTest(ORDER, false,
                "black", "green", "yellow");
        /*
         * Assert values are true
         */
        assertEquals(false, m.isInInsertionMode());
        assertEquals(mExpected, m);
    }

    /**
     * Test for order method with empty String.
     */
    @Test
    public final void testOrderEmpty() {
        /*
         * Set up variables
         */
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false);
        /*
         * Assert values are true
         */
        assertEquals(mExpected.order(), m.order());
        assertEquals(mExpected, m);
    }

    /**
     * Test for order method with non empty String.
     */
    @Test
    public final void testOrderNE1() {
        /*
         * Set up variables
         */
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false, "black",
                "green", "yellow");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "black", "green", "yellow");
        /*
         * Assert values are true
         */
        assertEquals(mExpected.order(), m.order());
        assertEquals(mExpected, m);
    }

    /**
     * Test for order method with non empty + more entries String.
     */
    @Test
    public final void testOrderNE2() {
        /*
         * Set up variables
         */
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false, "black",
                "green", "yellow", "purple", "white", "blue");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "black", "green", "yellow", "purple", "white", "blue");
        /*
         * Assert values are true
         */
        assertEquals(mExpected.order(), m.order());
        assertEquals(mExpected, m);
    }

    /**
     * Test for size method with empty String.
     */
    @Test
    public final void testSizeEmpty() {
        /*
         * Set up variables.
         */
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
        assertEquals(0, m.size());
    }

    /**
     * Test for size method with non empty String.
     */
    @Test
    public final void testSizeNE1() {
        /*
         * Set up variables.
         */
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true,
                "black", "green", "yellow");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "black", "green", "yellow");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
        assertEquals(3, m.size());
    }

    /**
     * Test for size method with non empty + more entries String.
     */
    @Test
    public final void testSizeNE2() {
        /*
         * Set up variables.
         */
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true,
                "black", "green", "yellow", "purple", "white", "blue", "orange",
                "teal", "red", "gray");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "black", "green", "yellow", "purple", "white", "blue", "orange",
                "teal", "red", "gray");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
        assertEquals(10, m.size());
    }
}
