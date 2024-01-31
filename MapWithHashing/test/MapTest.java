import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.map.Map;

/**
 * JUnit test fixture for {@code Map<String, String>}'s constructor and kernel
 * methods.
 *
 * @author JT Vendetti & Selin Kirbas
 *
 */
public abstract class MapTest {

    /**
     * Invokes the appropriate {@code Map} constructor for the implementation
     * under test and returns the result.
     *
     * @return the new map
     * @ensures constructorTest = {}
     */
    protected abstract Map<String, String> constructorTest();

    /**
     * Invokes the appropriate {@code Map} constructor for the reference
     * implementation and returns the result.
     *
     * @return the new map
     * @ensures constructorRef = {}
     */
    protected abstract Map<String, String> constructorRef();

    /**
     *
     * Creates and returns a {@code Map<String, String>} of the implementation
     * under test type with the given entries.
     *
     * @param args
     *            the (key, value) pairs for the map
     * @return the constructed map
     * @requires <pre>
     * [args.length is even]  and
     * [the 'key' entries in args are unique]
     * </pre>
     * @ensures createFromArgsTest = [pairs in args]
     */
    private Map<String, String> createFromArgsTest(String... args) {
        assert args.length % 2 == 0 : "Violation of: args.length is even";
        Map<String, String> map = this.constructorTest();
        for (int i = 0; i < args.length; i += 2) {
            assert !map.hasKey(args[i]) : ""
                    + "Violation of: the 'key' entries in args are unique";
            map.add(args[i], args[i + 1]);
        }
        return map;
    }

    /**
     *
     * Creates and returns a {@code Map<String, String>} of the reference
     * implementation type with the given entries.
     *
     * @param args
     *            the (key, value) pairs for the map
     * @return the constructed map
     * @requires <pre>
     * [args.length is even]  and
     * [the 'key' entries in args are unique]
     * </pre>
     * @ensures createFromArgsRef = [pairs in args]
     */
    private Map<String, String> createFromArgsRef(String... args) {
        assert args.length % 2 == 0 : "Violation of: args.length is even";
        Map<String, String> map = this.constructorRef();
        for (int i = 0; i < args.length; i += 2) {
            assert !map.hasKey(args[i]) : ""
                    + "Violation of: the 'key' entries in args are unique";
            map.add(args[i], args[i + 1]);
        }
        return map;
    }

