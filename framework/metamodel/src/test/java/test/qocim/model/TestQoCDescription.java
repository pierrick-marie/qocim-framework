package test.qocim.model;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import qocim.model.QoCDescription;

import static org.junit.Assert.assertEquals;

public class TestQoCDescription {

	private QoCDescription testDescription;

	private static final String DESCRIPTION_NAME = "description";

	@BeforeClass
	public static final void beforeClass() {
		System.out.println(" ======= Test QoCDescription =======");
	}

	@Before
	public final void before() {
		testDescription = new QoCDescription(DESCRIPTION_NAME);
	}

	@Test
	public final void testToString() {
		assertEquals("QoC Description: " + DESCRIPTION_NAME, testDescription.toString());

		System.out.println(" - toString(): OK");
	}

	@Test
	public final void testKeywords() {
		assertEquals(0, testDescription.keywords().size());

		final String keyword = "keyword";
		testDescription.keywords().add(keyword);
		assertEquals(1, testDescription.keywords().size());
		assertEquals(keyword, testDescription.keywords().get(0));

		System.out.println(" - keywords(): OK");
	}

	@Test
	public final void testDescription() {
		final String description = "Description";

		assertEquals("", testDescription.description());
		testDescription.setDescription(description);
		assertEquals(description, testDescription.description());

		System.out.println(" - description(): OK");
	}
}
