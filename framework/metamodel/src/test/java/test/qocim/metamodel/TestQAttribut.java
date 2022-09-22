package test.qocim.metamodel;

import org.junit.Before;
import org.junit.Test;
import qocim.metamodel.QAttribut;
import qocim.metamodel.QClass;
import test.qocim.metamodel.log.Messages;

import static org.junit.Assert.*;

public class TestQAttribut {

	private static final String ATTRIBUT_NAME = "test attribut";
	private static final Integer VALUE = 2;
	private QAttribut<Integer> testAttribut;
	private QClass container;

	@Before
	public final void before() {
		System.out.println(Messages.BEFORE);
		container = new QClass();
		testAttribut = new QAttribut<>(ATTRIBUT_NAME, container, VALUE);
	}

	@Test
	public final void testToString() {
		assertEquals(ATTRIBUT_NAME, testAttribut.toString());
		assertEquals(ATTRIBUT_NAME, testAttribut.name);
	}

	@Test
	public final void testContainer() {
		assertEquals(container, testAttribut.container());
		QClass testContainer = new QClass();
		testAttribut.setContainer(testContainer);
		assertTrue(testContainer == testAttribut.container());
	}

	@Test
	public final void testEquals() {
		assertTrue(testAttribut.equals(testAttribut));
		assertTrue(testAttribut.equals(ATTRIBUT_NAME));
		assertFalse(testAttribut.equals(ATTRIBUT_NAME + " FALSE "));
	}

	@Test
	public final void testValue() {
		assertEquals(VALUE, testAttribut.value());

		Integer newValue = 3;
		assertEquals(testAttribut, testAttribut.setValue(newValue));
		assertEquals(newValue, testAttribut.value());
	}
}
