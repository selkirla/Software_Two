import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import components.naturalnumber.NaturalNumber;
import components.naturalnumber.NaturalNumber1L;

/**
 * JUnit test fixture for {@code NaturalNumber}'s constructors and kernel
 * methods.
 *
 * @author JT Vendetti & Selin Kirbas
 *
 */

public abstract class NaturalNumberTest {

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * implementation under test and returns the result.
     *
     * @return the new number
     * @ensures constructorTest = 0
     */
    protected abstract NaturalNumber constructorTest();

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * implementation under test and returns the result.
     *
     * @param i
     *            {@code int} to initialize from
     * @return the new number
     * @requires i >= 0
     * @ensures constructorTest = i
     */
    protected abstract NaturalNumber constructorTest(int i);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * implementation under test and returns the result.
     *
     * @param s
     *            {@code String} to initialize from
     * @return the new number
     * @requires there exists n: NATURAL (s = TO_STRING(n))
     * @ensures s = TO_STRING(constructorTest)
     */
    protected abstract NaturalNumber constructorTest(String s);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * implementation under test and returns the result.
     *
     * @param n
     *            {@code NaturalNumber} to initialize from
     * @return the new number
     * @ensures constructorTest = n
     */
    protected abstract NaturalNumber constructorTest(NaturalNumber n);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * reference implementation and returns the result.
     *
     * @return the new number
     * @ensures constructorRef = 0
     */
    protected abstract NaturalNumber constructorRef();

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * reference implementation and returns the result.
     *
     * @param i
     *            {@code int} to initialize from
     * @return the new number
     * @requires i >= 0
     * @ensures constructorRef = i
     */
    protected abstract NaturalNumber constructorRef(int i);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * reference implementation and returns the result.
     *
     * @param s
     *            {@code String} to initialize from
     * @return the new number
     * @requires there exists n: NATURAL (s = TO_STRING(n))
     * @ensures s = TO_STRING(constructorRef)
     */
    protected abstract NaturalNumber constructorRef(String s);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * reference implementation and returns the result.
     *
     * @param n
     *            {@code NaturalNumber} to initialize from
     * @return the new number
     * @ensures constructorRef = n
     */
    protected abstract NaturalNumber constructorRef(NaturalNumber n);

    /**
     * Test for no argument constructor.
     *
     */
    @Test
    public final void testConstructor() {
        /*
         * Set up variables
         */
        NaturalNumber q = this.constructorTest();
        NaturalNumber qExpected = this.constructorRef();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(qExpected, q);
    }

    /**
     * Test for int constructor using 0.
     *
     */
    @Test
    public final void testConstructorIntZero() {
        /*
         * Set up variables
         */
        NaturalNumber q = this.constructorTest(0);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(0, q.toInt());
    }

    /**
     * Test for int constructor using single digit.
     */
    @Test
    public void testConstructorIntSingle() {
        /*
         * Set up variable
         */
        int i = 8;
        NaturalNumber q = this.constructorTest(i);
        NaturalNumber qExpected = this.constructorTest(i);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(qExpected, q);
    }

    /**
     * Test for int constructor constructor using double digit.
     */
    @Test
    public void testConstructorIntDouble() {
        /*
         * Set up variable
         */
        int i = 56;
        NaturalNumber q = this.constructorTest(i);
        NaturalNumber qExpected = this.constructorTest(i);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(qExpected, q);
    }

    /**
     * Test for String constructor with zero.
     *
     */
    @Test
    public final void testConstructorStringZero() {
        /*
         * Set up variables
         */
        NaturalNumber q = this.constructorTest("0");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(0, q.toInt());
    }

    /**
     * Test for String constructor with single digit.
     *
     */
    @Test
    public final void testConstructorStringSingle() {
        /*
         * Set up variables
         */
        String s = "8";

        NaturalNumber q = this.constructorTest(s);
        NaturalNumber qExpected = this.constructorRef(s);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(qExpected, q);
    }

