import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import components.set.Set;

/**
 * JUnit test fixture for {@code Set<String>}'s constructor and kernel methods.
 *
 * @author JT Vendetti & Selin Kirbas
 *
 */
public abstract class SetTest {

    /**
     * Invokes the appropriate {@code Set} constructor for the implementation
     * under test and returns the result.
     *
     * @return the new set
     * @ensures constructorTest = {}
     */
    protected abstract Set<String> constructorTest();

    /**
     * Invokes the appropriate {@code Set} constructor for the reference
     * implementation and returns the result.
     *
     * @return the new set
     * @ensures constructorRef = {}
     */
    protected abstract Set<String> constructorRef();

    /**
     * Creates and returns a {@code Set<String>} of the implementation under
     * test type with the given entries.
     *
     * @param args
     *            the entries for the set
     * @return the constructed set
     * @requires [every entry in args is unique]
     * @ensures createFromArgsTest = [entries in args]
     */
    private Set<String> createFromArgsTest(String... args) {
        Set<String> set = this.constructorTest();
        for (String s : args) {
            assert !set.contains(
                    s) : "Violation of: every entry in args is unique";
            set.add(s);
        }
        return set;
    }

    /**
     * Creates and returns a {@code Set<String>} of the reference implementation
     * type with the given entries.
     *
     * @param args
     *            the entries for the set
     * @return the constructed set
     * @requires [every entry in args is unique]
     * @ensures createFromArgsRef = [entries in args]
     */
    private Set<String> createFromArgsRef(String... args) {
        Set<String> set = this.constructorRef();
        for (String s : args) {
            assert !set.contains(
                    s) : "Violation of: every entry in args is unique";
            set.add(s);
        }
        return set;
    }

    /**
     * Test for constructor.
     */
    @Test
    public void testConstructor() {
        /*
         * Set up variables
         */
        Set<String> str = this.constructorTest();
        Set<String> strExpected = this.constructorRef();
        /*
         * Assert values match expectations
         */
        assertEquals(strExpected, str);
    }

    /**
     * Test for add with empty Set.
     */
    @Test
    public void testAddEmpty() {
        /*
         * Set up variables
         */
        Set<String> str = this.createFromArgsTest();
        Set<String> strExpected = this.createFromArgsRef("one");
        /*
         * Call method under test
         */
        str.add("one");
        /*
         * Assert values match expectations
         */
        assertEquals(strExpected, str);
    }

    /**
     * Test for add with non-empty Set.
     */
    @Test
    public void testAddNE() {
        /*
         * Set up variables
         */
        Set<String> str = this.createFromArgsTest("one", "two", "three");
        Set<String> strExpected = this.createFromArgsRef("one", "two",
                "three", "four");
        /*
         * Call method under test
         */
        str.add("four");
        /*
         * Assert values match expectations
         */
        assertEquals(strExpected, str);
    }

    /**
     * Test for add with non-empty Set resulting in all right subtrees.
     */
    @Test
    public void testAddNE2() {
        /*
         * Set up variables
         */
        Set<String> str = this.createFromArgsTest("anteater", "bear", "cheetah");
        Set<String> strExpected = this.createFromArgsRef("anteater", "bear",
                "cheetah", "dog");
        /*
         * Call method under test
         */
        str.add("dog");
        /*
         * Assert values match expectations
         */
        assertEquals(strExpected, str);
    }

    /**
     * Test for add with non-empty Set resulting in all left subtrees.
     */
    @Test
    public void testAddNE3() {
        /*
         * Set up variables
         */
        Set<String> str = this.createFromArgsTest("zebra", "yak", "xerus",
                "walrus", "vulture");
        Set<String> strExpected = this.createFromArgsRef("zebra", "yak", "xerus",
                "walrus", "vulture", "urchin");
        /*
         * Call method under test
         */
        str.add("urchin");
        /*
         * Assert values match expectations
         */
        assertEquals(strExpected, str);
    }

