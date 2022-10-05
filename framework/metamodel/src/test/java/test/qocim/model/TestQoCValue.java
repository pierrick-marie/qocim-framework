package test.qocim.model;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import qocim.model.QoCValue;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestQoCValue {

	private QoCValue<Integer> testValue;
	private Date creationDate;

	private static final String VALUE_NAME = "qoc value";
	private static final Integer VALUE_ID = 1;
	private static final Integer QOC_VALUE = 42;

	@BeforeClass
	public static void beforeClass() {
		System.out.println(" ======= Test QoCValue =======");
	}

	@Before
	public final void before() {
		creationDate = new Date();
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		testValue = new QoCValue<>(VALUE_NAME, VALUE_ID, QOC_VALUE);
	}

	@Test
	public final void testToString() {
		assertEquals("QoC Value: " + VALUE_NAME, testValue.toString());

		System.out.println(" - toString(): OK");
	}

	@Test
	public final void testId() {
		assertEquals(VALUE_ID, testValue.id());

		System.out.println(" - id(): OK");
	}

	@Test
	public final void testCreationDate() {
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		final Date testCreationDate = new Date();
		assertTrue(creationDate.before(testValue.creationDate()));
		assertTrue(testCreationDate.after(testValue.creationDate()));

		System.out.println(" - creationDate(): OK");
	}

	@Test
	public final void testValue() {
		assertEquals(QOC_VALUE, testValue.value());

		System.out.println(" - value(): OK");
	}
}
