package test.qocim.model;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import qocim.model.Direction;
import qocim.model.QoCDefinition;
import qocim.model.QoCDescription;
import qocim.model.QoCValue;

import javax.measure.unit.SI;

import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.Assert.*;

public class TestQoCDefinition {

	private static final String DEFINITION_NAME = "test qoc definition";
	private static final String DEFINITION_ID = "1.1";

	private QoCDefinition testDefinition;

	@BeforeClass
	public static final void beforeClass() {
		System.out.println(" ======= Test QoCDefinition =======");
	}

	@Before
	public final void before() {
		testDefinition = new QoCDefinition(DEFINITION_NAME, DEFINITION_ID);
	}

	@Test
	public final void testToString() {
		assertEquals("QoC Definition: " + DEFINITION_NAME + " " + DEFINITION_ID, testDefinition.toString());
		System.out.println(" - toString(): OK");
	}

	@Test
	public final void testId() {
		assertEquals(DEFINITION_ID, testDefinition.id());
		System.out.println(" - id(): OK");
	}

	@Test
	public final void testUnit() {
		assertEquals("", testDefinition.unit());

		testDefinition.setUnit(SI.BIT.toString());
		assertEquals(SI.BIT.toString(), testDefinition.unit());

		System.out.println(" - unit(): OK");
	}

	@Test
	public final void testUri() {
		try {
			URI testUri = new URI("");
			assertEquals(testUri, testDefinition.uri());

			testUri = new URI("telnet://192.0.2.16:80/");
			testDefinition.setUri(testUri);
			assertEquals(testUri, testDefinition.uri());
			assertEquals("telnet://192.0.2.16:80/", testDefinition.uri().toString());

			System.out.println(" - uri(): OK");
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}

	@Test
	public final void testDirection() {

		assertEquals(Direction.UNKNOWN, testDefinition.direction());

		testDefinition.setDirection(Direction.LOWER);
		assertEquals(Direction.LOWER, testDefinition.direction());

		System.out.println(" - direction(): OK");
	}

	@Test
	public final void testIsDefault() {

		assertFalse(testDefinition.isDefault());
		testDefinition.setIsDefault(true);
		assertTrue(testDefinition.isDefault());

		System.out.println(" - isDefault(): OK");
	}

	@Test
	public final void testIsInvariant() {

		assertFalse(testDefinition.isInvariant());
		testDefinition.setIsInvariant(true);
		assertTrue(testDefinition.isInvariant());

		System.out.println(" - isInvariant(): OK");
	}

	@Test
	public final void testDescription() {

		assertEquals(null, testDefinition.desription());

		QoCDescription testDescription = new QoCDescription("test description");
		testDefinition.setDescription(testDescription);
		assertEquals(testDescription, testDefinition.desription());

		System.out.println(" - description(): OK");
	}

	@Test
	public final void testRelatedDefinition() {

		assertEquals(0, testDefinition.relatedDefinitions().size());

		QoCDefinition testRelatedDefinition = new QoCDefinition("related definition", "2.1");
		testDefinition.addRelatedDefinition(testRelatedDefinition);
		assertEquals(1, testDefinition.relatedDefinitions().size());
		assertEquals(testRelatedDefinition, testDefinition.relatedDefinitions().get(0));

		testDefinition.removeRelatedDefinition(testRelatedDefinition);
		assertEquals(0, testDefinition.relatedDefinitions().size());

		System.out.println(" - relatedDefinition(): OK");
	}

	@Test
	public final void testQoCValues() {

		assertEquals(0, testDefinition.qocValues().size());

		QoCValue<Integer> testQoCValue = new QoCValue<>("test qoc value", 1);
		testDefinition.addValue(testQoCValue);
		assertEquals(1, testDefinition.qocValues().size());
		assertEquals(testQoCValue, testDefinition.qocValues().get(0));

		testDefinition.removeValue(testQoCValue);
		assertEquals(0, testDefinition.qocValues().size());

		System.out.println(" - qocValues(): OK");
	}

	@Test
	public final void testMinQoCValues() {

		assertEquals(null, testDefinition.minValue());

		QoCValue<Integer> testQoCValue = new QoCValue<>("test min qoc value", 2);
		testDefinition.setMinValue(testQoCValue);
		assertEquals(testQoCValue, testDefinition.minValue());

		System.out.println(" - minValues(): OK");
	}

	@Test
	public final void testMaxQoCValues() {

		assertEquals(null, testDefinition.maxValue());

		QoCValue<Integer> testQoCValue = new QoCValue<>("test max qoc value", 3);
		testDefinition.setMaxValue(testQoCValue);
		assertEquals(testQoCValue, testDefinition.maxValue());

		System.out.println(" - miaxValues(): OK");
	}
}