    /**
     * Test for add with non-empty Set resulting in both left and right subtrees.
     */
    @Test
    public void testAddNE4() {
        /*
         * Set up variables
         */
        Set<String> str = this.createFromArgsTest("lion", "hare", "sheep",
                "deer", "jellyfish", "python", "turtle");
        Set<String> strExpected = this.createFromArgsRef("lion", "hare", "sheep",
                "deer", "jellyfish", "python", "turtle", "weasel");
        /*
         * Call method under test
         */
        str.add("weasel");
        /*
         * Assert values match expectations
         */
        assertEquals(strExpected, str);
    }

    /**
     * Test for remove with non-empty to empty Set.
     */
    @Test
    public void testRemoveNEToEmpty() {
        /*
         * Set up variables
         */
        Set<String> str = this.createFromArgsTest("one");
        Set<String> strExpected = this.createFromArgsRef();

        String removed = "one";
        /*
         * Call method under test
         */
        String s = str.remove("one");
        /*
         * Assert values match expectations
         */
        assertEquals(strExpected, str);
        assertEquals(removed, s);
    }

    /**
     * Test for remove with non-empty Set.
     */
    @Test
    public void testRemoveNE() {
        /*
         * Set up variables
         */
        Set<String> str = this.createFromArgsTest("black", "white", "gray");
        Set<String> strExpected = this.createFromArgsRef("black", "white");

        String removed = "gray";
        /*
         * Call method under test
         */
        String s = str.remove("gray");
        /*
         * Assert values match expectations
         */
        assertEquals(strExpected, str);
        assertEquals(removed, s);
    }

    /**
     * Test for remove with non-empty Set for all right subtrees, remove from end.
     */
    @Test
    public void testRemoveNE2() {
        /*
         * Set up variables
         */
        Set<String> str = this.createFromArgsTest("cereal", "egg", "menemen",
                "oatmeal", "scone");
        Set<String> strExpected = this.createFromArgsRef("cereal", "egg", "menemen",
                "oatmeal");

        String removed = "scone";
        /*
         * Call method under test
         */
        String s = str.remove("scone");
        /*
         * Assert values match expectations
         */
        assertEquals(strExpected, str);
        assertEquals(removed, s);
    }

    /**
     * Test for remove with non-empty Set for all right subtrees, remove from beginning.
     */
    @Test
    public void testRemoveNE3() {
        /*
         * Set up variables
         */
        Set<String> str = this.createFromArgsTest("bagel", "cereal", "egg", "menemen",
                "oatmeal", "scone");
        Set<String> strExpected = this.createFromArgsRef("cereal", "egg", "menemen",
                "oatmeal", "scone");

        String removed = "bagel";
        /*
         * Call method under test
         */
        String s = str.remove("bagel");
        /*
         * Assert values match expectations
         */
        assertEquals(strExpected, str);
        assertEquals(removed, s);
    }

    /**
     * Test for remove with non-empty Set for all left subtrees, remove from end.
     */
    @Test
    public void testRemoveNE4() {
        /*
         * Set up variables
         */
        Set<String> str = this.createFromArgsTest("waffle", "simit", "porridge",
                "omelette");
        Set<String> strExpected = this.createFromArgsRef("waffle", "simit", "porridge");

        String removed = "omelette";
        /*
         * Call method under test
         */
        String s = str.remove("omelette");
        /*
         * Assert values match expectations
         */
        assertEquals(strExpected, str);
        assertEquals(removed, s);
    }

    /**
     * Test for remove with non-empty Set for all left subtrees, remove from beginning.
     */
    @Test
    public void testRemoveNE5() {
        /*
         * Set up variables
         */
        Set<String> str = this.createFromArgsTest("yogurt", "waffle", "simit", "porridge",
                "omelette");
        Set<String> strExpected = this.createFromArgsRef("waffle", "simit", "porridge",
                "omelette");

        String removed = "yogurt";
        /*
         * Call method under test
         */
        String s = str.remove("yogurt");
        /*
         * Assert values match expectations
         */
        assertEquals(strExpected, str);
        assertEquals(removed, s);
    }

