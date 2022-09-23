package test.qocim.model.test;

import org.junit.Before;
import org.junit.Test;
import qocim.model.QoCIndicator;
import test.qocim.metamodel.log.Messages;
import test.qocim.model.MyIndicator;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TestQoCIndicator {

	private final String INDICATOR_NAME = "test qoc indicator";
	private final Integer INDICATOR_ID = 1;
	private QoCIndicator testIndicator;

	@Before
	public final void before() {
		System.out.println(Messages.BEFORE);
		testIndicator = new QoCIndicator(INDICATOR_NAME, INDICATOR_ID);
	}

	@Test
	public final void testContructor() {
		assertEquals(INDICATOR_ID, testIndicator.id());
		assertEquals(INDICATOR_NAME, testIndicator.name);
		assertEquals("QoC Indicator: " + INDICATOR_ID, testIndicator.toString());
		assertEquals(0, testIndicator.criteria().size());
		assertEquals(0, testIndicator.qocValues().size());
		assertEquals(null, testIndicator.information());
	}

	@Test
	public final void testMyIndicator() {

		final String name = "test new attribut";
		final Integer value = 42;

		MyIndicator myIndicator = new MyIndicator();
		myIndicator.add(name, value);
		assertEquals(value, myIndicator.get(name));

		myIndicator.set(name, value + 1);
		assertEquals(value + 1, myIndicator.get(name));

		myIndicator.remove(name);
		assertEquals(null, myIndicator.get(name));
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
	}
}