    /**
     * Test for constructor.
     */
    @Test
    public void testConstructor() {
        /*
         * Set up variables
         */
        Map<String, String> s = this.createFromArgsTest();
        Map<String, String> sExpected = this.createFromArgsRef();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected, s);
    }

    /**
     * Test for empty add.
     */
    @Test
    public void testAddEmpty() {
        /*
         * Set up variables
         */
        Map<String, String> s = this.createFromArgsTest();
        Map<String, String> sExpected = this.createFromArgsRef("yellow",
                "orange");
        /*
         * Call method under test
         */
        s.add("yellow", "orange");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected, s);

    }

    /**
     * Test for add with multiple pairs.
     */
    @Test
    public void testAddMultiple() {
        /*
         * Set up variables
         */
        Map<String, String> s = this.createFromArgsTest("blue", "purple",
                "green", "red");
        Map<String, String> sExpected = this.createFromArgsRef("yellow",
                "orange", "blue", "purple", "green", "red");
        /*
         * Call method under test
         */
        s.add("yellow", "orange");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected, s);

    }

    /**
     * Test for remove to empty map.
     */
    @Test
    public void testRemoveEmpty() {
        /*
         * Set up variables
         */
        Map<String, String> s = this.createFromArgsTest("blue", "purple");
        Map<String, String> sExpected = this.createFromArgsRef();
        /*
         * Call method under test
         */
        s.remove("blue");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected, s);
    }

    /**
     * Test for remove with multiple pairs.
     */
    @Test
    public void testRemoveMultiple() {
        /*
         * Set up variables
         */
        Map<String, String> s = this.createFromArgsTest("yellow", "orange",
                "blue", "purple", "green", "red");
        Map<String, String> sExpected = this.createFromArgsRef("yellow",
                "orange", "blue", "purple");
        /*
         * Call method under test
         */
        s.remove("green");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected, s);
    }

    /**
     * Test removeAny with one pair.
     */
    @Test
    public void testRemoveAnyOne() {
        /*
         * Set up variables
         */
        Map<String, String> s = this.createFromArgsTest("yellow", "orange");
        Map<String, String> sExpected = this.createFromArgsRef("yellow",
                "orange");
        int size = s.size();
        /*
         * Call method under test
         */
        Map.Pair<String, String> removed = s.removeAny();
        /*
         * Remove value in expected
         */
        Map.Pair<String, String> storeRemoved = sExpected.remove(removed.key());
        /*
         * Assert values match expectations
         */
        assertEquals(sExpected, s);
        assertEquals(size - 1, s.size());
    }

    /**
     * Test removeAny with multiple pairs.
     */
    @Test
    public void testRemoveAnyMultiple() {
        /*
         * Set up variables
         */
        Map<String, String> s = this.createFromArgsTest("yellow", "orange",
                "blue", "purple", "green", "red");
        Map<String, String> sExpected = this.createFromArgsRef("yellow",
                "orange", "blue", "purple", "green", "red");
        int size = s.size();
        /*
         * Call method under test
         */
        Map.Pair<String, String> removed = s.removeAny();
        /*
         * Remove value in expected
         */
        Map.Pair<String, String> storeRemoved = sExpected.remove(removed.key());
        /*
         * Assert values match expectations
         */
        assertEquals(sExpected, s);
        assertEquals(size - 1, s.size());
    }

    /**
     * Test for value.
     */
    @Test
    public void testValue() {
        /*
         * Set up variables
         */
        Map<String, String> s = this.createFromArgsTest("yellow", "orange",
                "blue", "purple", "green", "red");
        /*
         * Call method under test
         */
        String val = s.value("blue");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals("purple", val);
    }

    /**
     * Test hasKey for empty map (false).
     */
    @Test
    public void testHasKeyEmpty() {
        /*
         * Set up variables
         */
        Map<String, String> s = this.createFromArgsTest();
        /*
         * Call method under test
         */
        boolean hasKey = s.hasKey("pink");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(false, hasKey);
    }

    /**
     * Test for hasKey (true).
     */
    @Test
    public void testHasKeyT() {
        /*
         * Set up variables
         */
        Map<String, String> s = this.createFromArgsTest("pink", "white",
                "black", "cyan");
        /*
         * Call method under test
         */
        boolean hasKey = s.hasKey("black");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(true, hasKey);
    }

    /**
     * Test for hasKey (false).
     */
    @Test
    public void testHasKeyF() {
        /*
         * Set up variables
         */
        Map<String, String> s = this.createFromArgsTest("pink", "white",
                "black", "cyan");
        /*
         * Call method under test
         */
        boolean hasKey = s.hasKey("blue");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(false, hasKey);
    }

    /**
     * Test for size with empty map.
     */
    @Test
    public void testSizeEmpty() {
        /*
         * Set up variables
         */
        Map<String, String> s = this.createFromArgsTest();
        /*
         * Call method under test
         */
        int size = s.size();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(0, size);
    }

    /**
     * Test for size with one pair.
     */
    @Test
    public void testSizeOne() {
        /*
         * Set up variables
         */
        Map<String, String> s = this.createFromArgsTest("pink", "white");
        /*
         * Call method under test
         */
        int size = s.size();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(1, size);
    }

    /**
     * Test for size with multiple pairs.
     */
    @Test
    public void testSizeMultiple() {
        /*
         * Set up variables
         */
        Map<String, String> s = this.createFromArgsTest("pink", "white",
                "black", "cyan", "lime", "magenta");
        /*
         * Call method under test
         */
        int size = s.size();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(3, size);
    }

}
