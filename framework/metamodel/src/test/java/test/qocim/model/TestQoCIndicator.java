package test.qocim.model;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import qocim.model.QoCCriterion;
import qocim.model.QoCIndicator;
import qocim.model.QoCValue;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TestQoCIndicator {

	private final String INDICATOR_NAME = "test qoc indicator";
	private final Integer INDICATOR_ID = 1;
	private QoCIndicator testIndicator;

	@BeforeClass
	public static void beforeClass() {
		System.out.println(" ======= Test QoCIndicator =======");
	}

	@Before
	public final void before() {
		testIndicator = new QoCIndicator(INDICATOR_NAME, INDICATOR_ID);
	}

	@Test
	public final void testContructor() {
		assertEquals(INDICATOR_ID, testIndicator.id());
		assertEquals(INDICATOR_NAME, testIndicator.name);
		assertEquals(0, testIndicator.qocCriteria().size());
		assertEquals(0, testIndicator.qocValues().size());

		System.out.println(" - new(): OK");
	}

	@Test
	public final void testQoCIndicatorUsage() {
		final String name = "Precision";
		final Integer id = 1;
		QoCIndicator precision = new QoCIndicator(name, id);

		precision.add("value", "42");
		List<String> elements = new LinkedList<>();
		elements.add("list element value");
		precision.add("list criteria", elements);

		assertEquals("42", precision.get("value"));
		assertEquals(elements, precision.get("list criteria"));

		System.out.println(" - usage: OK");
	}

	@Test
	public final void testId() {
		assertEquals(INDICATOR_ID, testIndicator.id());

		System.out.println(" - id(): OK");
	}

	@Test
	public final void testToString() {
		assertEquals("QoC Indicator: " + INDICATOR_NAME + " " + INDICATOR_ID, testIndicator.toString());

		System.out.println(" - toString(): OK");
	}


	@Test
	public final void testQoCValue() {

		assertEquals(0, testIndicator.qocValues().size());

		QoCValue<Integer> testQoCValue = new QoCValue<>("test qoc value", 1, 42);
		testIndicator.addQoCValue(testQoCValue);
		assertEquals(1, testIndicator.qocValues().size());
		assertEquals(testQoCValue, testIndicator.qocValues().get(0));

		testIndicator.removeQoCValue(testQoCValue);
		assertEquals(0, testIndicator.qocValues().size());

		System.out.println(" - qocValue(): OK");
	}

	@Test
	public final void testQoCCriteria() {

		assertEquals(0, testIndicator.qocCriteria().size());

		QoCCriterion testQoCCriterion = new QoCCriterion("test qoc value", "1.1");
		testIndicator.addQoCCriterion(testQoCCriterion);
		assertEquals(1, testIndicator.qocCriteria().size());
		assertEquals(testQoCCriterion, testIndicator.qocCriteria().get(0));

		testIndicator.removeQoCCriteria(testQoCCriterion);
		assertEquals(0, testIndicator.qocCriteria().size());

		System.out.println(" - qocCriteria(): OK");
	}

	@Test
	public final void testGetQoCCriterionById() {

		for (int i = 0; i < 5; i++) {
			testIndicator.addQoCCriterion(new QoCCriterion("test " + i , i + ".1"));
		}

		assertEquals(5, testIndicator.qocCriteria().size());

		for (int i = 0; i < 5; i++) {
			QoCCriterion criterion = testIndicator.getQoCCriterionById(i + ".1");
			assertEquals(i + ".1", criterion.id());
			assertEquals("test " + i, criterion.name);
		}

		System.out.println(" - getQoCCriterionById(): OK");
	}

	@Test
	public final void testGetQoCValueById() {

		for (int i = 0; i < 5; i++) {
			testIndicator.addQoCValue(new QoCValue<Integer>("test " + i , i + 10, i + 42));
		}

		assertEquals(5, testIndicator.qocValues().size());

		for (int i = 0; i < 5; i++) {
			QoCValue<Integer> value = (QoCValue<Integer>) testIndicator.getQoCValueById(i + 10);
			assertEquals(i + 10, (int) value.id());
			assertEquals(i + 42, (int) value.value());
		}

		System.out.println(" - getQoCValueById(): OK");
	}
}