    /**
     * Test for String constructor with double digit.
     *
     */
    @Test
    public final void testConstructorStringDouble() {
        /*
         * Set up variables
         */
        String s = "56";

        NaturalNumber q = this.constructorTest(s);
        NaturalNumber qExpected = this.constructorRef(s);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(qExpected, q);
    }

    /**
     * Test for NaturalNumber constructor with zero.
     *
     */
    @Test
    public final void testConstructorNNZero() {
        /*
         * Set up variables
         */
        NaturalNumber n = new NaturalNumber1L("0");

        NaturalNumber q = this.constructorTest(n);
        NaturalNumber qExpected = this.constructorRef(n);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(qExpected, q);
    }

    /**
     * Test for NaturalNumber constructor with single digit.
     *
     */
    @Test
    public final void testConstructorNNSingle() {
        /*
         * Set up variables
         */
        NaturalNumber n = new NaturalNumber1L("8");

        NaturalNumber q = this.constructorTest(n);
        NaturalNumber qExpected = this.constructorRef(n);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(qExpected, q);
    }

    /**
     * Test for NaturalNumber constructor with double digit.
     *
     */
    @Test
    public final void testConstructorNNDouble() {
        /*
         * Set up variables
         */
        NaturalNumber n = new NaturalNumber1L("56");

        NaturalNumber q = this.constructorTest(n);
        NaturalNumber qExpected = this.constructorRef(n);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(qExpected, q);
    }

    /**
     * Test for multiplyBy10 method with zero.
     *
     */
    @Test
    public final void testMultiplyBy10Zero() {
        /*
         * Set up variables
         */
        NaturalNumber q = this.constructorTest();
        NaturalNumber qExpected = this.constructorRef();
        /*
         * Call method under test
         */
        q.multiplyBy10(0);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(qExpected, q);
    }

    /**
     * Test for multiplyBy10 method with 0 to 3.
     *
     */
    @Test
    public final void testMultiplyBy10One() {
        /*
         * Set up variables
         */
        NaturalNumber q = new NaturalNumber1L();
        NaturalNumber qExpected = new NaturalNumber1L(3);
        /*
         * Call method under test
         */
        q.multiplyBy10(3);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(qExpected, q);
    }

    /**
     * Test for multiplyBy10 method with 5 to 50.
     *
     */
    @Test
    public void testMultiplyBy10Two() {
        /*
         * Set up variables
         */
        NaturalNumber q = new NaturalNumber1L(5);
        NaturalNumber qExpected = new NaturalNumber1L(50);
        /*
         * Call method under test
         */
        q.multiplyBy10(0);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(qExpected, q);
    }

    /**
     * Test for multiplyBy10 method with 7 to 77..
     *
     */
    @Test
    public void testMultiplyBy10Three() {
        /*
         * Set up variables
         */
        NaturalNumber q = new NaturalNumber1L(7);
        NaturalNumber qExpected = new NaturalNumber1L(77);
        /*
         * Call method under test
         */
        q.multiplyBy10(7);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(qExpected, q);
    }

    /**
     * Test for multiplyBy10 method with large number.
     *
     */
    @Test
    public final void testMultiplyBy10Four() {
        /*
         * Set up variables
         */
        NaturalNumber q = new NaturalNumber1L(12345678);
        NaturalNumber qExpected = new NaturalNumber1L(123456789);
        /*
         * Call method under test
         */
        q.multiplyBy10(9);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(qExpected, q);
    }

    /**
     * Test for divideBy10 method with 0.
     *
     */
    @Test
    public final void testDivideBy10Zero() {
        /*
         * Set up variables
         */
        NaturalNumber q = new NaturalNumber1L(0);
        NaturalNumber qExpected = new NaturalNumber1L(0);
        /*
         * Call method under test
         */
        int result = q.divideBy10();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(0, result);
        assertEquals(qExpected, q);
    }

