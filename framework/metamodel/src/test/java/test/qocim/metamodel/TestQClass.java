package test.qocim.metamodel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import qocim.metamodel.QClass;
import test.qocim.metamodel.log.Messages;

import static org.junit.Assert.*;

public class TestQClass {

    private final String CLASS_NAME = "test class";
    private QClass testClass = null;
    private QClass anonymousTestClass = null;

    @Before
    public final void before() {
        System.out.println(Messages.BEFORE);
        testClass = new QClass(CLASS_NAME);
        anonymousTestClass = new QClass();
    }

    @Test
    public final void testToString() {
        assertEquals(CLASS_NAME, testClass.toString());
        assertEquals(CLASS_NAME, testClass.name);
        assertEquals("QClass", anonymousTestClass.toString());
        assertEquals("QClass", anonymousTestClass.name);
    }

    @Test
    public final void testEquals() {
        assertTrue(testClass.equals(testClass));
        assertFalse(testClass.equals(anonymousTestClass));
    }

    @Test
    public final void testContainer() {
        assertTrue(testClass == testClass.container());
        assertTrue(anonymousTestClass == anonymousTestClass.container());
        testClass.setContainer(anonymousTestClass);
        assertTrue(anonymousTestClass == anonymousTestClass.container());
        assertTrue(anonymousTestClass == testClass.container());
    }
}
