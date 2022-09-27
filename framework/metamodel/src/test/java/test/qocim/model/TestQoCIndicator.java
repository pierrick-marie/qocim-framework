package test.qocim.model;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import qocim.information.InformationImpl;
import qocim.information.QInformation;
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
	public static final void beforeClass() {
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
		assertEquals(0, testIndicator.criteria().size());
		assertEquals(0, testIndicator.qocValues().size());
		assertEquals(null, testIndicator.information());

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
	public final void testInformation() {
		final Integer informationData = 42;
		final String informationName = "test information";
		final QInformation<Integer> testInformation = new InformationImpl<>(informationName, informationData);
		testIndicator.setInformation(testInformation);
		assertEquals(testInformation, testIndicator.information());
		assertEquals(0, testInformation.compareTo((QInformation<Integer>) testIndicator.information()));

		System.out.println(" - information(): OK");
	}

	@Test
	public final void testToString() {
		assertEquals("QoC Indicator: " + INDICATOR_NAME + " " + INDICATOR_ID, testIndicator.toString());

		System.out.println(" - toString(): OK");
	}


	@Test
	public final void testQoCValue() {

		assertEquals(0, testIndicator.qocValues().size());

		QoCValue<Integer> testQoCValue = new QoCValue<>("test qoc value", 1);
		testIndicator.addQoCValue(testQoCValue);
		assertEquals(1, testIndicator.qocValues().size());
		assertEquals(testQoCValue, testIndicator.qocValues().get(0));

		testIndicator.removeQoCValue(testQoCValue);
		assertEquals(0, testIndicator.qocValues().size());

		System.out.println(" - qocValue(): OK");
	}

	@Test
	public final void testQoCCriteria() {

		assertEquals(0, testIndicator.criteria().size());

		QoCCriterion testQoCCriterion = new QoCCriterion("test qoc value", "1.1");
		testIndicator.addCriterion(testQoCCriterion);
		assertEquals(1, testIndicator.criteria().size());
		assertEquals(testQoCCriterion, testIndicator.criteria().get(0));

		testIndicator.removeCriteria(testQoCCriterion);
		assertEquals(0, testIndicator.criteria().size());

		System.out.println(" - criteria(): OK");
	}
}
