package test.qocim.metamodel;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import qocim.metamodel.QClass;

import static org.junit.Assert.*;

public class TestQClass {

	private final String CLASS_NAME = "test class";
	private final String ANONYMOUS_CLASS_NAME = "anonymous test class";
	private QClass testClass = null;
	private QClass anonymousTestClass = null;

	@BeforeClass
	public static final void beforeClass() {
		System.out.println(" ======= Test QClass =======");
	}

	@Before
	public final void before() {
		testClass = new QClass(CLASS_NAME);
		anonymousTestClass = new QClass(ANONYMOUS_CLASS_NAME);
	}

	@Test
	public final void testToString() {
		assertEquals(CLASS_NAME, testClass.toString());
		assertEquals(CLASS_NAME, testClass.name);
		assertEquals(ANONYMOUS_CLASS_NAME, anonymousTestClass.toString());
		assertEquals(ANONYMOUS_CLASS_NAME, anonymousTestClass.name);

          System.out.println(" - toString(): OK");
	}

	@Test
	public final void testEquals() {
		assertTrue(testClass.equals(testClass));
		assertFalse(testClass.equals(anonymousTestClass));

          System.out.println(" - equals(): OK");
      }

	@Test
	public final void testContainer() {
		assertTrue(testClass == testClass.container());
		assertTrue(anonymousTestClass == anonymousTestClass.container());
		testClass.setContainer(anonymousTestClass);
		assertTrue(anonymousTestClass == anonymousTestClass.container());
		assertTrue(anonymousTestClass == testClass.container());

          System.out.println(" - container(): OK");
      }
}