    /**
     * Test for divideBy10 method with 10.
     *
     */
    @Test
    public final void testDivideBy10One() {
        /*
         * Set up variables
         */
        NaturalNumber q = new NaturalNumber1L(10);
        NaturalNumber qExpected = new NaturalNumber1L(1);
        /*
         * Call method under test
         */
        int result = q.divideBy10();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(0, result);
        assertEquals(qExpected, q);
    }

    /**
     * Test for divideBy10 method with 334.
     *
     */
    @Test
    public final void testDivideBy10Two() {
        /*
         * Set up variables
         */
        NaturalNumber q = new NaturalNumber1L(334);
        NaturalNumber qExpected = new NaturalNumber1L(33);
        /*
         * Call method under test
         */
        int result = q.divideBy10();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(4, result);
        assertEquals(qExpected, q);
    }

    /**
     * Test for divideBy10 method with 12345678.
     *
     */
    @Test
    public void testDivideBy10Three() {
        /*
         * Set up variables
         */
        NaturalNumber q = new NaturalNumber1L(12345678);
        NaturalNumber qExpected = new NaturalNumber1L(1234567);
        /*
         * Call method under test
         */
        int result = q.divideBy10();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(8, result);
        assertEquals(qExpected, q);
    }

    /**
     * Test for isZero method default (true).
     */
    @Test
    public void testIsZeroT() {
        /*
         * Set up variables
         */
        NaturalNumber q = this.constructorTest();
        /*
         * Call method under test
         */
        boolean result = q.isZero();
        /*
         * Assert that values of variables match expectations
         */
        assertTrue(result);
    }

    /**
     * Test for isZero method with int constructor (true).
     */
    @Test
    public void testIsZeroIntT() {
        /*
         * Set up variables
         */
        int zero = 0;
        NaturalNumber q = this.constructorTest(zero);
        /*
         * Call method under test
         */
        boolean result = q.isZero();
        /*
         * Assert that values of variables match expectations
         */
        assertTrue(result);
    }

    /**
     * Test for isZero method with int constructor (false).
     */
    @Test
    public void testIsZeroIntF() {
        /*
         * Set up variables
         */
        int thirty = 30;
        NaturalNumber q = this.constructorTest(thirty);
        /*
         * Call method under test
         */
        boolean result = q.isZero();
        /*
         * Assert that values of variables match expectations
         */
        assertFalse(result);
    }

    /**
     * Test for isZero method with String constructor (true).
     */
    @Test
    public void testIsZeroStringT() {
        /*
         * Set up variables
         */
        String s = "0";
        NaturalNumber q = this.constructorTest(s);
        /*
         * Call method under test
         */
        boolean result = q.isZero();
        /*
         * Assert that values of variables match expectations
         */
        assertTrue(result);
    }

    /**
     * Test for isZero method with String constructor (false).
     */
    @Test
    public void testIsZeroStringF() {
        /*
         * Set up variables
         */
        String s = "30";
        NaturalNumber q = this.constructorTest(s);
        /*
         * Call method under test
         */
        boolean result = q.isZero();
        /*
         * Assert that values of variables match expectations
         */
        assertFalse(result);
    }

    /**
     * Test for isZero method with NaturalNumber constructor (true).
     */
    @Test
    public void testIsZeroNNT() {
        /*
         * Set up variables
         */
        NaturalNumber n = new NaturalNumber1L("0");

        NaturalNumber q = this.constructorTest(n);
        /*
         * Call method under test
         */
        boolean result = q.isZero();
        /*
         * Assert that values of variables match expectations
         */
        assertTrue(result);
    }

    /**
     * Test for isZero method with NaturalNumber constructor (false).
     */
    @Test
    public void testIsZeroNNF() {
        /*
         * Set up variables
         */
        NaturalNumber n = new NaturalNumber1L("30");

        NaturalNumber q = this.constructorTest(n);
        /*
         * Call method under test
         */
        boolean result = q.isZero();
        /*
         * Assert that values of variables match expectations
         */
        assertFalse(result);
    }
}
