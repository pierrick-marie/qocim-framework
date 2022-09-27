package test.qocim.model;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import qocim.model.QoCCriterion;
import qocim.model.QoCDefinition;
import qocim.model.QoCValue;

import static org.junit.Assert.assertEquals;

public class TestQoCCriterion {

	private static final String CRITERION_NAME = "test criterion";
	private static final String CRITERION_ID = "1.1";

	private QoCCriterion testCriterion;

	@BeforeClass
	public static final void beforeClass() {
		System.out.println(" ======= Test QoCCriterion =======");
	}

	@Before
	public final void before() {
		testCriterion = new QoCCriterion(CRITERION_NAME, CRITERION_ID);
	}

	@Test
	public final void testToString() {
		assertEquals("QoC criterion: " + CRITERION_NAME + " " + CRITERION_ID, testCriterion.toString());

		System.out.println(" - toString(): OK");
	}

	@Test
	public final void testId() {
		assertEquals(CRITERION_ID, testCriterion.id());

		System.out.println(" - id(): OK");
	}

	@Test
	public final void testQoCDefinition() {
		assertEquals(0, testCriterion.qocDefinitions().size());

		QoCDefinition testQoCDefinition = new QoCDefinition("test qoc value", "1.1");
		testCriterion.addQoCDefinition(testQoCDefinition);
		assertEquals(1, testCriterion.qocDefinitions().size());
		assertEquals(testQoCDefinition, testCriterion.qocDefinitions().get(0));

		testCriterion.removeQoCDefinition(testQoCDefinition);
		assertEquals(0, testCriterion.qocDefinitions().size());

		System.out.println(" - qocDefinition(): OK");
	}
}
