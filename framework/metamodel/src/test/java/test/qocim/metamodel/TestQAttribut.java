package test.qocim.metamodel;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import qocim.metamodel.QAttribut;
import qocim.metamodel.QClass;

import static org.junit.Assert.*;

public class TestQAttribut {

	private final String ATTRIBUT_NAME = "test attribut";
	private final Integer VALUE = 2;
	private QAttribut<Integer> testAttribut;
	private QClass container;

	@BeforeClass
	public static final void beforeClass() {
		System.out.println(" ======= Test QAttribut =======");
	}

	@Before
	public final void before() {
		container = new QClass("container");
		testAttribut = new QAttribut<>(ATTRIBUT_NAME, container, VALUE);
	}

	@Test
	public final void testToString() {
		assertEquals(ATTRIBUT_NAME, testAttribut.toString());
		assertEquals(ATTRIBUT_NAME, testAttribut.name);

		System.out.println(" - toString(): OK");
	}

	@Test
	public final void testContainer() {
		assertEquals(container, testAttribut.container());
		QClass testContainer = new QClass("test container");
		testAttribut.setContainer(testContainer);
		assertTrue(testContainer == testAttribut.container());

		System.out.println(" - container(): OK");
	}

	@Test
	public final void testEquals() {
		assertTrue(testAttribut.equals(testAttribut));
		assertTrue(testAttribut.equals(ATTRIBUT_NAME));
		assertFalse(testAttribut.equals(ATTRIBUT_NAME + " FALSE "));

		System.out.println(" - equals(): OK");
	}

	@Test
	public final void testValue() {
		assertEquals(VALUE, testAttribut.value());

		Integer newValue = 3;
		testAttribut.setTValue(newValue);
		assertEquals(newValue, testAttribut.value());

		newValue = 4;
		assertEquals(testAttribut, testAttribut.setTValue(newValue));
		assertEquals(newValue, testAttribut.value());

		assertFalse(testAttribut.setObjectValue("Test False, type error, expected Integer"));

		System.out.println(" - value(): OK");
	}
}