    /**
     * Test for remove with non-empty Set for left & right subtrees,
     * remove from end.
     */
    @Test
    public void testRemoveNE6() {
        /*
         * Set up variables
         */
        Set<String> str = this.createFromArgsTest("menemen", "egg", "porridge", "apple",
                "frittata", "omelette", "waffle");
        Set<String> strExpected = this.createFromArgsRef("menemen", "egg",
                "porridge", "apple", "frittata", "omelette");

        String removed = "waffle";
        /*
         * Call method under test
         */
        String s = str.remove("waffle");
        /*
         * Assert values match expectations
         */
        assertEquals(strExpected, str);
        assertEquals(removed, s);
    }

    /**
     * Test for remove with non-empty Set for left & right subtrees,
     * remove from middle.
     */
    @Test
    public void testRemoveNE7() {
        /*
         * Set up variables
         */
        Set<String> str = this.createFromArgsTest("menemen", "egg", "porridge", "apple",
                "frittata", "omelette", "waffle");
        Set<String> strExpected = this.createFromArgsRef("menemen", "egg",
                "porridge", "frittata", "omelette", "waffle");

        String removed = "apple";
        /*
         * Call method under test
         */
        String s = str.remove("apple");
        /*
         * Assert values match expectations
         */
        assertEquals(strExpected, str);
        assertEquals(removed, s);
    }

    /**
     * Test for remove with non-empty Set for left & right subtrees,
     * remove from beginning.
     */
    @Test
    public void testRemoveNE8() {
        /*
         * Set up variables
         */
        Set<String> str = this.createFromArgsTest("menemen", "egg", "porridge", "apple",
                "frittata", "omelette", "waffle");
        Set<String> strExpected = this.createFromArgsRef("egg", "porridge", "apple",
                "frittata", "omelette", "waffle");

        String removed = "menemen";
        /*
         * Call method under test
         */
        String s = str.remove("menemen");
        /*
         * Assert values match expectations
         */
        assertEquals(strExpected, str);
        assertEquals(removed, s);
    }

    /**
     * Test for removeAny with non-empty to empty Set.
     */
    @Test
    public void testRemoveAnyNEToEmpty() {
        /*
         * Set up variables
         */
        Set<String> str = this.createFromArgsTest("black");
        Set<String> strExpected = this.createFromArgsRef("black");
        /*
         * Call method under test
         */
        String s = str.removeAny();
        String removed = "black";
        /*
         * Assert values match expectations
         */
        assertTrue(strExpected.contains(s));
        strExpected.remove(s);
        assertEquals(strExpected, str);
        assertEquals(removed, s);
    }

    /**
     * Test for removeAny with non-empty Set for all right subtrees.
     */
    @Test
    public void testRemoveAnyNE() {
        /*
         * Set up variables
         */
        Set<String> str = this.createFromArgsTest("date", "guava", "kiwi");
        Set<String> strExpected = this.createFromArgsRef("date", "guava", "kiwi");
        /*
         * Call method under test
         */
        String s = str.removeAny();
        /*
         * Assert values match expectations
         */
        assertTrue(strExpected.contains(s));
        String removed = strExpected.remove(s);
        assertEquals(strExpected, str);
        assertEquals(removed, s);
    }

    /**
     * Test for removeAny with non-empty Set for all left subtrees.
     */
    @Test
    public void testRemoveAnyNE2() {
        /*
         * Set up variables
         */
        Set<String> str = this.createFromArgsTest("tangerine", "pomegranate", "orange",
                "mango", "grape");
        Set<String> strExpected = this.createFromArgsRef("tangerine", "pomegranate",
                "orange", "mango", "grape");
        /*
         * Call method under test
         */
        String s = str.removeAny();
        /*
         * Assert values match expectations
         */
        assertTrue(strExpected.contains(s));
        String removed = strExpected.remove(s);
        assertEquals(strExpected, str);
        assertEquals(removed, s);
    }

    /**
     * Test for removeAny with non-empty Set for both left & right subtrees.
     */
    @Test
    public void testRemoveAnyNE3() {
        /*
         * Set up variables
         */
        Set<String> str = this.createFromArgsTest("melon", "date", "pomegranate", "apple",
                "kiwi", "nectarine", "raspberry");
        Set<String> strExpected = this.createFromArgsRef("melon", "date", "pomegranate",
                "apple", "kiwi", "nectarine", "raspberry");
        /*
         * Call method under test
         */
        String s = str.removeAny();
        /*
         * Assert values match expectations
         */
        assertTrue(strExpected.contains(s));
        String removed = strExpected.remove(s);
        assertEquals(strExpected, str);
        assertEquals(removed, s);
    }

    /**
     * Test for contains with empty Set.
     */
    @Test
    public void testContainsEmpty() {
        /*
         * Set up variables
         */
        Set<String> str = this.createFromArgsTest();
        Set<String> strExpected = this.createFromArgsRef();
        /*
         * Call method under test
         */
        boolean result = str.contains("black");
        /*
         * Assert values match expectations
         */
        assertEquals(false, result);
        assertEquals(strExpected, str);
    }

    /**
     * Test for contains with non-empty Set (false).
     */
    @Test
    public void testContainsNEFalse() {
        /*
         * Set up variables
         */
        Set<String> str = this.createFromArgsTest("black", "white", "gray",
                "blue", "purple");
        Set<String> strExpected = this.createFromArgsRef("black", "white", "gray",
                "blue", "purple");
        /*
         * Call method under test
         */
        boolean result = str.contains("five");
        /*
         * Assert values match expectations
         */
        assertEquals(false, result);
        assertEquals(strExpected, str);
    }

    /**
     * Test for contains with non-empty Set (true).
     */
    @Test
    public void testContainsTrue() {
        /*
         * Set up variables
         */
        Set<String> str = this.createFromArgsTest("black", "white", "gray",
                "blue", "purple");
        Set<String> strExpected = this.createFromArgsRef("black", "white", "gray",
                "blue", "purple");
        /*
         * Call method under test
         */
        boolean result = str.contains("blue");
        /*
         * Assert values match expectations
         */
        assertEquals(true, result);
        assertEquals(strExpected, str);
    }

    /**
     * Test for size with empty Set.
     */
    @Test
    public void testSizeEmpty() {
        /*
         * Set up variables
         */
        Set<String> str = this.createFromArgsTest();
        Set<String> strExpected = this.createFromArgsRef();
        /*
         * Call method under test
         */
        int size = str.size();
        /*
         * Assert values match expectations
         */
        assertEquals(0, size);
        assertEquals(strExpected, str);
    }

    /**
     * Test for size with non-empty Set (small).
     */
    @Test
    public void testSizeNESm() {
        /*
         * Set up variables
         */
        Set<String> str = this.createFromArgsTest("black");
        Set<String> strExpected = this.createFromArgsRef("black");
        /*
         * Call method under test
         */
        int size = str.size();
        /*
         * Assert values match expectations
         */
        assertEquals(1, size);
        assertEquals(strExpected, str);
    }

    /**
     * Test for size with non-empty Set (medium).
     */
    @Test
    public void testSizeNEMed() {
        /*
         * Set up variables
         */
        Set<String> str = this.createFromArgsTest("black", "white", "gray",
                "blue", "purple", "red");
        Set<String> strExpected = this.createFromArgsRef("black", "white", "gray",
                "blue", "purple", "red");
        /*
         * Call method under test
         */
        int size = str.size();
        /*
         * Assert values match expectations
         */
        assertEquals(6, size);
        assertEquals(strExpected, str);
    }

    /**
     * Test for size with non-empty Set (large).
     */
    @Test
    public void testSizeNELg() {
        /*
         * Set up variables
         */
        Set<String> str = this.createFromArgsTest("black", "white", "gray", "blue",
                "purple", "red", "green", "yellow", "orange", "pink");
        Set<String> strExpected = this.createFromArgsRef("black", "white", "gray", "blue",
                "purple", "red", "green", "yellow", "orange", "pink");
        /*
         * Call method under test
         */
        int size = str.size();
        /*
         * Assert values match expectations
         */
        assertEquals(10, size);
        assertEquals(strExpected, str);
    }
}
